package com.carrefour.driveanddeliver.service.mapper;

import com.carrefour.driveanddeliver.model.TimeSlot;
import com.carrefour.driveanddeliver.service.dto.TimeSlotDTO;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {
    public TimeSlotDTO toDto(TimeSlot timeSlot) {
        return TimeSlotDTO.builder()
                .id(timeSlot.getId())
                .deliveryMethod(timeSlot.getDeliveryMethod())
                .booked(timeSlot.isBooked())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime()).build();
    }

    public TimeSlot toEntity(TimeSlotDTO dto) {
        return TimeSlot.builder()
                .id(dto.getId())
                .deliveryMethod(dto.getDeliveryMethod())
                .booked(dto.isBooked())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime()).build();

    }
}