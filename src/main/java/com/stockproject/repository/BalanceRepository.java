package com.stockproject.repository;

import com.stockproject.entities.User;
import com.stockproject.entities.BalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<BalanceTransaction,Integer>  {
    @Query("select u from BalanceTransaction u where u.user = :user")
    public BalanceTransaction findByUser(@Param("user") User user);

}
