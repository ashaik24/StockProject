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

    private final AdminStockRepository adminStockRepository;
    private final DataService dataService;
    private boolean isRunning = false;
    private int priceGenerationInterval = 1; //In seconds

    /**
     * This class handles random price generation for each stock while current time is a valid stock activity time
     * that admin defined.
     * @param adminStockRepository
     * @param dataService
     */
    @Autowired
    public StockPriceGenerator(AdminStockRepository adminStockRepository,DataService dataService){
        this.adminStockRepository=adminStockRepository;
        this.dataService = dataService;
        GenerateAllStockPrices();
    }

    /**
     * This function runs a ScheduleExecutorService that continuously update all stocks prices.
     */
    private void GenerateAllStockPrices()
    {
        Runnable updateStockPrices = new Runnable() {
            public void run() {
                if(dataService.inScheduledTime()) {
                    List<StockDetails> stockDetailsList = adminStockRepository.findAll();
                    for (StockDetails stock : stockDetailsList) {
                       GenerateRandomPriceForStock(stock);
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
        executor.scheduleAtFixedRate(updateStockPrices, 0, priceGenerationInterval, TimeUnit.SECONDS);
    }

    /**
     * This function updates current price, generates new target stock price when target price reached.
     * @param stock
     */
    private void GenerateRandomPriceForStock(StockDetails stock)
    {
        if(!isRunning)
        {
            stock.resetBounds();
        }
        stock.UpdateTargetPrice();
        stock.UpdateStockPrice();
        adminStockRepository.save(stock);
    }


}
