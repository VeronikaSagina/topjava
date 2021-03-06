package ru.javawebinar.topjava.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.web.AbstractRestControllerTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.web.user.AbstractUserController.EXCEPTION_DUPLICATE_EMAIL;

public class AdminRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = "/rest/admin/users/";

    /*
     *  CRUD
     */

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", 1000, Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isCreated());
        UserLite returned = MATCHER_USER_LITE.fromJsonAction(action);
        expected.setId(returned.getId());
        MATCHER_USER_LITE.assertEquals(new UserLite(expected), returned);
        MATCHER_USER_LITE.assertCollectionEquals(
                Arrays.asList(new UserLite(ADMIN), new UserLite(expected), new UserLite(USER)),
                userService.getAll().stream().map(UserLite::new).collect(Collectors.toList()));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MATCHER_USER_LITE.contentListMatcher(new UserLite(ADMIN), new UserLite(USER)));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID)
                // .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString("admin:admin".getBytes())))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_USER_LITE.contentMatcher(new UserLite(ADMIN)));
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_USER_LITE.contentMatcher(new UserLite(USER)));
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, userService.get(USER_ID));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER_USER_LITE.assertCollectionEquals(Collections.singletonList(
                new UserLite(ADMIN)), userService.getAll().stream().map(UserLite::new).collect(Collectors.toList()));
    }

    /*
     *  redirect
     */
    @Test
    public void testGetUnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    /*
     *  exception
     */
    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(TestUtil.userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User invalidUser = new User(USER);
        invalidUser.setName("");
        invalidUser.setPassword(null);
        invalidUser.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(content().json("{'cause':'ValidationException'}"))
                .andDo(print());
    }

    @Test
    public void testCreateInvalid() throws Exception {
        User expected = new User(null, "New", "", "", 1000, Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json("{'cause':'ValidationException'}"))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void updateDuplicateEmail() throws Exception {
        User updated = new User(USER);
        updated.setEmail("admin@gmail.com");
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
//                .locale(new Locale("ru"))
                .with(userHttpBasic(ADMIN))
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isConflict())
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void createDuplicateEmail() throws Exception{
        User expected = new User(null, "NewUser", "user@yandex.ru", "qwertyuio", 1000, Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isConflict())
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }
}