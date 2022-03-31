package com.stockproject.repository;

import com.stockproject.entities.ScheduledTransaction;
import com.stockproject.entities.StockDetails;
import com.stockproject.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories()
public interface ScheduledTransactionRepository extends CrudRepository<ScheduledTransaction,Integer> {
    @Query("select st from ScheduledTransaction st where st.user= :user and st.dateCompleted is null and st.dateCancelled is null")
    public List<ScheduledTransaction> findAllByUserAndDates(@Param("user") User user);

    @Query("select st from ScheduledTransaction st where st.id= :id")
    public ScheduledTransaction findByTransactionId(@Param("id") long id);

}
