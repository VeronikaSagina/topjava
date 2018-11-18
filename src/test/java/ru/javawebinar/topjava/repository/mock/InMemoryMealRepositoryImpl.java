package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//@Primary - Если есть несколько бинов удовлетворяющих условию (например имплементирующих нужный интерфейс,
//то будет использован этот бин. Также смотри @Qualifier
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static Map<Integer, Map<Integer, Meal>> mealMapRepository = new ConcurrentHashMap<>();
    private static final AtomicInteger currentId = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private static List<Meal> MEALS = new ArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2018, Month.OCTOBER, 25, 8, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2018, Month.OCTOBER, 25, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2018, Month.OCTOBER, 25, 19, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2018, Month.SEPTEMBER, 1, 8, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2018, Month.SEPTEMBER, 1, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2018, Month.SEPTEMBER, 1, 19, 0), "Ужин", 510)
    ));

    {
        MEALS.forEach(um -> save(um, InMemoryUserRepositoryImpl.USER_ID));
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510), InMemoryUserRepositoryImpl.ADMIN_ID);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), InMemoryUserRepositoryImpl.ADMIN_ID);
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("+++ PreDestroy");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal);
        if (meal.isNew()) {
            meal.setId(currentId.incrementAndGet());
        } else if (get(meal.getId(), userId) == null) {
            return null;
        }
        Map<Integer, Meal> mealMap = mealMapRepository.computeIfAbsent(userId, ConcurrentHashMap::new);
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("Delete " + id);
        Map<Integer, Meal> mealMap = mealMapRepository.get(userId);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = mealMapRepository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("Get all meals for user {}" + userId);
        Map<Integer, Meal> mealMap = mealMapRepository.get(userId);
        List<Meal> values = new ArrayList<>(mealMap.values());
        values.sort(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()));
        return values;
    }

    @Override
    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        List<Meal> values = getAll(userId);
        return values.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
