package com.stockproject.controller;


import com.stockproject.entities.AddMoney;
import com.stockproject.entities.BalanceTransaction;
import com.stockproject.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BalanceController {
    private BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService){
        this.balanceService=balanceService;
    }

    @PostMapping(value="addMoney",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BalanceTransaction addMoney(@RequestBody AddMoney addMoney){
        return balanceService.addMoney(addMoney);
    }

    @GetMapping(value = "getUserBalance/{id}")
    public float getUserBalance(@PathVariable("id") String username){
        return balanceService.getAvailableCash(username);
    }

    @PostMapping(value="withDrawMoney",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BalanceTransaction withDrawMoney(@RequestBody AddMoney addMoney){
        return balanceService.withDrawMoney(addMoney);
    }

    @GetMapping("/getBalance/{id}")
    public float getUser(@PathVariable("id") String username){
        return balanceService.getAvailableCash(username);
    }
}