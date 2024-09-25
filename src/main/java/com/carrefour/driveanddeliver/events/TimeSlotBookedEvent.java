package com.carrefour.driveanddeliver.events;

import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;

public record TimeSlotBookedEvent(Long customerId, DeliveryMethod deliveryMethod, Long timeSlotId) {
}
