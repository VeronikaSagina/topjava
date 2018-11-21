package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.AuthorizedUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> findBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("findAll for user {}" + AuthorizedUser.id());
        if (startTime == null) {
            startTime = LocalTime.MIN;
        }
        if (endTime == null) {
            endTime = LocalTime.MAX;
        }
        if (startDate == null) {
            startDate = LocalDate.MIN;
        }
        if (endDate == null) {
            endDate = LocalDate.MAX;
        }
        return service.getBetween(AuthorizedUser.id(), startDate, endDate, startTime, endTime);
    }

    public List<MealWithExceed> findAll() {
        LOG.info("findAll for user{}" + AuthorizedUser.id());
        return service.findAll(AuthorizedUser.id());
    }

    public void deleteById(int id) {
        LOG.info("deleteById {} user {}", id, AuthorizedUser.id());
        service.deleteById(id, AuthorizedUser.id());
    }

    public Meal findById(int id) {
        LOG.info("findById {} user {}", id, AuthorizedUser.id());
        return service.findById(id, AuthorizedUser.id());
    }

    public Meal edit(Meal update) {
        LOG.info("edit {} user {}", update, AuthorizedUser.id());
        return service.edit(update, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.id();
        checkNew(meal);
        LOG.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = AuthorizedUser.id();
        checkIdConsistent(meal, id);
        LOG.info("update {} for User {}", meal, userId);
        service.edit(meal, userId);
    }
}
