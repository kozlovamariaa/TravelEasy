package com.kozlovam.controllers;

import com.kozlovam.dto.UserDTO;
import com.kozlovam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userprofile")
public class ProfileController {
    @Autowired
    UserDTO userDTO;

    @Autowired
    UserService userService;

    @GetMapping("/main")
    public UserDTO openUserPage(@RequestHeader("Authorization") String authorization){
        userDTO = userService.userVerifyByToken(authorization);
        return userDTO;
    }
}
