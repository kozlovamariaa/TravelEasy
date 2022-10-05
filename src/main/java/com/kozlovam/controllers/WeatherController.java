package com.kozlovam.controllers;


import com.kozlovam.services.UserService;
import com.kozlovam.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/getweather")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @Autowired
    UserService userService;

    @GetMapping()
    public String getCurrentWeather(@RequestParam("city") String city, @RequestHeader("Authorization") String authorization){
        if(userService.userVerifyByToken(authorization) != null) {
            return weatherService.getJSON(city);
        }
        return "try again";
    }
}
