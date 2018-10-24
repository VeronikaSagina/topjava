package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService service;

    public List<User> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        LOG.info("Get" + id);
        return service.get(id);
    }

    public User create(User user) {
        LOG.info("Create " + user);
        checkNew(user);
        return service.save(user);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }


    User getByMail(String email) {
        LOG.info("get by email " + email);
        return service.getByEmail(email);
    }


    void update(User user, int id) {
        LOG.info("update " + user);
        checkIdConsistent(user, id);
        service.update(user);
    }
}