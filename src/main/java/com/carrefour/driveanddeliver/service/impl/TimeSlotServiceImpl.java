package com.carrefour.driveanddeliver.service.impl;

import com.carrefour.driveanddeliver.exception.BusinessException;
import com.carrefour.driveanddeliver.exception.ResourceNotFoundException;
import com.carrefour.driveanddeliver.model.TimeSlot;
import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import com.carrefour.driveanddeliver.repository.TimeSlotRepository;
import com.carrefour.driveanddeliver.service.TimeSlotService;
import com.carrefour.driveanddeliver.service.dto.TimeSlotDTO;
import com.carrefour.driveanddeliver.service.mapper.TimeSlotMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing timeSlots.
 */
@Service
@AllArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    private final TimeSlotMapper timeSlotMapper;

    /**
     * Retrieves available time slots by delivery method.
     *
     * @return a list of TimeSlotDTO objects representing available time slots.
     */
    @Transactional(readOnly = true)
    public List<TimeSlotDTO> getAvailableTimeSlots(DeliveryMethod deliveryMethod) {
        List<TimeSlot> deliveries = timeSlotRepository.findByDeliveryMethodAndBookedFalse(deliveryMethod);
        return deliveries.stream()
                .map(timeSlotMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a timeSlot by its ID.
     *
     * @param id the ID of the timeSlot to retrieve.
     * @return a TimeSlotDTO object representing the timeSlot.
     * @throws ResourceNotFoundException if no timeSlot is found with the given ID.
     */
    @Transactional(readOnly = true)
    public TimeSlotDTO getTimeSlotById(Long id) {
        TimeSlot timeSlot = getTimeSlot(id);
        return timeSlotMapper.toDto(timeSlot);
    }

    /**
     * Creates a new timeSlot.
     *
     * @param timeSlotDTO the TimeSlotDTO object containing the timeSlot details.
     * @return a TimeSlotDTO object representing the created timeSlot.
     * @throws BusinessException if time slot already exists for the delivery method.
     */
    @Transactional
    public TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = timeSlotMapper.toEntity(timeSlotDTO);
        boolean exist = timeSlotRepository.existsByDeliveryMethodAndStartTimeAndEndTime(timeSlotDTO.getDeliveryMethod(),
                timeSlotDTO.getStartTime(), timeSlotDTO.getEndTime());
        if (exist) {
            throw new BusinessException("A time slot already exists for the delivery method: " + timeSlotDTO.getDeliveryMethod().name() +
                    ", starting at: " + timeSlotDTO.getStartTime() +
                    " and ending at: " + timeSlotDTO.getEndTime());
        }
        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toDto(savedTimeSlot);

    }

    /**
     * Creates a new timeSlot.
     *
     * @param timeSlotId the id of timeSlot.
     * @return a TimeSlotDTO object representing the booked timeSlot.
     * @throws BusinessException if time slot already booked.
     */
    @Transactional
    public TimeSlotDTO bookTimeSlot(Long timeSlotId) {
        TimeSlot timeSlot = getTimeSlot(timeSlotId);
        if (timeSlot.isBooked()) {
            throw new BusinessException("Time slot already booked.");
        }
        int updatedRow = timeSlotRepository.bookTimeSlot(timeSlotId);
        timeSlot.setBooked(updatedRow > 0);
        return timeSlotMapper.toDto(timeSlot);
    }

    private TimeSlot getTimeSlot(Long id) {
        return timeSlotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time slot not found with id " + id));
    }
}
