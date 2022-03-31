package com.stockproject.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name ="USER_TRANSACTONS")
public class UserTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private StockDetails stockDetails;

    @Column(name="UNITS")
    private int units;

    @Column(name="IS_SOLD")
    private Boolean isSold;

    @Column(name="STOCK_PRICE")
    private float stockPrice;

    @Column(name="TRANSACTION_DATE")
    private Date transactionDate;
}
