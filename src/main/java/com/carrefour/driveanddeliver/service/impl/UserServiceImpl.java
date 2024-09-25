package com.carrefour.driveanddeliver.service.impl;

import com.carrefour.driveanddeliver.exception.BusinessException;
import com.carrefour.driveanddeliver.exception.ResourceNotFoundException;
import com.carrefour.driveanddeliver.model.User;
import com.carrefour.driveanddeliver.repository.UserRepository;
import com.carrefour.driveanddeliver.security.SecurityUtils;
import com.carrefour.driveanddeliver.service.UserService;
import com.carrefour.driveanddeliver.service.dto.RegistrationDTO;
import com.carrefour.driveanddeliver.service.dto.UserDTO;
import com.carrefour.driveanddeliver.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve.
     * @return a UserDTO object representing the user.
     * @throws ResourceNotFoundException if no user is found with the given ID.
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    /**
     * Retrieves current user.
     *
     * @return a UserDTO object representing the current user.
     * @throws ResourceNotFoundException if no user is found.
     */
    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        return SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByUsername)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Creates a new user.
     *
     * @param registrationDTO the registrationDTO object containing the user details.
     * @return a UserDTO object representing the registred user.
     * @throws BusinessException if Username is already taken or Email is already taken.
     */
    @Transactional
    public UserDTO registerUser(RegistrationDTO registrationDTO) {

        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new BusinessException("Username is already taken.");
        }
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new BusinessException("Email is already taken.");
        }

        User user = userMapper.toEntity(registrationDTO);
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);

    }
}
