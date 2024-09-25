package com.carrefour.driveanddeliver.service;

import com.carrefour.driveanddeliver.events.TimeSlotBookedEvent;
import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public DeliveryEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishTimeSlotBooked(Long customerId, DeliveryMethod deliveryMethod, Long timeSlotId) {
        TimeSlotBookedEvent event = new TimeSlotBookedEvent(customerId, deliveryMethod, timeSlotId);
        eventPublisher.publishEvent(event);
    }
}