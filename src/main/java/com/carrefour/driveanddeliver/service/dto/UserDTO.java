package com.carrefour.driveanddeliver.service.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}
