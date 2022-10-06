package com.kozlovam.services;


import com.kozlovam.dao.UserDAO;
import com.kozlovam.dto.UserDTO;
import com.kozlovam.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserDAO userDAO;


    @DisplayName("JUnit test for findUserById method")
    @Test
    public void findUserById(){
        User user = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");

        BDDMockito.given(userDAO.findById(1)).willReturn(user);
        assertNotNull(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserlogin(user.getUserlogin());
        Assert.assertEquals(user.getUserlogin(), userDTO.getUserlogin());
    }


    @Test
    public void findUserByNullId(){
        User user = new User();

        BDDMockito.given(userDAO.findById(user.getId())).willReturn(null);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserlogin(user.getUserlogin());
        Assert.assertEquals(user.getUserlogin(), userDTO.getUserlogin());
    }

    @DisplayName("JUnit test for loadUserByUserLogin method")
    @Test
    public void loadUserByUserLogin(){
        User user = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");

        BDDMockito.given(userService.loadUserByUserLogin(user.getUserlogin())).willReturn(user);

        User newUser = userDAO.findByUsername(user.getUserlogin());

        Assert.assertEquals(user, newUser);
    }

    @DisplayName("JUnit test for saveUser method")
    @Test
    public void saveUser(){
        User user = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");
        User newuser = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");
        userDAO.save(user);
        userDAO.save(newuser);
        Mockito.verify(userDAO, times(2)).save(any());
    }

    @DisplayName("JUnit test for findAllUsers method")
    @Test
    public void findAllUsers(){
        User user = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");
        User newuser = new User("Ivan", "Ivanov", "ivan@maiil.ru", "11111");
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(newuser);
        Mockito.when(userService.findAllUsers()).thenReturn(users);
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void findAllUsersIfListNull(){
        ArrayList<User> users = new ArrayList<>();
        Mockito.when(userService.findAllUsers()).thenReturn(users);
        Assert.assertEquals(0, users.size());
    }

    @Test
    public void loadNullUserIfDoesNotExist(){
        User user = new User();
        Mockito.when(userDAO.findByUsername(user.getUserlogin())).thenReturn(null);
        Assert.assertEquals(null, user.getUserlogin());
    }
}
