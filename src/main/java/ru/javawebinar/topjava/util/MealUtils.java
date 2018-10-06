package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealUtils {
    private static final AtomicInteger currentId = new AtomicInteger();
    private static List<Meal> meals = Arrays.asList(
            new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    private static Map<Integer, Meal> map = meals.stream()
            .collect(Collectors.toConcurrentMap(Meal::getId, Function.identity()));

    public static List<MealWithExceed> getMealWithExceeds() {
        return getFilteredWithExceeded(map.values(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    public static void delete(int id){
        map.remove(id);
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
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    public static void edit(Meal update) {
        if (map.containsKey(update.getId())) {
            map.put(update.getId(), update);
        }
    }

    public static Meal get(int id) {
        return map.get(id);
    }

    public static void create(Meal meal) {
        meal.setId(currentId.incrementAndGet());
        map.put(meal.getId(), meal);
    }
}