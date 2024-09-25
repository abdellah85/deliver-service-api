package com.carrefour.driveanddeliver.repository;

import com.carrefour.driveanddeliver.model.TimeSlot;
import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    
    List<TimeSlot> findByDeliveryMethodAndBookedFalse(DeliveryMethod deliveryMethod);

    boolean existsByDeliveryMethodAndStartTimeAndEndTime(DeliveryMethod deliveryMethod, LocalTime startTime, LocalTime endTime);
    
    @Modifying
    @Transactional
    @Query("UPDATE TimeSlot ts SET ts.booked = true WHERE ts.id = :id AND ts.booked = false")
    int bookTimeSlot(Long id);

}