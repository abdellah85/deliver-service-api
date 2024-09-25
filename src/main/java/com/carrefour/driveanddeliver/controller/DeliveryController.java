package com.carrefour.driveanddeliver.controller;

import com.carrefour.driveanddeliver.controller.constant.UrlConstantes;
import com.carrefour.driveanddeliver.service.DeliveryService;
import com.carrefour.driveanddeliver.service.dto.DeliveryDTO;
import com.carrefour.driveanddeliver.service.dto.EnumDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(UrlConstantes.DELIVERIES_URL)
@AllArgsConstructor
@Validated
@Slf4j
@Tag(name = "${api.deliveryController.name}", description = "${api.deliveryController.description}")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/{id}")
    @Operation(summary = "${api.deliveryController.getDelivery.description}")
    public ResponseEntity<DeliveryDTO> getDelivery(@PathVariable Long id) {
        log.debug("REST request to get delivery by ID : {}", id);
        DeliveryDTO deliveryDTO = deliveryService.getDeliveryById(id);
        deliveryDTO = addDeliveryLinks(deliveryDTO);
        return ResponseEntity.ok(deliveryDTO);
    }

    @GetMapping(UrlConstantes.DELIVERY_METHODS_URL)
    @Operation(summary = "${api.deliveryController.getDeliveryMethods.description}")
    public ResponseEntity<List<EnumDTO>> getDeliveryMethods() {
        log.debug("REST request to get delivery methods");
        List<EnumDTO> deliveryMethods = deliveryService.getDeliveryMethods();
        return ResponseEntity.ok(deliveryMethods);
    }

    @PostMapping
    @Operation(summary = "${api.deliveryController.createDelivery.description}")
    public ResponseEntity<DeliveryDTO> createDelivery(@Valid @RequestBody DeliveryDTO deliveryDTO) {
        log.debug("REST to create a new delivery");
        DeliveryDTO newDelivery = deliveryService.createDelivery(deliveryDTO);
        newDelivery = addDeliveryLinks(newDelivery);
        URI location = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DeliveryController.class)
                .getDelivery(newDelivery.getId())).toUri();
        return ResponseEntity.created(location).body(newDelivery);
    }

    private DeliveryDTO addDeliveryLinks(DeliveryDTO deliveryDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DeliveryController.class)
                .getDelivery(deliveryDTO.getId())).withSelfRel();
        deliveryDTO.add(selfLink);
        return deliveryDTO;
    }
}