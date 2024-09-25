package com.carrefour.driveanddeliver.controller;

import com.carrefour.driveanddeliver.controller.constant.UrlConstantes;
import com.carrefour.driveanddeliver.security.dto.AuthRequest;
import com.carrefour.driveanddeliver.security.dto.AuthResponse;
import com.carrefour.driveanddeliver.security.JwtUtil;
import com.carrefour.driveanddeliver.security.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstantes.AUTH_URL)
@AllArgsConstructor
@Validated
@Slf4j
@Tag(name = "${api.authController.name}", description = "${api.authController.description}")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtUtil jwtUtil;
    
    private final CustomUserDetailsService userDetailsService;

    @PostMapping(UrlConstantes.LOGIN_URL)
    @Operation(summary = "${api.authController.login.description}")
    public ResponseEntity<AuthResponse> createToken(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return ResponseEntity.ok(AuthResponse.builder().token(jwtUtil.getToken(userDetails.getUsername())).build());
    }
}
