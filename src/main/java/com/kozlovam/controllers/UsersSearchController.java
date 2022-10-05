package com.kozlovam.controllers;

import com.kozlovam.dto.UserDTO;
import com.kozlovam.models.User;
import com.kozlovam.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/searchusers")
public class UsersSearchController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping("/allusers")
    public List<UserDTO> searchUser(@RequestHeader("Authorization") String authorization) {
        if (userService.userVerifyByToken(authorization) != null) {
            List<User> list = userService.findAllUsers();
            List<UserDTO> dtos = list
                    .stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
            return dtos;
        }
        return null;
    }


    @GetMapping("/user")
    public ResponseEntity searchUserByLogin(@RequestParam("userlogin") String userlogin, @RequestHeader("Authorization") String authorization) {
        if (userService.userVerifyByToken(authorization) != null) {
            User selectedUser = userService.loadUserByUserLogin(userlogin);
            if(selectedUser == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь не найден");
            }
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(selectedUser, UserDTO.class));
        }
        return null;
    }
}
