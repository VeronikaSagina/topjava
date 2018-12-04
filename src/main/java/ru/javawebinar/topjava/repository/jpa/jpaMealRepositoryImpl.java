package ru.javawebinar.topjava.repository.jpa;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
@Repository
public class jpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            em.persist(meal);
        } else {
            if (get(meal.getId(), userId) == null){
                return null;
            }
            em.merge(meal);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("user_id", userId)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
       Meal meal = em.find(Meal.class, id);
       return meal != null && meal.getUser().getId() == userId ? meal :null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL_MEALS, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("startDate", startDateTime)
                .setParameter("endDate", endDateTime)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public Meal getMealWithUser(int mealId) {
        return null;
    }
}
