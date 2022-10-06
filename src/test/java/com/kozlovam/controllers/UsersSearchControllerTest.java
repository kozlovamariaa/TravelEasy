package com.kozlovam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kozlovam.dto.UserDTO;
import com.kozlovam.models.User;
import com.kozlovam.security.JWTAuthorizationFilter;
import com.kozlovam.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersSearchControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @InjectMocks
    UsersSearchController usersSearchController;

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @BeforeEach
    public void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(usersSearchController).build();
    }
    //добавить в thenReturn assert
    @Test
    public void returnsUserList() throws UnsupportedEncodingException {
        Mockito.when(userService.userVerifyByToken("token")).thenReturn(new UserDTO());

        List<User> list = new ArrayList<>();
        list.add(0, new User());
        list.add(1, new User());
        list.add(2, new User());

        Mockito.when(userService.findAllUsers()).thenReturn(list);

        Assert.assertEquals(3, list.size());
    }

    @Test
    public void findAllUsers() throws Exception {
        String token = jwtAuthorizationFilter.getJWTToken("maria");
        Assert.assertNotNull(token);

        Mockito.when(userService.userVerifyByToken(token)).thenReturn(new UserDTO());
        List<User> list = new ArrayList<>();
        list.add(0, new User());
        list.add(1, new User());
        list.add(2, new User());
        Mockito.when(userService.findAllUsers()).thenReturn(list);

        this.mockMvc.perform(get("/searchusers/allusers")
                .header("Authorization", token))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void searchUserByLogin() throws Exception {
        String token = jwtAuthorizationFilter.getJWTToken("maria");
        Assert.assertNotNull(token);

        UserDTO user_first = new UserDTO();
        user_first.setid(0);
        user_first.setUsername("Ivan");
        user_first.setUserlastname("Ivanov");
        user_first.setUserlogin("ivan@maiil.ru");

        User userModel= new User("Ivan", "Ivanov", "ivan@maiil.ru", "1111");

        Mockito.when(userService.userVerifyByToken(token)).thenReturn(user_first);
        Mockito.when(userService.loadUserByUserLogin("ivan@maiil.ru")).thenReturn(userModel);

        MvcResult result = this.mockMvc.perform(get("/searchusers/user")
                .header("Authorization", token)
                .param("userlogin", "ivan@maiil.ru"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();

        Assert.assertEquals(mapper.writeValueAsString(user_first), result.getResponse().getContentAsString());
    }
}

