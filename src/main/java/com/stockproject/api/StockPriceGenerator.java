package com.stockproject.api;

import com.stockproject.StockProjectApplication;
import com.stockproject.entities.StockDetails;
import com.stockproject.repository.AdminStockRepository;
import com.stockproject.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class StockPriceGenerator {
    private  final AdminStockRepository adminStockRepository;
    private final DataService dataService;
    private  boolean isRunning = false;
    @Autowired
    public StockPriceGenerator(AdminStockRepository adminStockRepository,DataService dataService){
        this.adminStockRepository=adminStockRepository;
        this.dataService = dataService;
        Runnable updateStockPrices = new Runnable() {
            public void run() {
                if(dataService.inScheduledTime()) {
                    List<StockDetails> stockDetailsList = adminStockRepository.findAll();
                    for (StockDetails stock : stockDetailsList) {
                        if(!isRunning)
                        {
                            stock.resetBounds();;
                        }
                        stock.UpdateTargetPrice();
                        stock.UpdateStockPrice();
                        adminStockRepository.save(stock);
                    }
                    isRunning = true;
                }
                else
                {
                    isRunning = false;
                }
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updateStockPrices, 0, 1, TimeUnit.SECONDS);
    }


}
