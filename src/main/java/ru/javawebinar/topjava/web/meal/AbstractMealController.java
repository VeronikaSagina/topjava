package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.AuthorizedUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    private static final Logger LOG = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> findAll() {
        LOG.info("findAll for user{}" + AuthorizedUser.id());
        return service.findAll(AuthorizedUser.id());
    }

    public List<MealWithExceed> findAll(String startDate, String endDate, String startTime, String endTime) {
        LocalDate startD = !StringUtils.isEmpty(startDate) ? DateTimeUtil.parseLocalDate(startDate) : LocalDate.MIN;
        LocalDate endD = !StringUtils.isEmpty(endDate) ? DateTimeUtil.parseLocalDate(endDate) : LocalDate.MAX;
        LocalTime startT = !StringUtils.isEmpty(startTime) ? DateTimeUtil.parseLocalTime(startTime) : LocalTime.MIN;
        LocalTime endT = !StringUtils.isEmpty(endTime) ? DateTimeUtil.parseLocalTime(endTime) : LocalTime.MAX;
        return service.getBetween(AuthorizedUser.id(), startD, endD, startT, endT);
    }

    public List<MealWithExceed> findAll(LocalDateTime start, LocalDateTime end) {
        return service.getBetween(AuthorizedUser.id(), start, end);
    }

    public void delete(int id) {
        LOG.info("delete meal for user{}", AuthorizedUser.id());
        service.deleteById(id, AuthorizedUser.id());
    }

    public Meal showCreateMeal() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
    }

    public Meal save(Meal meal) {
        checkNew(meal);
        LOG.info("create meal for user{}", AuthorizedUser.id());
        return service.save(meal, AuthorizedUser.id());
    }

    public Meal update(Meal meal, int id) {
        checkIdConsistent(meal, id);
        LOG.info("update meal id:{} for user{}", meal.getId(), AuthorizedUser.id());
        return service.edit(meal, AuthorizedUser.id());
    }

    public Meal getOne(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return service.findById(id, userId);
    }
}
