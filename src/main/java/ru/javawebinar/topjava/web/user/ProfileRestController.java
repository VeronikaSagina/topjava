package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.web.AuthorizedUser;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    @Autowired
    public ProfileRestController(UserService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLite get() {
        return super.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(User user) {
        super.update(user, AuthorizedUser.id());
    }
}
