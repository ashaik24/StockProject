package com.stockproject.controller;

import com.stockproject.entities.*;
import com.stockproject.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminStockController {

    private DataService dataService;

    @Autowired
    public AdminStockController(DataService dataService){
        this.dataService = dataService;
    }

    @PostMapping(value = "/addStocks",consumes = MediaType.APPLICATION_JSON_VALUE)
    List<StockDetails> addStock(@RequestBody StockDetails stockDetails){
        stockDetails.resetBounds();
        return dataService.save(stockDetails);
    }

    @PostMapping(value = "/updateMarketHours")
    MarketHours updateMarketHours(@RequestBody MarketHours marketHours){
        return dataService.updateMarketHours(marketHours);
    }

    @GetMapping(value = "/getMarketHours")
    MarketHours getMarketHours(){
        return dataService.getMarketHours();
    }

    @GetMapping(value = "/getUserScheduledTransactions/{id}")
    public List<ScheduledTransactionClientObject> getUserScheduledTransactions(@PathVariable("id") String username){
        List<ScheduledTransaction> allList = dataService.getUserScheduledTransactions(username);
        List<ScheduledTransactionClientObject> returnList = new ArrayList<>();
        for (ScheduledTransaction  sTransaction: allList) {
            if(!sTransaction.IsExpired())
            {
                returnList.add(new ScheduledTransactionClientObject(sTransaction));
            }
        }
        return returnList;
    }

    @GetMapping(value="/deleteScheduledTransaction/{id}")
    public boolean deleteScheduledTransaction(@PathVariable("id")long id)
    {
        return dataService.deleteScheduledTransaction(id);
    }
}
