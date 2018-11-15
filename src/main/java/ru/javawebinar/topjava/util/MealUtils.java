package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.AuthorizedUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealUtils {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;
    public static List<Meal> MEALS = new ArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2018, Month.OCTOBER, 25, 8, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2018, Month.OCTOBER, 25, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2018, Month.OCTOBER, 25, 19, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2018, Month.SEPTEMBER, 1, 8, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2018, Month.SEPTEMBER, 1, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2018, Month.SEPTEMBER, 1, 19, 0), "Ужин", 510)
    ));

    public static List<MealWithExceed> getMealWithExceeds(Collection<Meal> all) {
        return getFilteredWithExceeded(all, LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
    }

    public static void main(String[] args) {

    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (DateTimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(AuthorizedUser.id(), meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}