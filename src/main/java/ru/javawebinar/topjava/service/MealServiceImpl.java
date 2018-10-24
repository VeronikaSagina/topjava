package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealUtils;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    public List<MealWithExceed> findAll(int userId) throws NotFoundException{
        Collection<Meal> all = repository.getAll();
        List<Meal> meals = all.stream()
                .filter(m -> (m.getUserId() == userId))
                .collect(Collectors.toList());
        return MealUtils.getMealWithExceeds(meals);
    }

    public Meal findById(int id, int userId) throws NotFoundException{
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
            return meal;
        }
        throw new NotFoundException("Not found meal with id = " + id);
    }

    public void deleteById(int id, int userId) throws NotFoundException{
        if (repository.get(id).getUserId() == userId) {
            repository.delete(id);
        } else {
            throw new NotFoundException("Not found meal with id = " + id);
        }
    }

    public Meal edit(Meal update, int userId) throws NotFoundException {
        if (update.getUserId() == userId) {
            return repository.save(update);
        } else {
            throw new NotFoundException("You can't update this meal");
        }
    }
}
