package com.kozlovam.controllers;

import com.kozlovam.dto.UserDTO;
import com.kozlovam.security.JWTAuthorizationFilter;
import com.kozlovam.services.UserService;
import com.kozlovam.services.WeatherService;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.NestedServletException;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {
    @Autowired
    MockMvc mockMvc;
    @InjectMocks
    WeatherController weatherController;
    @MockBean
    UserService userService;
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    private String notcity = "abc";
    private String city_rus = "Москва";
    private String city_eng = "Moscow";


    @BeforeEach
    public void setUp() throws UnsupportedEncodingException {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }


    @Test
    public void cityDoesNotExist() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setid(1);
        userDTO.setUsername("maria");
        userDTO.setUserlastname("maria");
        userDTO.setUserlogin("mashamasha");

        String token = jwtAuthorizationFilter.getJWTToken("maria");
        Assert.assertNotNull(token);

        Mockito.when(userService.userVerifyByToken(token)).thenReturn(userDTO);
        Assert.assertEquals(1, userDTO.getid());

        this.mockMvc.perform(get("/getweather")
                .header("Authorization", token)
                .param("city", notcity))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Город не найден"));
//
//        Assert.assertEquals("Город не найден", result.getResponse().getContentAsString());
    }

    @Test
    public void cityExistsRus() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setid(1);
        userDTO.setUsername("maria");
        userDTO.setUserlastname("kozlova");
        userDTO.setUserlogin("mashamasha");

        String token = jwtAuthorizationFilter.getJWTToken("maria");
        assertNotNull(token);

        Mockito.when(userService.userVerifyByToken(token)).thenReturn(userDTO);

        Assert.assertEquals(1, userDTO.getid());
        this.mockMvc.perform(get("/getweather")
                .header("Authorization", token)
                .param("city", city_rus)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void cityExistsEng() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setid(1);
        userDTO.setUsername("maria");
        userDTO.setUserlastname("kozlova");
        userDTO.setUserlogin("mashamasha");

        String token = jwtAuthorizationFilter.getJWTToken("maria");
        assertNotNull(token);
        Mockito.when(userService.userVerifyByToken(token)).thenReturn(userDTO);

        this.mockMvc.perform(get("/getweather")
                .header("Authorization", token)
                .param("city", city_eng))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void unauthorizedUser() throws Exception {
        this.mockMvc.perform(get("/getweather")
                .param("city", city_eng))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
