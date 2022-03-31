package com.stockproject.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class StockPurchase {
   private int stockId;
   private String username;
   private int units;
   private Date expiry;
   private float desirePrice;
   private boolean isSell;
}
