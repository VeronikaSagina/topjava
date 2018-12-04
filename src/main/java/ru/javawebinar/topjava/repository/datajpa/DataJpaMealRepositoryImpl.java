package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collection;


@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    private final CrudMealRepository repository;

    private final CrudUserRepository userRepository;

    @Autowired
    public DataJpaMealRepositoryImpl(CrudMealRepository repository, CrudUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = userRepository.getOne(userId);
        meal.setUser(user);
        return repository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.deleteMealByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.getByIdAndUserId(id, userId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.findAllMealsByUser_id(userId, Sort.by(Sort.Direction.DESC, "dateTime"));
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return repository.findMealsByUserIdAndDateTimeBetweenOrderByDateTimeDesc(userId, startDateTime, endDateTime);
    }

    @Override
    public Meal getMealWithUser(int mealId) {
        return repository.findById(mealId);
    }

}
