package com.carrefour.driveanddeliver.service.dto;

import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@Builder
public class DeliveryDTO extends RepresentationModel<DeliveryDTO> {

    private Long id;

    @NotNull(message = "Delivery method must not be null")
    private DeliveryMethod deliveryMethod;

    @NotNull(message = "Delivery date must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    private UserDTO customer;
}
