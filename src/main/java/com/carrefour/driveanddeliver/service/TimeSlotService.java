package com.carrefour.driveanddeliver.service;

import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import com.carrefour.driveanddeliver.service.dto.TimeSlotDTO;

import java.util.List;

public interface TimeSlotService {
    TimeSlotDTO getTimeSlotById(Long id);

    List<TimeSlotDTO> getAvailableTimeSlots(DeliveryMethod deliveryMethod);

    TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO);

    TimeSlotDTO bookTimeSlot(Long timeSlotId);
}
