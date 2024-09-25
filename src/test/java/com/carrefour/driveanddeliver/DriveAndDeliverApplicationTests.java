package com.carrefour.driveanddeliver;

import com.carrefour.driveanddeliver.controller.constant.UrlConstantes;
import com.carrefour.driveanddeliver.security.dto.AuthRequest;
import com.carrefour.driveanddeliver.service.dto.RegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriveAndDeliverApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String USERNAME = "aabdellah";
    private static final String PASSWORD = "Abdell@h85";
    private static final String BEARER = "Bearer ";

    @Test
    @Order(1)
    void testRegisterUser() throws Exception {
        mockMvc.perform(postRegistrationRequest())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    private MockHttpServletRequestBuilder postRegistrationRequest() throws Exception {
        RegistrationDTO registrationDTO = RegistrationDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .email("aajabli@sqli.com")
                .firstname("Abdellah")
                .lastname("Ajabli")
                .build();
        String json = objectMapper.writeValueAsString(registrationDTO);
        return MockMvcRequestBuilders.post(UrlConstantes.USERS_URL + UrlConstantes.USER_REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    }

    private String generateToken() throws Exception {
        String loginJson = objectMapper.writeValueAsString(new AuthRequest(USERNAME, PASSWORD));
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post(UrlConstantes.AUTH_URL + UrlConstantes.LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return BEARER + objectMapper.readTree(response).get("token").asText();
    }

}
