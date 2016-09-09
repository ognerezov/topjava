package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private static final DateTimeFormatter formater= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    private String stringDateTime;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.stringDateTime=getStringDateTime();
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
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
}
