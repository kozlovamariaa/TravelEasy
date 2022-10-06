package com.kozlovam.controllers;

import com.kozlovam.dto.UserDTO;
import com.kozlovam.models.Flight;
import com.kozlovam.security.JWTAuthorizationFilter;
import com.kozlovam.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AviaTicketsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;
    @InjectMocks
    AviaTicketsController aviaTicketsController;
    @MockBean
    UserService userService;


    @BeforeEach
    public void setUp() throws UnsupportedEncodingException {
        mockMvc = MockMvcBuilders.standaloneSetup(aviaTicketsController).build();
    }

    @Test
    public void returnsJSONOfFlights() throws Exception {
        UserDTO userDTO = new UserDTO(1, "Ivan", "Ivanov", "ivan@maiil.ru");
        String token = jwtAuthorizationFilter.getJWTToken("maria");
        assertNotNull(token);

        Mockito.when(userService.userVerifyByToken(token)).thenReturn(userDTO);
        Flight flight = new Flight();
        flight.setOrigin("MOW");
        flight.setDestination("DXB");
        flight.setDeparture_at("2023-03-01");
        flight.setReturn_at("2023-03-10");

        this.mockMvc.perform(get("/buytickets")
                .header("Authorization", token)
                .flashAttr("flight", flight))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void unauthorizedUser() throws Exception {
        Flight flight = new Flight();
        flight.setOrigin("MOW");
        flight.setDestination("DXB");
        flight.setDeparture_at("2023-03-01");
        flight.setReturn_at("2023-03-10");

        this.mockMvc.perform(get("/buytickets")
                .flashAttr("flight", flight))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}