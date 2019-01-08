package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.TestUtil.userAuth;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class RootControllerTest extends AbstractRestControllerTest {

    @Test
    public void testUserList() {
        try {
            mockMvc.perform(get("/users")
                    .with(userAuth(ADMIN)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("users"))
                    .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnAuth() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testMealList() {
        try {
            mockMvc.perform(post("/users")
                    .param("userId", String.valueOf(USER_ID)));
            mockMvc.perform(get("/meals")
                    .with(userAuth(ADMIN)))
                    .andDo(print())
                    .andExpect(view().name("meals"))
                    .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}