package ru.javawebinar.topjava.service.datajpa;

import mockit.MockUp;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.MealUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Before
    public void setUp() {
        service.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Autowired
    protected JpaUtil jpaUtil;

    @Test
    public void getUserByIdWithMeals() {
        MockUp<AuthorizedUser> mockUp = TestUtil.getMockUp(USER_ID);
        try {
            User userWithMeal = service.getWithMeal(USER_ID);
            List<Meal> expected = Arrays.asList(MEAL_TEST_1, MEAL_TEST_2, MEAL_TEST_3, MEAL_TEST_4, MEAL_TEST_5, MEAL_TEST_6);
            expected.sort(Comparator.comparing(Meal::getDateTime).reversed());
            UserTestData.MATCHER.assertEquals(USER, userWithMeal);
            MealTestData.MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(expected, userWithMeal.getCaloriesPerDay()), MealUtils.getMealWithExceeds(userWithMeal.getMeals(), userWithMeal.getCaloriesPerDay()));
        } finally {
            mockUp.tearDown();
        }
    }
}
