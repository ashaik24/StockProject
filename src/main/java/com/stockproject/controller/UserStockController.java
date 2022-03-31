package com.stockproject.controller;

import com.stockproject.StockProjectApplication;
import com.stockproject.entities.*;
import com.stockproject.repository.AdminStockRepository;
import com.stockproject.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserStockController {
    private DataService dataService;
    private AdminStockRepository adminStockRepository;

    @Autowired
    public UserStockController(DataService dataService, AdminStockRepository adminStockRepository){
        this.dataService = dataService;
        this.adminStockRepository = adminStockRepository;
    }

    @GetMapping("/getStocks")
    public List<StockDetails> getStock(){
        return dataService.getStocks();
    }

    @PostMapping(value = "/buyStocks",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object buyStock(@RequestBody StockPurchase stockPurchase) {
        stockPurchase.setSell(false);
        if(stockPurchase.getExpiry() == null) {
            return dataService.sellOrBuyStock(stockPurchase, false);
        }
        else
        {
            return dataService.scheduleStockTransaction(stockPurchase);
        }
    }

    @GetMapping("/getStockDetails/{id}")
    public StockDetails getStockDetails(@PathVariable("id") int id){
        StockDetails stock = adminStockRepository.findByStockId(id);
        if(stock != null)
            return stock;
        return null;
    }

    @GetMapping("/getUserStocks/{username}")
    public List<UserTransactionInformation> getUserStocks(@PathVariable("username") String username){
        return dataService.getUserStocks(username);
    }

    @PostMapping(value = "/sellStocks",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object sellStocks(@RequestBody StockPurchase stockPurchase) {
        stockPurchase.setSell(true);
        if(stockPurchase.getExpiry() == null) {
            return dataService.sellOrBuyStock(stockPurchase, true);
        }
        else
        {
            return dataService.scheduleStockTransaction(stockPurchase);
        }
    }

    @GetMapping(value="/getTransactions/{username}")
    public List<UserTransactions> getTransactions(@PathVariable("username") String username){
        return dataService.getTransactions(username);
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
