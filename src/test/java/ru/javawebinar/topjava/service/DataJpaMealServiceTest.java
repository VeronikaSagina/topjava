package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealWithUser(){
        Meal actual = service.getMealWithUser(100002);
        Meal expected = MealTestData.MEAL_TEST_1;
        expected.setUser(UserTestData.USER);
        MealTestData.MATCHER_MEAL.assertEquals(expected, actual);
    }

}
