package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public abstract class AbstractUserServiceTest extends DbTest {


    @Autowired
    protected UserService service;

    @Test
    public void testSave() {
        User newUser = new User(null, "New", "new@gmail.com", "newPassword",
                1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        UserTestData.MATCHER.assertCollectionEquals(Arrays.asList(UserTestData.ADMIN, newUser, UserTestData.USER), service.getAll());
    }

    @Test
    public void testDuplicateMailSave() {
        thrown.expect(DataAccessException.class);
        service.save(new User(null, "newUserDuplicateEmail", "user@yandex.ru", "12345", Role.ROLE_USER));
    }

    @Test
    public void testDelete() {
        service.delete(UserTestData.USER_ID);
        UserTestData.MATCHER.assertCollectionEquals(Collections.singletonList(UserTestData.ADMIN), service.getAll());
    }

    @Test
    public void testNotFoundDelete() {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    public void testGet() {
        User user = service.get(UserTestData.USER_ID);
        Assert.assertEquals(UserTestData.USER, user);
    }

    @Test
    public void testGetNotFoundException() {
        thrown.expect(NotFoundException.class);
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

    @Test
    public void testRoles(){
        User user = service.get(UserTestData.USER_ID);
        Assert.assertEquals(UserTestData.USER.getRoles(), user.getRoles());
    }
    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.save(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "mail@yandex.ru", "password", 9, true, Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "mail@yandex.ru", "password", 10001, true, Collections.emptySet())), ConstraintViolationException.class);
    }

}
