package com.kozlovam.services;

import com.kozlovam.dao.UserDAO;
import com.kozlovam.dto.UserDTO;
import com.kozlovam.models.Token;
import com.kozlovam.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private final static Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserDTO userDTO;

    public UserService() {
    }

    public UserDTO findUserById(int id){
        User user = userDAO.findById(id);
        if(user != null){
            userDTO.setUsername(user.getUsername());
            userDTO.setUserlastname(user.getUserlastname());
            userDTO.setUserlogin(user.getUserlogin());
            userDTO.setid(user.getId());
            return userDTO;
        }
        else{
            logger.error("Пользователь не найден.");
        }
        return null;
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }


    public void updateUser(User user) {
        userDAO.update(user);
    }


    public UserDTO userVerifyByToken(String token) {
        Token userToken = userDAO.userVerifyByToken(token);
        if(userToken != null){
            return findUserById(userToken.getUser().getId());
        }
        return null;
    }

    public void saveUserToken(Token token) {
        userDAO.saveUserToken(token);
    }

    public User loadUserByUserLogin(String userlogin) throws UsernameNotFoundException {
        User newUser = userDAO.findByUsername(userlogin);
        if (newUser == null) {
            logger.error("Пользователь не найден.");
        }
        else{
            logger.info("Пользователь найден: " + userlogin);
            return newUser;
        }
        return null;
    }

    //for registration
    public User loadUser(User user) throws UsernameNotFoundException {
        User newUser = userDAO.findByUsername(user.getUserlogin());
        if (newUser == null) {
            logger.error("Пользователь не найден.");
            return null;
        }
        return newUser;
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }
}
