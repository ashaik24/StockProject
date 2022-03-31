package com.stockproject.repository;

import com.stockproject.entities.StockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminStockRepository extends JpaRepository<StockDetails,Integer> {
    @Query("select sd from StockDetails sd where sd.id= :id")
    public StockDetails findByStockId(@Param("id") int id);
}
