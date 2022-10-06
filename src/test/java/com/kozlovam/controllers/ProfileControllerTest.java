package com.kozlovam.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kozlovam.dao.UserDAO;
import com.kozlovam.dto.UserDTO;
import com.kozlovam.models.Token;
import com.kozlovam.models.User;
import com.kozlovam.security.JWTAuthorizationFilter;
import com.kozlovam.services.UserService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;
    @InjectMocks
    ProfileController profileController;
    @MockBean
    UserService userService;

    @BeforeEach
    public void setUp() throws UnsupportedEncodingException {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    public void test(){
        assertThat(profileController).isNotNull();
    }

    @Test
    public void forbiddenRequestWithoutToken() throws Exception {
        this.mockMvc.perform(get("/userprofile/main"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void loadProfile() throws Exception {
        UserDTO userDTO = new UserDTO(1, "Ivan", "Ivanov", "ivan@maiil.ru");
        String token = jwtAuthorizationFilter.getJWTToken("ivan@maiil.ru");
        Assert.assertNotNull(token);
        Mockito.when(userService.userVerifyByToken(token)).thenReturn(userDTO);

        MvcResult result = this.mockMvc.perform(get("/userprofile/main")
                .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(userDTO);

        Assert.assertEquals(mapper.writeValueAsString(userDTO), result.getResponse().getContentAsString());
    }
}
