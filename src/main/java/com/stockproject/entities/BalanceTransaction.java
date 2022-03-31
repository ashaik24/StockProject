package com.stockproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name ="BALANCE_TRANSACTION")
public class BalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user;

    @Column(name="CASH_VALUE")
    private float cashValue;

    @Column(name="ADDED_DATE")
    private Date addedDate;

    @Column(name="IS_TOP_UP")
    private boolean isTopUp;

    @Column(name="IS_ADDED")
    private boolean isAdded;

}
