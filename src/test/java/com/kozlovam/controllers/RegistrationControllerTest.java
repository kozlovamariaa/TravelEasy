package com.kozlovam.controllers;


import com.kozlovam.exceptions.NullPasswordException;
import com.kozlovam.exceptions.NullUserException;
import com.kozlovam.models.User;
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
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    RegistrationController registrationController;

    @MockBean
    UserService userService;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void Test() {
        assertThat(registrationController).isNotNull();
    }

    @Test
    public void registrationWithoutUser() throws Exception {
        User user = new User();

        Mockito.when(userService.loadUser(user)).thenThrow(NullUserException.class);

        MvcResult result = this.mockMvc.perform(
                post("/registration")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals("Ошибка, попробуйте еще", result.getResponse().getContentAsString());
    }

    @Test
    public void registrationWithoutPassword() throws Exception {
        User user = new User("Ivan", "Ivanov", "ivan@maiil.ru", null);

        Mockito.when(userService.loadUser(user)).thenThrow(NullPasswordException.class);

        MvcResult result = this.mockMvc.perform(
                post("/registration")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals("Ошибка, попробуйте еще", result.getResponse().getContentAsString());
    }

    @Test
    public void personIsExistThenStatus400Returned() throws Exception {
        User user = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");

        Mockito.when(userService.loadUser(user)).thenReturn(user);

        MvcResult result = this.mockMvc.perform(
                post("/registration").contentType("application/json")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        Assert.assertEquals("Такой пользователь существует", result.getResponse().getContentAsString());
    }

    @Test
    public void personNotExistThenStatus200Returned() throws Exception {
        User user = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");

        Mockito.when(userService.loadUser(any(User.class))).thenReturn(null);

        MvcResult result = this.mockMvc.perform(
                post("/registration").contentType("application/json")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Assert.assertEquals("Регистрация прошла успешно", result.getResponse().getContentAsString());
    }

    @Test
    public void registrationWithoutUsername() throws Exception {
        User user = new User(null, "Ivanov", "ivan@maiil.ru", "11111");

        Mockito.when(userService.loadUser(user)).thenThrow(NullUserException.class);

        Assert.assertThrows(NullUserException.class , () -> userService.loadUser(user));

        MvcResult result = this.mockMvc.perform(
                post("/registration").contentType("application/json")
                        .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        Assert.assertEquals("Ошибка, попробуйте еще", result.getResponse().getContentAsString());
    }
}