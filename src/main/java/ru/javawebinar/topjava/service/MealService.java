package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.MealUtils;

import java.util.Collection;
import java.util.List;

public class MealService {
    private InMemoryMealRepositoryImpl repository = new InMemoryMealRepositoryImpl();

    public List<MealWithExceed> findAll() {
        Collection<Meal> all = repository.getAll();
        return MealUtils.getMealWithExceeds(all);
    }

    public Meal findById(int id) {
        return repository.get(id);
    }

    public void deleteById(int id) {
        repository.delete(id);
    }

    public void edit(Meal update) {
        repository.save(update);
    }
}
