package com.stockproject.controller;


import com.stockproject.objects.AddMoney;
import com.stockproject.entities.BalanceTransaction;
import com.stockproject.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
/**
 * Handles user balance related calls
 */
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


    @PostMapping(value="withDrawMoney",consumes = MediaType.APPLICATION_JSON_VALUE)
    public BalanceTransaction withDrawMoney(@RequestBody AddMoney addMoney){
        return balanceService.withDrawMoney(addMoney);
    }

    @GetMapping("/getBalance/{id}")
    public float getUser(@PathVariable("id") String username){
        return balanceService.getAvailableCash(username);
    }
}