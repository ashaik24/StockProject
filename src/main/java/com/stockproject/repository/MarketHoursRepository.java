package com.stockproject.repository;

import com.stockproject.entities.MarketHours;
import com.stockproject.entities.StockDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface MarketHoursRepository extends CrudRepository<MarketHours, Integer> {
    @Query("select mh from MarketHours mh")
    public MarketHours findTop();
}
