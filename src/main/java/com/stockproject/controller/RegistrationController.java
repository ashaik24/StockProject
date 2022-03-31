package com.stockproject.controller;

import com.stockproject.entities.User;
import com.stockproject.entities.BalanceTransaction;
import com.stockproject.repository.BalanceRepository;
import com.stockproject.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
/**
 * Handles all the user registrations and logins
 */
public class RegistrationController {

    private RegistrationService registrationService;
    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService=registrationService;
    }

    @PostMapping("/register")
    void addUser(@RequestBody User user){
        user.setBalance(0);
        registrationService.save(user);
    }

    @GetMapping("/login/{id}")
    User getUser(@PathVariable("id") String username){
        return registrationService.findUser(username);
    }
}
