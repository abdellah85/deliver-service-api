package com.carrefour.driveanddeliver;

import com.carrefour.driveanddeliver.controller.constant.UrlConstantes;
import com.carrefour.driveanddeliver.model.enumeration.DeliveryMethod;
import com.carrefour.driveanddeliver.security.dto.AuthRequest;
import com.carrefour.driveanddeliver.service.dto.DeliveryDTO;
import com.carrefour.driveanddeliver.service.dto.RegistrationDTO;
import com.carrefour.driveanddeliver.service.dto.TimeSlotDTO;
import com.carrefour.driveanddeliver.service.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER = "Bearer ";
    private String token;

    @BeforeEach
    void setUp(TestInfo testInfo) throws Exception {
        if (!testInfo.getTestMethod().get().getName().equals("testRegisterUser")) {
            token = generateToken();
        }
    }

    @Test
    @Order(1)
    void testRegisterUser() throws Exception {
        mockMvc.perform(postRegistrationRequest())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @Order(2)
    void testCreateTimeSlotDeliveryFirst() throws Exception {
        mockMvc.perform(postTimeSlotRequest(LocalTime.of(9, 0), LocalTime.of(11, 0)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @Order(3)
    void testCreateTimeSlotDeliverySecond() throws Exception {
        mockMvc.perform(postTimeSlotRequest(LocalTime.of(11, 0), LocalTime.of(12, 0)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @Order(4)
    void testExistingTimeSlot() throws Exception {
        mockMvc.perform(postTimeSlotRequest(LocalTime.of(9, 0), LocalTime.of(11, 0)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    void testCreateDelivery() throws Exception {
        mockMvc.perform(postDeliveryRequest(LocalDate.of(2024, 10, 5), 1L, 1L))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @Order(6)
    void testCreateDeliveryWithUnavailableTimeSlot() throws Exception {
        mockMvc.perform(postDeliveryRequest(LocalDate.of(2024, 10, 5), 1L, 1L))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Time slot already booked."));
    }

    @Test
    @Order(7)
    void testGetAvailableTimeSlots() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(UrlConstantes.TIMESLOTS_URL + UrlConstantes.AVAILABLE_TIMESLOTS_URL)
                        .header(AUTH_HEADER, token)
                        .param("method", DeliveryMethod.DELIVERY_TODAY.name()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].booked").value(false));
    }

    @Test
    @Order(8)
    void testGetDeliveryMethods() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(UrlConstantes.DELIVERIES_URL + UrlConstantes.DELIVERY_METHODS_URL)
                        .header(AUTH_HEADER, generateToken()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    void testGetDelivery() throws Exception {
        Long deliveryId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(UrlConstantes.DELIVERIES_URL + "/{id}", deliveryId)
                        .header(AUTH_HEADER, generateToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deliveryId));
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

    private MockHttpServletRequestBuilder postTimeSlotRequest(LocalTime startTime, LocalTime endTime) throws Exception {
        TimeSlotDTO timeSlotDTO = TimeSlotDTO.builder()
                .deliveryMethod(DeliveryMethod.DELIVERY_TODAY)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        String json = objectMapper.writeValueAsString(timeSlotDTO);
        return MockMvcRequestBuilders.post(UrlConstantes.TIMESLOTS_URL)
                .header(AUTH_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    }

    private MockHttpServletRequestBuilder postDeliveryRequest(LocalDate deliveryDate, Long customerId, Long timeSlotId) throws Exception {
        DeliveryDTO deliveryDTO = DeliveryDTO.builder()
                .deliveryDate(deliveryDate)
                .deliveryMethod(DeliveryMethod.DELIVERY_TODAY)
                .customer(UserDTO.builder().id(customerId).build())
                .timeSlot(TimeSlotDTO.builder().id(timeSlotId).build())
                .build();
        String json = objectMapper.writeValueAsString(deliveryDTO);
        return MockMvcRequestBuilders.post(UrlConstantes.DELIVERIES_URL)
                .header(AUTH_HEADER, token)
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
