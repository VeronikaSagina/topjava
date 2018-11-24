package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

@Repository
@Transactional(readOnly = true)
public class jpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            em.persist(meal);
        } else {
            em.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("user", user)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        try {
            User user = em.getReference(User.class, userId);
            return em.createNamedQuery(Meal.GET, Meal.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        User user = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.GET_ALL_MEALS, Meal.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        User user = em.getReference(User.class, userId);
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("startDate", LocalDateTime.of(startDate, startTime))
                .setParameter("endDate", LocalDateTime.of(endDate, endTime))
                .setParameter("user", user)
                .getResultList();
    }
}
