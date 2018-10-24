package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.AuthorizedUser;

@Controller
public class ProfileRestController extends AbstractUserController{

   /* @Autowired
    private UserService service;*/
   public User get() {
       return super.get(AuthorizedUser.id());
   }

    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    public void update(User user) {
        super.update(user, AuthorizedUser.id());
    }
}
