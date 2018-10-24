package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//@Primary - Если есть несколько бинов удовлетворяющих условию (например имплементирующих нужный интерфейс,
//то будет использован этот бин. Также смотри @Qualifier
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static Map<Integer, Meal> mealMapRepository = new ConcurrentHashMap<>();
    private static final AtomicInteger currentId = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    {
        MealUtils.MEALS.forEach(this::save);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Meal save(Meal meal) {
        LOG.info("Save " + meal);
        if (meal.isNew()) {
            meal.setId(currentId.incrementAndGet());
        }
        mealMapRepository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        LOG.info("Delete " + id);
        mealMapRepository.remove(id);
    }

    @Override
    public Meal get(int id) {
        LOG.info("Get " + id);
        return mealMapRepository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        LOG.info("Get all meals for user.");
        List<Meal> values = new ArrayList<>(mealMapRepository.values());
        values.sort(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()));
        return values;
    }
}
