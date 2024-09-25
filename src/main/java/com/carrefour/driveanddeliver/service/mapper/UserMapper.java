package com.carrefour.driveanddeliver.service.mapper;

import com.carrefour.driveanddeliver.model.User;
import com.carrefour.driveanddeliver.service.dto.UserDTO;
import com.carrefour.driveanddeliver.service.dto.RegistrationDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .firstname(user.getFirstname()).build();
    }

    public User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .lastname(dto.getLastname())
                .firstname(dto.getFirstname()).build();
    }

    public User toEntity(RegistrationDTO dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .lastname(dto.getLastname())
                .firstname(dto.getFirstname()).build();
    }

    

    public User toEntity(UserDTO dto, User user) {
        user.setEmail(dto.getEmail());
        user.setEmail(dto.getEmail());
        user.setLastname(dto.getLastname());
        user.setFirstname(dto.getFirstname());
        return user;
    }
}