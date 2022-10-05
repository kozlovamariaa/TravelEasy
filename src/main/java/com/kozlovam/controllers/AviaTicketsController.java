package com.kozlovam.controllers;

import com.kozlovam.models.Flight;
import com.kozlovam.services.FlightService;
import com.kozlovam.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buytickets")
public class AviaTicketsController {

    @Autowired
    UserService userService;

    @Autowired
    FlightService flightService;

    @Autowired
    JSONObject obj;


    @GetMapping
    public ResponseEntity getAllTickets(@ModelAttribute("flight") Flight flight, @RequestHeader("Authorization") String authorization){
        if(userService.userVerifyByToken(authorization) != null){
            obj = flightService.getJSON(flight);
            if(obj == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Измените данные поиска");
            }
            return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
        }
        return null;
    }
}