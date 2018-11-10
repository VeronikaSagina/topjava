package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealUtils;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealWithExceed> findAll(int userId) throws NotFoundException {
        Collection<Meal> all = repository.getAll(userId);
        return MealUtils.getMealWithExceeds(all);
    }

    @Override
    public List<MealWithExceed> getBetween(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> between = repository.getBetween(startDate, endDate, startTime, endTime, userId);
        return MealUtils.getMealWithExceeds(between);
    }

    public Meal findById(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void deleteById(int id, int userId) throws NotFoundException {
       checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal edit(Meal update, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.save(update, userId), update.getId());
    }

    @Override
    public Meal save(Meal update, int userId) {
        return repository.save(update, userId);
    }
}
