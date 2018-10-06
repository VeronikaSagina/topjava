package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealUtils;

import java.util.List;

public class MealService {
    public List<MealWithExceed> findAll() {
        return MealUtils.getMealWithExceeds();
    }

    public Meal findById(int id) {
        return MealUtils.get(id);
    }

    public void deleteById(int id){
        MealUtils.delete(id);
    }

    public void edit(Meal update){
        MealUtils.edit(update);
    }

    public void create(Meal meal){
        MealUtils.create(meal);
    }
}
