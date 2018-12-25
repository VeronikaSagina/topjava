package ru.javawebinar.topjava.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.web.AbstractRestControllerTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.*;

public class AdminRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = "/rest/admin/users/";

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_USER_LITE.contentMatcher(new UserLite(ADMIN)));
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_USER_LITE.contentMatcher(new UserLite(USER)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER_USER_LITE.assertCollectionEquals(Collections.singletonList(new UserLite(ADMIN)), userService.getAll().stream().map(UserLite::new).collect(Collectors.toList()));
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, userService.get(USER_ID));
    }

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isCreated());
        User returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

/*        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, expected, USER), userService.getAll());*/
        MATCHER_USER_LITE.assertEquals(new UserLite(expected), new UserLite(returned));
        MATCHER_USER_LITE.assertCollectionEquals(
                Arrays.asList(new UserLite(ADMIN), new UserLite(expected), new UserLite(USER)),
                userService.getAll().stream().map(UserLite::new).collect(Collectors.toList()));
    }

}