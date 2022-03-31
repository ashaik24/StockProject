package com.stockproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name ="MARKET_HOURS")
public class MarketHours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="START_TIME")
    private String startTime;

    @Column(name="END_TIME")
    private String endTime;

    public LocalTime GetLocalStartTime(){
        return GetFormattedTime(startTime);
    }

    public LocalTime GetLocalEndTime(){
        return GetFormattedTime(endTime);
    }

    private LocalTime GetFormattedTime(String time)
    {
        DateTimeFormatter f = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
        return LocalDateTime.parse(time,f).toLocalTime();
    }

}
