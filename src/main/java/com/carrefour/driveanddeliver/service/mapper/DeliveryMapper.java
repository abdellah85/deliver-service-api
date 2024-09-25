package com.carrefour.driveanddeliver.service.mapper;

import com.carrefour.driveanddeliver.model.Delivery;
import com.carrefour.driveanddeliver.service.dto.DeliveryDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryMapper {

    private final TimeSlotMapper timeSlotMapper;
    private final UserMapper userMapper;

    public DeliveryDTO toDto(Delivery delivery) {
        return DeliveryDTO.builder()
                .id(delivery.getId())
                .deliveryMethod(delivery.getDeliveryMethod())
                .timeSlot(timeSlotMapper.toDto(delivery.getTimeSlot()))
                .customer(userMapper.toDto(delivery.getCustomer()))
                .deliveryDate(delivery.getDeliveryDate()).build();
    }

    public Delivery toEntity(DeliveryDTO dto) {
        return Delivery.builder()
                .id(dto.getId())
                .deliveryMethod(dto.getDeliveryMethod())
                .timeSlot(timeSlotMapper.toEntity(dto.getTimeSlot()))
                .customer(userMapper.toEntity(dto.getCustomer()))
                .deliveryDate(dto.getDeliveryDate()).build();

    }

}