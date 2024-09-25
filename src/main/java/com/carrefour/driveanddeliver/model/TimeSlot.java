package com.carrefour.driveanddeliver.model;

import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "time_slot")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method", length = 20, nullable = false)
    private DeliveryMethod deliveryMethod;

    private LocalTime startTime;

    private LocalTime endTime;
    
    private boolean booked;
}