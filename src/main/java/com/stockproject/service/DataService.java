package com.stockproject.service;

import com.stockproject.repository.*;
import com.stockproject.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class DataService {

    private final AdminStockRepository adminStockRepository;
    private final UserTransactionsRepository userTransactionsRepository;
    private final UserRepository registrationRepository;
    private final BalanceRepository balanceRepository;
    private final ScheduledTransactionRepository scheduledTransactionRepository;
    private final MarketHoursRepository marketHoursRepository;
    @Autowired
    public DataService(AdminStockRepository adminStockRepository, UserTransactionsRepository userTransactionsRepository,
                       UserRepository registrationRepository, BalanceRepository balanceRepository,ScheduledTransactionRepository scheduledTransactionRepository,
                       MarketHoursRepository marketHoursRepository) {
        this.adminStockRepository = adminStockRepository;
        this.userTransactionsRepository = userTransactionsRepository;
        this.registrationRepository = registrationRepository;
        this.balanceRepository = balanceRepository;
        this.scheduledTransactionRepository=scheduledTransactionRepository;
        this.marketHoursRepository=marketHoursRepository;
    }

    public List<StockDetails> save(StockDetails stockDetails){
        stockDetails.setTargetPrice(stockDetails.getInitialPrice());
        adminStockRepository.save(stockDetails);
        return adminStockRepository.findAll();
    }

    public List<StockDetails> getStocks(){
        return adminStockRepository.findAll();
    }


    public User getUserDetails(String username){
        User user = registrationRepository.findByUsername(username);
        return user;
    }

    public List<UserTransactionInformation> getUserStocks(String username){
        List<UserTransactions> list = userTransactionsRepository.findAllByUser(getUserDetails(username));
        Map<Integer,UserTransactionInformation> map = new HashMap<>();
        for(UserTransactions transaction : list)
        {
            int stockId = transaction.getStockDetails().getId();
            if(map.containsKey(stockId))
            {
                int cCount = map.get(stockId).getUnits();
                if(transaction.getIsSold()) {
                    map.get(stockId).setUnits(cCount - transaction.getUnits());
                }
                else
                {
                    map.get(stockId).setUnits(cCount + transaction.getUnits());
                }
            }
            else
            {
                UserTransactionInformation uInformation = new UserTransactionInformation();
                uInformation.setStockId(stockId);
                uInformation.setIncrease(transaction.getStockDetails().IsIncreasing());
                uInformation.setUnits(transaction.getUnits() * (transaction.getIsSold()?-1:1));
                uInformation.setCompanyName(transaction.getStockDetails().getCompanyName());
                uInformation.setStockPrice(transaction.getStockDetails().getInitialPrice());
                map.put(stockId,uInformation);
            }
        }
        List<UserTransactionInformation> returnList = new ArrayList<>();
        for(var p : map.entrySet())
        {
            if(p.getValue().getUnits() > 0)
                returnList.add(p.getValue());
        }
        return returnList;
    }

    public UserTransactions sellOrBuyStock(StockPurchase stockPurchase,boolean isSelling){
        ScheduledTransaction sTransaction = new ScheduledTransaction();
        sTransaction.setIsSelling(isSelling);
        sTransaction.setStockDetails(adminStockRepository.findByStockId(stockPurchase.getStockId()));
        sTransaction.setUser(getUserDetails(stockPurchase.getUsername()));
        sTransaction.setDesiredUnits(stockPurchase.getUnits());
        return performScheduledTransaction(sTransaction,false);
    }

    public ScheduledTransaction scheduleStockTransaction(StockPurchase stockPurchase)
    {
        ScheduledTransaction scheduledTransaction = new ScheduledTransaction();
        scheduledTransaction.setDesiredUnits(stockPurchase.getUnits());
        scheduledTransaction.setStockDetails(adminStockRepository.findByStockId(stockPurchase.getStockId()));
        scheduledTransaction.setUser(getUserDetails(stockPurchase.getUsername()));
        scheduledTransaction.setDesiredPrice(stockPurchase.getDesirePrice());
        scheduledTransaction.setIsSelling(stockPurchase.isSell());
        scheduledTransaction.setExpiryDate(stockPurchase.getExpiry());
        scheduledTransaction.setDateAdded(new Date());
        return scheduledTransactionRepository.save(scheduledTransaction);
    }

    public List<UserTransactions> getTransactions(String username){
         return userTransactionsRepository.findAllByUser(getUserDetails(username));
    }

    public MarketHours getMarketHours(){
        return marketHoursRepository.findTop();
    }

    public MarketHours updateMarketHours(MarketHours marketHours){
        return marketHoursRepository.save(marketHours);
    }

    public boolean inScheduledTime(){
        if(marketHoursRepository.findTop() == null)
            return  true;
        LocalTime startTime = marketHoursRepository.findTop().GetLocalStartTime();
        LocalTime endTime = marketHoursRepository.findTop().GetLocalEndTime();
        LocalTime currentTIme = LocalTime.now();
        return !isWeekend() && (currentTIme.isAfter(startTime) && currentTIme.isBefore(endTime));
    }

    public boolean isWeekend(){
        LocalDate now = LocalDate.now();
        DayOfWeek day = DayOfWeek.of(now.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    public UserTransactions performScheduledTransaction(ScheduledTransaction sTransaction,boolean isScheduled)
    {
        StockDetails stock = sTransaction.getStockDetails();
        User user = sTransaction.getUser();
        if(!isScheduled)
        {
            sTransaction.setDesiredPrice(stock.getInitialPrice());
        }
        float balanceRequired = sTransaction.getDesiredPrice() * sTransaction.getDesiredUnits();
        if(!isScheduled || (isScheduled && !sTransaction.IsExpired() && stock.getInitialPrice() == sTransaction.getDesiredPrice()))
        {
            if(!sTransaction.getIsSelling() && (stock.getVolume() < sTransaction.getDesiredUnits() || user.getBalance() < balanceRequired))
            {
                return null;
            }
            //Add user transaction
            UserTransactions userTransactions = new UserTransactions();
            userTransactions.setUser(sTransaction.getUser());
            userTransactions.setTransactionDate(new Date());
            userTransactions.setStockPrice(stock.getInitialPrice());
            userTransactions.setStockDetails(stock);
            userTransactions.setIsSold(sTransaction.getIsSelling());
            userTransactions.setUnits(sTransaction.getDesiredUnits());
            userTransactionsRepository.save(userTransactions);

            if(sTransaction.getIsSelling())
            {
                stock.setVolume(stock.getVolume() + sTransaction.getDesiredUnits());
            }
            else
            {
                stock.setVolume(stock.getVolume() - sTransaction.getDesiredUnits());
            }
            adminStockRepository.save(stock);
            //modify stock volume
            if(isScheduled) {
                sTransaction.setDateCompleted(new Date());
                scheduledTransactionRepository.save(sTransaction);
            }
            //Complete transaction

            //Add or remove balance from user
            if(sTransaction.getIsSelling())
                user.setBalance(user.getBalance() + balanceRequired);
            else
                user.setBalance(user.getBalance() - balanceRequired);

            registrationRepository.save(user);

            //Adding balance transaction to table.
            BalanceTransaction balanceTransaction = new BalanceTransaction();
            balanceTransaction.setCashValue(balanceRequired);
            balanceTransaction.setUser(user);
            balanceTransaction.setAddedDate(new Date());
            balanceTransaction.setTopUp(false);
            balanceTransaction.setAdded(sTransaction.getIsSelling());
            balanceRepository.save(balanceTransaction);
            return userTransactions;
        }
        return null;
    }

    public List<ScheduledTransaction> getAllScheduledTransactions() {
        return (List<ScheduledTransaction>) scheduledTransactionRepository.findAll();
    }

    public List<ScheduledTransaction> getUserScheduledTransactions(String userid)
    {
        return (List<ScheduledTransaction>)scheduledTransactionRepository.findAllByUserAndDates(getUserDetails(userid));
    }

    public ScheduledTransaction getScheduledTransaction(long id)
    {
        return scheduledTransactionRepository.findByTransactionId(id);
    }

    public boolean deleteScheduledTransaction(long id)
    {
        ScheduledTransaction scheduledTransaction = getScheduledTransaction(id);
        if(scheduledTransaction != null)
        {
            scheduledTransaction.setDateCancelled(new Date());
            scheduledTransactionRepository.save(scheduledTransaction);
            return true;
        }
        return false;
    }
}
