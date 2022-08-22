package com.example.sigur.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class VirtualData {
private final Date date=new Calendar.Builder().setDate(2022,Calendar.JANUARY,1).build().getTime();
public Date getDate(){
    return (Date)date.clone();
}

@Scheduled(fixedDelay = 100)
private void DateScheduler(){
    date.setTime(date.getTime()+8640000);
}
}
