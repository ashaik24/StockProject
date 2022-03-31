package com.stockproject.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name ="SCHEDULED_TRANSACTIONS")
public class ScheduledTransaction {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @OneToOne
        @NotNull
        StockDetails stockDetails;

        @ManyToOne
        @NotNull
        private User user;

        @Column(name="IS_SELLING")
        private Boolean isSelling;

        @Column(name="DESIRED_PRICE")
        private float desiredPrice;

        @Column(name="DESIRED_UNITS")
        private int desiredUnits;

        @Column(name="EXPIRY_DATE")
        private Date expiryDate;

        @Column(name="DATE_ADDED")
        private Date dateAdded;

        @Column(name="DATE_CANCELLED")
        private Date dateCancelled;

        @Column(name="DATE_COMPLETED")
        private Date dateCompleted;

        public boolean IsExpired()
        {
                if(dateCompleted == null) {
                        Date now = new Date();
                        return now.after(expiryDate);
                }
                return true;
        }
}
