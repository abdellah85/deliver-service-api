package com.carrefour.driveanddeliver.controller;


import com.carrefour.driveanddeliver.controller.constant.UrlConstantes;
import com.carrefour.driveanddeliver.service.UserService;
import com.carrefour.driveanddeliver.service.dto.RegistrationDTO;
import com.carrefour.driveanddeliver.service.dto.UserDTO;
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
@RequestMapping(UrlConstantes.USERS_URL)
@AllArgsConstructor
@Validated
@Tag(name = "${api.userController.name}", description = "${api.userController.description}")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping(UrlConstantes.USER_REGISTER_URL)
    @Operation(summary = "${api.userController.registerUser.description}")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegistrationDTO userRegistrationDTO) {
        log.debug("REST to register a new user");
        UserDTO newUser = userService.registerUser(userRegistrationDTO);
        newUser = addUserLinks(newUser);
        URI location = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getUser(newUser.getId())).toUri();
        return ResponseEntity.created(location).body(newUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "${api.userController.getUser.description}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get user by ID : {}", id);
        UserDTO userDTO = userService.getUserById(id);
        userDTO = addUserLinks(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    private UserDTO addUserLinks(UserDTO user) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(user.getId())).withSelfRel();
        user.add(selfLink);
        return user;
    }

}
