package com.stockproject.service;

import com.stockproject.entities.MarketHours;
import com.stockproject.entities.StockDetails;
import com.stockproject.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * Service class to handle all admin related logic and crud operation calls
 */
public class AdminStockService {

    private final AdminStockRepository adminStockRepository;
    private final MarketHoursRepository marketHoursRepository;

    public AdminStockService(AdminStockRepository adminStockRepository, UserTransactionsRepository userTransactionsRepository,
                       UserRepository registrationRepository, BalanceRepository balanceRepository, ScheduledTransactionRepository scheduledTransactionRepository,
                       MarketHoursRepository marketHoursRepository) {
        this.adminStockRepository = adminStockRepository;
        this.marketHoursRepository=marketHoursRepository;
    }
    public List<StockDetails> save(StockDetails stockDetails){
        stockDetails.setTargetPrice(stockDetails.getInitialPrice());
        adminStockRepository.save(stockDetails);
        return adminStockRepository.findAll();
    }

    public MarketHours updateMarketHours(MarketHours marketHours){
        return marketHoursRepository.save(marketHours);
    }

    public MarketHours getMarketHours(){
        return marketHoursRepository.findTop();
    }

}
