package com.carrefour.driveanddeliver.listener;

import com.carrefour.driveanddeliver.events.TimeSlotBookedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeliveryEventListener {

    @EventListener
    public void handleTimeSlotBooked(TimeSlotBookedEvent event) {
        if (event == null) {
            log.warn("Received null TimeSlotBookedEvent");
            return;
        }
        log.info("Time slot booked with ID: {} for customer ID: {}", event.timeSlotId(), event.customerId());
    }
}