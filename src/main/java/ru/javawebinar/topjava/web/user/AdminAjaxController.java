package ru.javawebinar.topjava.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> createOrUpdate(@Valid UserTo userTo, BindingResult result) {
        if (result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(f -> sb.append(f.getField()).append(" ").append(f.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (userTo.isNew()) {
            super.create(UserUtil.createNewUserFromUserTo(userTo));
        } else {
            super.update(userTo);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public void changeEnabled(@PathVariable("id") int userId,
                              @RequestParam("enabled") boolean enabled) {
        super.changeEnabled(userId, enabled);
    }
}
