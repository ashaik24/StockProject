package com.stockproject;

import com.stockproject.api.ScheduledTransactionExecutor;
import com.stockproject.api.StockPriceGenerator;
import com.stockproject.entities.*;
import com.stockproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@SpringBootApplication(scanBasePackages = {"com.stockproject"})
@EnableJpaRepositories(basePackages = {"com.stockproject.repository"})
public class StockProjectApplication {
    public StockPriceGenerator stockPriceGenerator;
    public ScheduledTransactionExecutor scheduledTransactionExecutor;

    @Autowired
    public  StockProjectApplication(StockPriceGenerator stockPriceGenerator,ScheduledTransactionExecutor scheduledTransactionExecutor){
        this.stockPriceGenerator = stockPriceGenerator;
        this.scheduledTransactionExecutor = scheduledTransactionExecutor;
    }

    public static void main(String[] args) {
        SpringApplication.run(StockProjectApplication.class, args);
    }



}
