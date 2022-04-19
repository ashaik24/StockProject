package com.stockproject.repository;

import com.stockproject.entities.StockDetails;
import com.stockproject.entities.User;
import com.stockproject.entities.UserTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionsRepository extends JpaRepository<UserTransactions,Integer> {
    @Query("from UserTransactions u where u.user = :user")
    public List<UserTransactions> findAllByUser(@Param("user") User user);

    @Query("from UserTransactions u where u.user = :user and u.stockDetails= :stockDetails")
    public UserTransactions findAllByUserAndStock(@Param("user") User user, @Param("stockDetails")StockDetails stockDetails);

}
