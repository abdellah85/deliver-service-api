package com.carrefour.driveanddeliver.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class RegistrationDTO extends RepresentationModel<RegistrationDTO> {

    @NotNull(message = "Username must not be null")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,20}$",
            message = "Username must be between 3 and 20 characters and can contain letters, numbers, " +
                    "dots, underscores, or hyphens.")
    private String username;
    private String firstname;
    private String lastname;
    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password must not be null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, include at least one uppercase letter, " +
                    "one lowercase letter, one digit, and one special character.")
    private String password;
}
