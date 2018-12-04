package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealUtils;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class MealServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);

    private static Map<String, Long> timesMap = new ConcurrentHashMap<>();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            timesMap.put(description.getMethodName(), nanos);
            LOG.info(description.getMethodName(), nanos);
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            timesMap.put(description.getMethodName() + " failed", nanos);
            LOG.info(description.getMethodName() + " failed", e, nanos);
        }

        @Override
        protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
            LOG.info(description.getMethodName() + " skipped", e, nanos);
        }

        @Override
        protected void finished(long nanos, Description description) {
            timesMap.put(description.getMethodName(), nanos);
            LOG.info(description.getMethodName(), nanos);
        }
    };

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

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
        MealTestData.MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(Arrays.asList(MealTestData.MEAL_TEST_6, MealTestData.MEAL_TEST_5)), actual);
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
        MealTestData.MATCHER.assertCollectionEquals(
                MealUtils.getMealWithExceeds(testListForUser), service.findAll(100000));
    }

    @Test
    public void testDeleteForIdException() {
        thrown.expect(NotFoundException.class);
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

    @AfterClass
    public static void statistics() {
        System.out.println("_______________________________________");
        for (Map.Entry<String, Long> entry : timesMap.entrySet()) {
            System.out.println(String.format("%s %s мс",
                    entry.getKey(), TimeUnit.NANOSECONDS.toMillis(entry.getValue())));
        }
        System.out.println("_______________________________________");
    }
}

