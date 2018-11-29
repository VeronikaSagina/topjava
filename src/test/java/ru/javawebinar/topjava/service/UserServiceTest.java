package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles(Profiles.ACTIVE_DB)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private UserService service;

    @Test
    public void testSave() {
        User newUser = new User(null, "New", "new@gmail.com", "newPassword",
                1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        UserTestData.MATCHER.assertCollectionEquals(Arrays.asList(UserTestData.ADMIN, newUser, UserTestData.USER), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() {
        service.save(new User(null, "newUserDuplicateEmail", "user@yandex.ru", "12345", Role.ROLE_USER));
    }

    @Test
    public void testDelete() {
        service.delete(UserTestData.USER_ID);
        UserTestData.MATCHER.assertCollectionEquals(Collections.singletonList(UserTestData.ADMIN), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() {
        service.delete(1);
    }

    @Test
    public void testGet() {
        User user = service.get(UserTestData.USER_ID);
        Assert.assertEquals(UserTestData.USER, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundException() {
        service.get(1);
    }

    @Test
    public void testGetByEmail() {
        User user = service.getByEmail("user@yandex.ru");
        UserTestData.MATCHER.assertEquals(UserTestData.USER, user);
    }

    @Test
    public void testGetAll() {
        List<User> all = service.getAll();
        UserTestData.MATCHER.assertCollectionEquals(Arrays.asList(UserTestData.ADMIN, UserTestData.USER), all);
    }

    @Test
    public void testUpdate() {
        User updateUser = new User(UserTestData.USER);
        updateUser.setName("updateUser");
        updateUser.setCaloriesPerDay(1650);
        service.update(updateUser);
        UserTestData.MATCHER.assertEquals(updateUser, service.get(UserTestData.USER_ID));
    }

}
