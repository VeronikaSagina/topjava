package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealUtils {
    private MealUtils() {
    }

    public static List<MealWithExceed> getMealWithExceeds(Collection<Meal> all, int caloriesPerDay) {
        return getFilteredWithExceeded(all, caloriesPerDay);
    }

    private static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), LocalTime.MIN, LocalTime.MAX))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(AuthorizedUser.id(), meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    public static Meal updateFromMealTo(Meal meal, MealTo mealTo) {
        meal.setDateTime(mealTo.getDateTime());
        meal.setDescription(mealTo.getDescription());
        meal.setCalories(mealTo.getCalories());
        return meal;
    }

    public static MealTo createMealToFromMeal(Meal meal) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }
}