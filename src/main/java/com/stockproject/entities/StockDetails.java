package com.stockproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Random;

@Getter
@Setter
@Entity
@Table(name ="STOCK_DETAILS")
public class StockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="COMPANY_NAME")
    private String companyName;

    @Column(name="STOCK_TICKER")
    private float stockTicker;

    @Column(name="VOLUME")
    private int volume;

    @Column(name="INITIAL_PRICE")
    private float initialPrice;

    @Column(name="TARGET_PRICE")
    private float targetPrice;

    @Column(name="IS_TARGET_REACHED")
    private boolean isTargetReached;

    @Column(name="LOWEST_PRICE")
    private float lowestPrice;

    @Column(name="HIGHEST_PRICE")
    private float highestPrice;

    @Column(name="STARTING_PRICE")
    private float startingPrice;

    public boolean IsIncreasing()
    {
        return initialPrice < targetPrice;
    }

    public void resetBounds(){
        highestPrice=initialPrice;
        lowestPrice=initialPrice;
        startingPrice = initialPrice;
    }

    public boolean UpdateTargetPrice()
    {
        if(targetPrice == initialPrice)
        {
            isTargetReached = true;
        }
        if(isTargetReached)
        {
            isTargetReached = false;
            Random r = new Random();
            boolean increase = ((r.nextInt() % 10) > 5);
            float incrementValue = r.nextFloat() % 15 + 5;
            if(!increase && targetPrice - incrementValue <0 )
                increase = true;
            if(increase)
                targetPrice += incrementValue;
            else
                targetPrice -= incrementValue;
            return  true;
        }
        return false;
    }

    public void UpdateStockPrice(){
        if(initialPrice > targetPrice)
        {
            initialPrice -= stockTicker;
            if(initialPrice < targetPrice)
            {
                isTargetReached = true;
            }
        }
        else {
            initialPrice += stockTicker;
            if(initialPrice > targetPrice)
            {
                isTargetReached = true;
            }
        }

        lowestPrice= Math.min(lowestPrice,initialPrice);
        highestPrice=Math.max(highestPrice,initialPrice);
    }

}
