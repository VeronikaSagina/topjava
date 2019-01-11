package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    private static final Logger LOG = getLogger(AbstractMealController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> findAll() {
        LOG.info("findAll for user{}" + AuthorizedUser.id());
        return service.findAll(AuthorizedUser.id());
    }

    public List<MealWithExceed> findAll(String startDate, String endDate, String startTime, String endTime) {
        if (isAllEmpty(startDate, endDate, startTime, endTime)) {
            return findAll();
        }
        LocalDate startD = !StringUtils.isEmpty(startDate) ? DateTimeUtil.parseLocalDate(startDate) : LocalDate.of(1999, 1, 1);
        LocalDate endD = !StringUtils.isEmpty(endDate) ? DateTimeUtil.parseLocalDate(endDate) : LocalDate.of(2100, 1, 1);
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

    public Meal save(MealTo meal) {
        LOG.info("create meal for user{}", AuthorizedUser.id());
        checkNew(meal);
        Meal meal1 = new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories());
        return service.save(meal1, AuthorizedUser.id());
    }

    public void update(MealTo meal, Integer id) {
        LOG.info("update meal id:{} for user{}", meal.getId(), AuthorizedUser.id());
        checkIdConsistent(meal, id);
        service.edit(meal, AuthorizedUser.id());
    }

    public MealTo getOne(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        return MealUtils.createMealToFromMeal(service.findById(id, userId));
    }

    private boolean isAllEmpty(String startDate, String endDate, String startTime, String endTime) {
        return StringUtils.isEmpty(startDate)
                && StringUtils.isEmpty(endDate)
                && StringUtils.isEmpty(startTime)
                && StringUtils.isEmpty(endTime);
    }
}
