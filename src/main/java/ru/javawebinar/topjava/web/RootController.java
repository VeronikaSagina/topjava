package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserLite;
import ru.javawebinar.topjava.util.MealUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RootController {

    private final UserService userService;

    @Autowired
    public RootController(UserService service) {
        this.userService = service;
    }

    @GetMapping(value = "/")
    public String root() {
        return "index";
    }

    @GetMapping(value = "/users")
    public String userList(Model model) {
        List<User> all = userService.getAll();
        List<UserLite> dtoList = all.stream()
                .map(UserLite::new)
                .collect(Collectors.toList());
        model.addAttribute("users", dtoList);
        return "users";
    }

    @PostMapping(value = "/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

}
