package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    List<MealWithExceed> findAll(int userId);

    List<MealWithExceed> getBetween(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    Meal getMealWithUser(int mealId,  int userId);

    Meal findById(int id, int userId);

    void deleteById(int id, int userId);

    Meal edit(Meal update, int userId);
    Meal save(Meal update, int userId);
}
