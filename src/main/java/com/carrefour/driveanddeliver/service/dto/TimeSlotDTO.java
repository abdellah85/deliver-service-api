package com.carrefour.driveanddeliver.service.dto;

import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalTime;


@Data
@Builder
public class TimeSlotDTO extends RepresentationModel<TimeSlotDTO> {

    private Long id;

    @NotNull(message = "Delivery method must not be null")
    private DeliveryMethod deliveryMethod;

    @NotNull(message = "start time must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "end time must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    private boolean booked;
}
