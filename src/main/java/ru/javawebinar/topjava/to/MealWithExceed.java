package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealWithExceed {
    private final Integer id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean exceed;
    private final int userId;

    @JsonCreator
    public MealWithExceed(
            @JsonProperty("userId") int userId,
            @JsonProperty("id") int id,
            @JsonProperty("dateTime") LocalDateTime dateTime,
            @JsonProperty("description") String description,
            @JsonProperty("calories") int calories,
            @JsonProperty("exceed") boolean exceed
    ) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
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

    public Integer getId() {
        return id;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "UserMealWithExceed{" +
                "id=" + id +
                "dateTime=" + dateTime.format(formatter) +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                ", userId=" + userId +
                '}';
    }
}
