package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserLite;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = AdminRestController.REST_URL)
public class AdminRestController extends AbstractUserController {

    static final String REST_URL = "/rest/admin/users";

    @Autowired
    public AdminRestController(UserService service) {
        super(service);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserLite> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLite get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLite> createWithLocation(@RequestBody User user) {
        UserLite createUser = super.create(user);
        URI urlNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/id")
                .buildAndExpand(createUser.getId()).toUri();
        return ResponseEntity.created(urlNewResource).body(createUser);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user, @PathVariable("id") int id) {
        super.update(user, id);
    }

    @Override
    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLite getByMail(@RequestParam("email") String email) {
        return super.getByMail(email);
    }
}
