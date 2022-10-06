package com.kozlovam.controllers;

import com.kozlovam.services.UserService;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    public MockMvc mockMvc;
    @InjectMocks
    private LoginController loginController;
    @MockBean
    UserService userService;


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }
    private String userlogin = "masha";
    private String userpassword = "11111";


    @Test
    public void Test(){
        assertThat(loginController).isNotNull();
    }


    @Test
    public void emptyPasswordAndLogin() throws Exception {
        this.mockMvc.perform(post("/login"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginWithoutPassword() throws Exception{
        this.mockMvc.perform(post("/login")
                .param("userlogin", userlogin))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void incorrectLogin() throws Exception {
        String invalidEmail = "lalala";
        Mockito.when(userService.loadUserByUserLogin(invalidEmail)).thenReturn(null);

        MvcResult result = this.mockMvc.perform(post("/login")
                .param("userlogin", invalidEmail)
                .param("userpassword", userpassword))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Assert.assertEquals("error, try again", result.getResponse().getContentAsString());
    }

    @Test
    public void correctLogin() throws Exception{
        this.mockMvc.perform(post("/login")
                .param("userlogin",userlogin)
                .param("userpassword", userpassword))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
