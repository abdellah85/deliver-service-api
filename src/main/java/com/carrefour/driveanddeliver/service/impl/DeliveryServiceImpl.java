package com.carrefour.driveanddeliver.service.impl;

import com.carrefour.driveanddeliver.exception.ResourceNotFoundException;
import com.carrefour.driveanddeliver.model.Delivery;
import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import com.carrefour.driveanddeliver.repository.DeliveryRepository;
import com.carrefour.driveanddeliver.service.DeliveryEventPublisher;
import com.carrefour.driveanddeliver.service.DeliveryService;
import com.carrefour.driveanddeliver.service.TimeSlotService;
import com.carrefour.driveanddeliver.service.UserService;
import com.carrefour.driveanddeliver.service.dto.DeliveryDTO;
import com.carrefour.driveanddeliver.service.dto.EnumDTO;
import com.carrefour.driveanddeliver.service.dto.TimeSlotDTO;
import com.carrefour.driveanddeliver.service.dto.UserDTO;
import com.carrefour.driveanddeliver.service.mapper.DeliveryMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Service class for managing deliveries.
 */
@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final TimeSlotService timeSlotService;

    private final UserService userService;
    private final DeliveryMapper deliveryMapper;

    private final DeliveryEventPublisher eventPublisher;

    /**
     * Retrieves a delivery by its ID.
     *
     * @param id the ID of the delivery to retrieve.
     * @return a DeliveryDTO object representing the delivery.
     * @throws ResourceNotFoundException if no delivery is found with the given ID.
     */
    @Transactional(readOnly = true)
    public DeliveryDTO getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .map(deliveryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id " + id));
    }

    /**
     * Creates a new delivery.
     *
     * @param deliveryDTO the DeliveryDTO object containing the delivery details.
     * @return a DeliveryDTO object representing the created delivery.
     */
    @Transactional
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {

        TimeSlotDTO timeSlotDTO = timeSlotService.bookTimeSlot(deliveryDTO.getTimeSlot().getId());
        deliveryDTO.setTimeSlot(timeSlotDTO);
        UserDTO userDTO = userService.getCurrentUser();
        deliveryDTO.setCustomer(userDTO);
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        Delivery savedDelivery = deliveryRepository.save(delivery);

        eventPublisher.publishTimeSlotBooked(deliveryDTO.getCustomer().getId(), deliveryDTO.getDeliveryMethod(),
                deliveryDTO.getTimeSlot().getId());

        return deliveryMapper.toDto(savedDelivery);
    }

    /**
     * Retrieves all delivery methods.
     *
     * @return a list of EnumDTO objects representing all delivery methods.
     */
    @Cacheable("deliveryMethods")
    public List<EnumDTO> getDeliveryMethods() {
        return Arrays.stream(DeliveryMethod.values())
                .map(deliveryMethod -> new EnumDTO(deliveryMethod.name(), deliveryMethod.getLabel()))
                .toList();
    }
}
