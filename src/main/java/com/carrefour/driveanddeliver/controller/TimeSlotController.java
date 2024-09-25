package com.carrefour.driveanddeliver.controller;

import com.carrefour.driveanddeliver.controller.constant.UrlConstantes;
import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import com.carrefour.driveanddeliver.service.TimeSlotService;
import com.carrefour.driveanddeliver.service.dto.TimeSlotDTO;
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
@RequestMapping(UrlConstantes.TIMESLOTS_URL)
@AllArgsConstructor
@Validated
@Tag(name = "${api.timeSlotController.name}", description = "${api.timeSlotController.description}")
@Slf4j
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @PostMapping
    @Operation(summary = "${api.timeSlotController.createTimeSlot.description}")
    public ResponseEntity<TimeSlotDTO> createTimeSlot(@Valid @RequestBody TimeSlotDTO timeSlotDTO) {
        log.debug("REST request to create a new timeSlot");
        TimeSlotDTO newTimeSlot = timeSlotService.createTimeSlot(timeSlotDTO);
        newTimeSlot = addTimeSlotLinks(newTimeSlot);
        URI location = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TimeSlotController.class)
                .getTimeSlot(newTimeSlot.getId())).toUri();
        return ResponseEntity.created(location).body(newTimeSlot);
    }


    @GetMapping(UrlConstantes.AVAILABLE_TIMESLOTS_URL)
    @Operation(summary = "${api.timeSlotController.getAvailableTimeSlots.description}")
    public ResponseEntity<List<TimeSlotDTO>> getAvailableTimeSlots(@RequestParam("method") DeliveryMethod deliveryMethod) {
        log.debug("REST request to get available time slots by delivery method: {}", deliveryMethod);
        List<TimeSlotDTO> timeSlots = timeSlotService.getAvailableTimeSlots(deliveryMethod)
                .stream().map(this::addTimeSlotLinks).toList();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/{id}")
    @Operation(summary = "${api.timeSlotController.getTimeSlot.description}")
    public ResponseEntity<TimeSlotDTO> getTimeSlot(@PathVariable Long id) {
        log.debug("REST request to get timeSlot by ID: {}", id);
        TimeSlotDTO timeSlotDTO = timeSlotService.getTimeSlotById(id);
        timeSlotDTO = addTimeSlotLinks(timeSlotDTO);
        return ResponseEntity.ok(timeSlotDTO);
    }

    private TimeSlotDTO addTimeSlotLinks(TimeSlotDTO timeSlotDTO) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TimeSlotController.class)
                .getTimeSlot(timeSlotDTO.getId())).withSelfRel();
        timeSlotDTO.add(selfLink);
        Link timeSlotsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TimeSlotController.class)
                .getAvailableTimeSlots(timeSlotDTO.getDeliveryMethod())).withRel("timeSlots");
        timeSlotDTO.add(timeSlotsLink);
        return timeSlotDTO;
    }
}

