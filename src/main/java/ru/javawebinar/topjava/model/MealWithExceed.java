package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 11.01.2015.
 */
public class MealWithExceed {
    private static final DateTimeFormatter formater= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean exceed;
    private static AtomicInteger count=new AtomicInteger();
    private int id;

    private String stringDateTime;

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        id=count.getAndIncrement();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.stringDateTime=getStringDateTime();
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public String getStringDateTime() {

        return formater.format(dateTime);
    }

    public int getId() {
        return id;
    }
}
