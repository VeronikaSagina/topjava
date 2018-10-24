package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.AuthorizedUser;

import java.util.List;

@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);
    @Autowired
    private MealService service;
    public List<MealWithExceed> findAll() {
        LOG.info("findAll for user " + AuthorizedUser.id());
        return service.findAll(AuthorizedUser.id());
    }

    public void deleteById(int id) {
        LOG.info(String.format("deleteById %d user %d", id, AuthorizedUser.id()));
        service.deleteById(id, AuthorizedUser.id());
    }

    public Meal findById(int id) {
        LOG.info(String.format("findById %d user %d", id, AuthorizedUser.id()));
        return service.findById(id, AuthorizedUser.id());
    }

    public Meal edit(Meal update) {
        LOG.info(String.format("edit %s user %d", update, AuthorizedUser.id()));
        return service.edit(update, AuthorizedUser.id());
    }
}
