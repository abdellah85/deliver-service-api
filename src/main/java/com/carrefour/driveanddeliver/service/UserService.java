package com.carrefour.driveanddeliver.service;

import com.carrefour.driveanddeliver.service.dto.RegistrationDTO;
import com.carrefour.driveanddeliver.service.dto.UserDTO;

public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO registerUser(RegistrationDTO registrationDTO);

    UserDTO getCurrentUser();

}
