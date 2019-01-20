package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    public static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";

    private UserService service;

    public AbstractUserController(UserService service) {
        this.service = service;
    }

    public List<UserLite> getAll() {
        LOG.info("getAll");
        return service.getAll().stream().map(UserLite::new).collect(Collectors.toList());
    }

    public UserLite get(int id) {
        LOG.info("Get user for id {}", id);
        return new UserLite(service.get(id));
    }

    public UserLite create(User user) {
        LOG.info("Create user {}", user);
        checkNew(user);
        return new UserLite(service.save(user));
    }

    public void delete(int id) {
        LOG.info("delete user with id{}", id);
        service.delete(id);
    }


    UserLite getByMail(String email) {
        LOG.info("get by email user {}", email);
        return new UserLite(service.getByEmail(email));
    }

    public void update(UserTo userTo, int id) {
        LOG.info("update {} with id={}", userTo, id);
        service.update(userTo);
    }
    public void update(User user, int id) {
        LOG.info("update {} with id={}", user, id);
        checkIdConsistent(user, id);
        service.update(user);
    }
    protected void changeEnabled(int userId, boolean enabled) {
        LOG.info("enabled user with id{} = {}", userId, enabled ? "enable" : "disable");
        service.enable(userId, enabled);
    }
}
