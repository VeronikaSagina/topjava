package ru.javawebinar.topjava.service;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealUtils;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AuthorizedUser;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public abstract class AbstractMealServiceTest extends DbTest {


    @Autowired
    protected MealService service;

    @Test
    public void testGetAll() {
        new MockUp<AuthorizedUser>() {
            @Mock
            public int id() {
                return ADMIN_ID;
            }
        };
        MealTestData.MATCHER.assertCollectionEquals(
                MealUtils.getMealWithExceeds(Arrays.asList(MealTestData.MEAL_TEST_AD3, MealTestData.MEAL_TEST_AD2, MealTestData.MEAL_TEST_AD1), UserUtil.DEFAULT_CALORIES_PER_DAY),
                service.findAll(BaseEntity.START_SEQ + 1));
    }

    @Test
    public void testGetBetween() {
        List<MealWithExceed> actual = service.getBetween(100000, LocalDate.of(2018, 11, 17),
                LocalDate.of(2018, 11, 17),
                LocalTime.of(8, 0, 0),
                LocalTime.of(21, 0, 0));
        new MockUp<AuthorizedUser>() {
            @Mock
            public int id() {
                return USER_ID;
            }
        };
        MealTestData.MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(Arrays.asList(MealTestData.MEAL_TEST_6, MealTestData.MEAL_TEST_5), UserUtil.DEFAULT_CALORIES_PER_DAY), actual);
    }

    @Test
    public void testGetByIdException() {
        thrown.expect(NotFoundException.class);
        service.findById(100007, 100001);
    }

    @Test
    public void testGetByIdException1() {
        thrown.expect(NotFoundException.class);
        service.findById(100007, 100011);
    }

    @Test
    public void testGetById() {
        Meal actual = service.findById(100003, UserTestData.USER_ID);
        Assert.assertEquals(MealTestData.MEAL_TEST_2, actual);
    }

    @Test
    public void testDeleteById() {
        List<Meal> testListForUser = new ArrayList<>();
        testListForUser.add(MealTestData.MEAL_TEST_1);
        testListForUser.add(MealTestData.MEAL_TEST_3);
        testListForUser.add(MealTestData.MEAL_TEST_6);
        testListForUser.add(MealTestData.MEAL_TEST_5);
        testListForUser.add(MealTestData.MEAL_TEST_4);
        service.deleteById(100003, 100000);
        new MockUp<AuthorizedUser>() {
            @Mock
            public int id() {
                return USER_ID;
            }
        };
        MealTestData.MATCHER.assertCollectionEquals(
                MealUtils.getMealWithExceeds(testListForUser, UserUtil.DEFAULT_CALORIES_PER_DAY), service.findAll(USER_ID));
    }

    @Test
    public void testDeleteForIdException() {
        thrown.expect(NotFoundException.class);
        service.deleteById(100111, 100001);
    }

    @Test
    public void testEdit() {
        Meal updated = new Meal(MealTestData.MEAL_TEST_AD1.getId(), LocalDateTime.now(), "обед", 800);
        service.edit(MealUtils.createMealToFromMeal(updated), 100001);
        Meal actual = service.findById(MealTestData.MEAL_TEST_AD1.getId(), 100001);
        Assert.assertEquals(updated, actual);
    }

    @Test
    public void testSave() {
        Meal newMeal = new Meal(LocalDateTime.of(2018, 11, 15, 7, 30), "завтрак", 500);
        service.save(newMeal, 100001);
        new MockUp<AuthorizedUser>() {
            @Mock
            public int id() {
                return ADMIN_ID;
            }
        };
        MealTestData.MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(Arrays.asList(
                MealTestData.MEAL_TEST_AD3, MealTestData.MEAL_TEST_AD2, MealTestData.MEAL_TEST_AD1, newMeal
        ), UserUtil.DEFAULT_CALORIES_PER_DAY), service.findAll(100001));
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), USER_ID), ConstraintViolationException.class);
    }
}

