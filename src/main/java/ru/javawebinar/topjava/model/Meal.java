package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal {
    private static AtomicInteger count=new AtomicInteger();
    private int id;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        id=count.getAndIncrement();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }


    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this.id=id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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

    public int getId() {
        return id;
    }
}
