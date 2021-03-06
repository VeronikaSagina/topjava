package ru.javawebinar.topjava.web.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Collection;

import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

//springTest

@ContextConfiguration("classpath:spring/springTest-app.xml")
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerStringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
   /* @Qualifier("inMemoryUserRepositoryImpl")*/
    private UserRepository repository;

    @Before
    public void setUp() {
        repository.getAll().forEach(u -> repository.delete(u.getId()));
        repository.save(USER);
        repository.save(ADMIN);
    }

    @Test
    public void testDelete() {
        controller.delete(UserTestData.USER_ID);
        Collection<UserLite> users = controller.getAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.iterator().next(), new UserLite(ADMIN));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() {
        controller.delete(10);
    }
}
