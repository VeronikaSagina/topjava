package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getMealWithUser(){
        Meal actual = service.getMealWithUser(100002, UserTestData.USER_ID);
        Meal expected = MealTestData.MEAL_TEST_1;
        expected.setUser(UserTestData.USER);
        System.out.println("_______________________");
        System.out.println(expected);
        System.out.println(actual);
        System.out.println("_______________________");
        MealTestData.MATCHER_MEAL.assertEquals(expected, actual);
    }

}
