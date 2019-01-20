package ru.javawebinar.topjava.web.user;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Autowired
    public AdminAjaxController(UserService service) {
        super(service);
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @JsonView(View.JsonUI.class)
    public List<UserLite> getAll() {
        return super.getAll();
    }

    @Override
    @JsonView(View.JsonUI.class)
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
    public void createOrUpdate(@Valid UserTo userTo) {
        /* System.out.println(messageSource.getMessage("birthday", new Object[]{"Васю", LocalDate.now()}, ru));*/
        if (userTo.isNew()) {
            super.create(UserUtil.createNewUserFromUserTo(userTo));
        } else {
            super.update(userTo, userTo.getId());
        }
    }

    @PostMapping(value = "/{id}")
    public void changeEnabled(@PathVariable("id") int userId, @RequestParam("enabled") boolean enabled) {
        super.changeEnabled(userId, enabled);
    }
}
