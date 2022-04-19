package com.stockproject.service;

import com.stockproject.repository.BalanceRepository;
import com.stockproject.repository.UserRepository;
import com.stockproject.objects.AddMoney;
import com.stockproject.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockproject.entities.BalanceTransaction;

import java.util.Date;

@Service
/**
 * Service class to handle all balance related logic and crud operations calls
 */
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final UserRepository registrationRepository;

    @Autowired
    public BalanceService(BalanceRepository balanceRepository, UserRepository registrationRepository) {
        this.balanceRepository = balanceRepository;
        this.registrationRepository=registrationRepository;
    }

    public BalanceTransaction addMoney(AddMoney addMoney) {
        User user = getUserDetails(addMoney.getUsername());
        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setCashValue(addMoney.getAmount());
        balanceTransaction.setUser(user);
        balanceTransaction.setAddedDate(new Date());
        balanceTransaction.setTopUp(true);
        balanceTransaction.setAdded(true);
        balanceRepository.save(balanceTransaction);
        user.setBalance(user.getBalance() + addMoney.getAmount());
        registrationRepository.save(user);
        return balanceTransaction;
    }

    public BalanceTransaction withDrawMoney(AddMoney addMoney) {
        if(getAvailableCash(addMoney.getUsername()) < addMoney.getAmount())
            return null;
        User user = getUserDetails(addMoney.getUsername());
        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setCashValue(addMoney.getAmount());
        balanceTransaction.setUser(user);
        balanceTransaction.setAddedDate(new Date());
        balanceTransaction.setTopUp(true);
        balanceTransaction.setAdded(false);
        balanceRepository.save(balanceTransaction);
        user.setBalance(user.getBalance() - addMoney.getAmount());
        registrationRepository.save(user);
        return balanceTransaction;
    }

    public float getAvailableCash(String username) {
        if(username.isEmpty() || username.isBlank()) {
            return 0;
        }
        else {
            User user = getUserDetails(username);
            return user.getBalance();
        }

    }

    public User getUserDetails(String username){
        return registrationRepository.findByUsername(username);
    }
}
