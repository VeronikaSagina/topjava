package ru.javawebinar.topjava.web.meal;

import mockit.MockUp;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealUtils;
import ru.javawebinar.topjava.web.AbstractRestControllerTest;
import ru.javawebinar.topjava.web.AuthorizedUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = "/rest/meals/";

    @Test
    public void getBetweenMy() throws Exception {
        mockMvc.perform(get(REST_URL)
                .param("startDate", "2018-10-01")
                .param("endDate", "2018-11-17")
                .param("startTime", "07:00")
                .param("endTime", "21:30")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER.contentListMatcher(
                        TestUtil.mockCreateWithExceed(MEAL_TEST_6, true, USER_ID),
                        TestUtil.mockCreateWithExceed(MEAL_TEST_5, true, USER_ID),
                        TestUtil.mockCreateWithExceed(MEAL_TEST_4, true, USER_ID)));
    }

    @Test
    public void getBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDateTime", "2018-10-01T07:00:00")
                .param("endDateTime", "2018-11-17T21:30:00")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER.contentListMatcher(
                        TestUtil.mockCreateWithExceed(MEAL_TEST_6, true, USER_ID),
                        TestUtil.mockCreateWithExceed(MEAL_TEST_5, true, USER_ID),
                        TestUtil.mockCreateWithExceed(MEAL_TEST_4, true, USER_ID)));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + 100003)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER_MEAL.contentMatcher(MEAL_TEST_2));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + 100003)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk());

        List<Meal> meals = Arrays.asList(MEAL_TEST_1, MEAL_TEST_3, MEAL_TEST_4, MEAL_TEST_5, MEAL_TEST_6);
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        MockUp<AuthorizedUser> mockUp = TestUtil.getMockUp(USER_ID);
        try {
            MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(meals, 2000), mealService.findAll(USER_ID));
        } finally {
            mockUp.tearDown();
        }
    }

    @Test
    public void update() throws Exception {
        Meal mealUp = new Meal(MEAL_TEST_5.getId(), MEAL_TEST_5.getDateTime(), "обедик", 800);
        mockMvc.perform(put(REST_URL + 100006)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(mealUp))
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk());
        MATCHER_MEAL.assertEquals(mealUp, mealService.findById(100006, USER_ID));
    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2018, 12, 25, 22, 50), "ужин", 800);
        ResultActions actions = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(meal))
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated());
        Meal returned = MATCHER_MEAL.fromJsonAction(actions);
        meal.setId(returned.getId());

        MATCHER_MEAL.assertEquals(meal, returned);
        List<Meal> meals = Arrays.asList(MEAL_TEST_1, MEAL_TEST_2, MEAL_TEST_3, MEAL_TEST_4, MEAL_TEST_5, MEAL_TEST_6, meal);
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        MockUp<AuthorizedUser> mockUp = TestUtil.getMockUp(USER_ID);
        try {
            MATCHER.assertCollectionEquals(MealUtils.getMealWithExceeds(meals, 2000), mealService.findAll(USER_ID));
        } finally {
            mockUp.tearDown();
        }
    }
}