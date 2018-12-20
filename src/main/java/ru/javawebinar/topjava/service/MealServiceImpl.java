package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealUtils;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealWithExceed> findAll(int userId) throws NotFoundException {
        Collection<Meal> all = repository.getAll(userId);
        return MealUtils.getMealWithExceeds(all);
    }

    @Override
    public List<MealWithExceed> getBetween(int userId, LocalDate startDate,
                                           LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Assert.noNullElements(new Object[]{startDate, endDate, startTime, endTime}, "date or time must not be null");
        Collection<Meal> between = repository.getBetween(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), userId);
        return MealUtils.getMealWithExceeds(between);
    }

    @Override
    public List<MealWithExceed> getBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Assert.noNullElements(new Object[]{startDateTime, endDateTime}, "date or time must not be null");
        Collection<Meal> meals = repository.getBetween(startDateTime, endDateTime, userId);
        return MealUtils.getMealWithExceeds(meals);
    }

    @Override
    public Meal getMealWithUser(int mealId, int userId) {
        return repository.getMealWithUser(mealId, userId);
    }

    public Meal findById(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Transactional
    public void deleteById(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Transactional
    public Meal edit(Meal update, int userId) throws NotFoundException {
        Assert.notNull(update, "meal must not be null");
        return checkNotFoundWithId(repository.save(update, userId), update.getId());
    }

    @Transactional
    public Meal save(Meal update, int userId) {
        Assert.notNull(update, "meal must not be null");
        return repository.save(update, userId);
    }
}
