package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {
    public AdminAjaxController(UserService service) {
        super(service);
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<UserLite> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLite get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(UserTo userTo) {
        if (userTo.isNew()) {
            super.create(UserUtil.createNewUserFromUserTo(userTo));
        } else {
            super.update(userTo);
        }
    }

    @PostMapping(value = "/{id}")
    public void changeEnabled(@PathVariable("id") int userId,
                              @RequestParam("enabled") boolean enabled) {
        super.changeEnabled(userId, enabled);
    }
}
