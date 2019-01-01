package ru.javawebinar.topjava.web.user;

import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserLite;

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
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {
        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (user.isNew()) {
            super.create(user);
        } else {
            super.update(user, id);
        }
    }

    @PostMapping("/changeEnabled")
    public void changeEnabled(@RequestParam("userId") int userId,
                             @RequestParam("enabled") boolean enabled) {
        super.changeEnabled(userId, enabled);
    }
}
