package com.kozlovam.controllers;

import com.kozlovam.models.User;
import com.kozlovam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    public void RegistrationController() {

    }

    @PostMapping()
    public ResponseEntity create(@ModelAttribute("user") User user) {
        if (userService.loadUser(user) == null) {
            user.setUserpassword(passwordEncoder.encode(user.getUserpassword()));
            userService.saveUser(user);
            return  ResponseEntity.status(HttpStatus.OK).body("Регистрация прошла успешно");
        }
        else if(userService.loadUser(user) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Такой пользователь существует");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка, попробуйте еще");
    }
}
