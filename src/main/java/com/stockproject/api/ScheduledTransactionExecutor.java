package com.stockproject.api;

import com.stockproject.StockProjectApplication;
import com.stockproject.entities.*;
import com.stockproject.repository.*;
import com.stockproject.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduledTransactionExecutor {

    private final DataService dataService;
    @Autowired
    public ScheduledTransactionExecutor(DataService dataService) {
        this.dataService = dataService;

        Runnable updateStockPrices = new Runnable() {
            public void run() {

                List<ScheduledTransaction> scheduledTransactionList = dataService.getAllScheduledTransactions();
                for (ScheduledTransaction sTransaction : scheduledTransactionList) {
                    dataService.performScheduledTransaction(sTransaction,true);
                }
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updateStockPrices, 0, 1, TimeUnit.SECONDS);
    }

}
