package com.stockproject.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserTransactionInformation {
    private String companyName;
    private int stockId;
    private int units;
    private float stockPrice;
    private boolean increase;
}
