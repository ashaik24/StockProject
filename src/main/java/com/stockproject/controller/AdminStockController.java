package com.stockproject.controller;

import com.stockproject.entities.*;
import com.stockproject.service.AdminStockService;
import com.stockproject.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Handles all the admin related activities
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminStockController {

    private AdminStockService adminStockService;

    @Autowired
    public AdminStockController(AdminStockService adminStockService){
        this.adminStockService=adminStockService;
    }

    @PostMapping(value = "/addStocks",consumes = MediaType.APPLICATION_JSON_VALUE)
    List<StockDetails> addStock(@RequestBody StockDetails stockDetails){
        stockDetails.resetBounds();
        return adminStockService.save(stockDetails);
    }

    @PostMapping(value = "/updateMarketHours")
    MarketHours updateMarketHours(@RequestBody MarketHours marketHours){
        return adminStockService.updateMarketHours(marketHours);
    }

    @GetMapping(value = "/getMarketHours")
    MarketHours getMarketHours(){
        return adminStockService.getMarketHours();
    }

}
