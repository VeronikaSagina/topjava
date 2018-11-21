package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealUtils;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

  /* @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void testSetUp() {
        dbPopulator.execute();
    }*/

    @Test
    public void testGetAll() {
        MealTestData.MATCHER.assertCollectionEquals(
                MealUtils.getMealWithExceeds(Arrays.asList(MealTestData.MEAL_TEST_AD3, MealTestData.MEAL_TEST_AD2, MealTestData.MEAL_TEST_AD1)),
                service.findAll(BaseEntity.START_SEQ + 1));
    }

    @Test
    public void testGetBetween() {
        List<MealWithExceed> actual = service.getBetween(100000, LocalDate.of(2018, 11, 17),
                LocalDate.of(2018, 11, 17),
                LocalTime.of(8, 0, 0),
                LocalTime.of(21, 0, 0));
        MealTestData.MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(Arrays.asList(MealTestData.MEAL_TEST_5, MealTestData.MEAL_TEST_6)), actual);
    }

    @Test(expected = NotFoundException.class)
    public void testGetByIdException() {
        //service.findById(100009, 100000);
        service.findById(100007, 100001);
    }

    @Test
    public void testGetById() {
        Meal actual = service.findById(100003, 100000);
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
        MealTestData.MATCHER.assertCollectionEquals(
                MealUtils.getMealWithExceeds(testListForUser), service.findAll(100000));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteForIdException() {
        service.deleteById(100111, 100001);
    }

    @Test
    public void testEdit() {
        Meal updated = new Meal(MealTestData.MEAL_TEST_AD1.getId(), LocalDateTime.now(), "обед", 800);
        service.edit(updated, 100001);
        Meal actual = service.findById(MealTestData.MEAL_TEST_AD1.getId(), 100001);
        Assert.assertEquals(updated, actual);
    }

    @Test
    public void testSave() {
        Meal newMeal = new Meal(LocalDateTime.of(2018, 11, 15, 7, 30), "завтрак", 500);
        service.save(newMeal, 100001);
        MealTestData.MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(Arrays.asList(
                MealTestData.MEAL_TEST_AD3, MealTestData.MEAL_TEST_AD2, MealTestData.MEAL_TEST_AD1, newMeal
        )), service.findAll(100001));
    }
}

