package ru.javawebinar.topjava.web;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class RootControllerTest extends AbstractRestControllerTest {

    @Test
    public void testUserList() {
        try {
            mockMvc.perform(get("/users"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("users"))
                    .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                    .andExpect(model().attribute("users", hasSize(2)))
                    .andExpect(model().attribute("users", hasItem(
                            allOf(
                                    hasProperty("id", is(START_SEQ)),
                                    hasProperty("name", is(USER.getName()))
                            ))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMealList() {
        try {
            mockMvc.perform(post("/users")
                    .param("userId", String.valueOf(USER_ID)));
            mockMvc.perform(get("/meals"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("meals"))
                    .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                    .andExpect(model().attribute("meals", hasSize(6)))
                    .andExpect(model().attribute("meals", hasItem(
                            allOf(
                                    hasProperty("description", is("обед")),
                                    hasProperty("id", is(100002)),
                                    hasProperty("dateTime", is(
                                            LocalDateTime.parse("2018-11-18 13:30:00",
                                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))),
                                    hasProperty("calories", is(800))
                            ))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}