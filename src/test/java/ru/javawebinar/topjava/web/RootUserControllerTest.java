package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class RootUserControllerTest extends AbstractUserControllerTest{

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
                            )
                    )));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}