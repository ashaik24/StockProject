package com.stockproject.controller;

import com.stockproject.entities.User;
import com.stockproject.entities.BalanceTransaction;
import com.stockproject.repository.BalanceRepository;
import com.stockproject.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    private RegistrationService registrationService;
    private BalanceRepository balanceRepository;
    @Autowired
    public RegistrationController(RegistrationService registrationService,BalanceRepository balanceRepository){
        this.registrationService=registrationService;
        this.balanceRepository = balanceRepository;
    }

    @PostMapping("/register")
    void addUser(@RequestBody User user){
        BalanceTransaction userCash = constructUserBalance(user);
        registrationService.save(user);
        balanceRepository.save(userCash);
    }

    public BalanceTransaction constructUserBalance(User user){
        BalanceTransaction userCash = new BalanceTransaction();
        userCash.setUser(user);
        userCash.setCashValue(0);
        return  userCash;
    }
    @GetMapping("/login/{id}")
    User getUser(@PathVariable("id") String username){
        return registrationService.findUser(username);
    }
}
