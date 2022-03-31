package com.stockproject.objects;

import com.stockproject.entities.ScheduledTransaction;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Getter@Setter
public class ScheduledTransactionClientObject {
    private long id;
    private String companyName;
    private Boolean isSelling;
    private float desiredPrice;
    private int desiredUnits;
    private Date expiryDate;

    public ScheduledTransactionClientObject(ScheduledTransaction scheduledTransaction)
    {
        this.id = scheduledTransaction.getId();
        this.companyName = scheduledTransaction.getStockDetails().getCompanyName();
        this.isSelling = scheduledTransaction.getIsSelling();
        this.desiredPrice = scheduledTransaction.getDesiredPrice();
        this.desiredUnits = scheduledTransaction.getDesiredUnits();
        this.expiryDate = scheduledTransaction.getExpiryDate();
    }
}
