package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.MealUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getUserByIdWithMeals() {
        User withMeal = service.getWithMeal(USER_ID);
        List<Meal> expected = Arrays.asList(MEAL_TEST_1, MEAL_TEST_2, MEAL_TEST_3, MEAL_TEST_4, MEAL_TEST_5, MEAL_TEST_6);
        expected.sort(Comparator.comparing(Meal::getDateTime).reversed());
        UserTestData.MATCHER.assertEquals(USER, withMeal);
        MealTestData.MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(expected), MealUtils.getMealWithExceeds(withMeal.getMeals()));
    }
}
