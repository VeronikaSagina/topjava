package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

public interface MealService {
    List<MealWithExceed> findAll(int userId);

    Meal findById(int id, int userId);

    void deleteById(int id, int userId);

    Meal edit(Meal update, int userId);
}
