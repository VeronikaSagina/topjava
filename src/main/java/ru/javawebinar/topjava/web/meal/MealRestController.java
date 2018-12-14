package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.AuthorizedUser;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/meals")
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(required = false) String startDate,
                          @RequestParam(required = false) String endDate,
                          @RequestParam(required = false) String startTime,
                          @RequestParam(required = false) String endTime
    ) {
        LOG.info("findAll for user{}" + AuthorizedUser.id());
        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate) && StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
            List<MealWithExceed> all = service.findAll(AuthorizedUser.id());
            model.addAttribute("meals", all);
            return "meals";
        }
        LocalDate startD = !StringUtils.isEmpty(startDate) ? DateTimeUtil.parseLocalDate(startDate) : LocalDate.MIN;
        LocalDate endD = !StringUtils.isEmpty(endDate) ? DateTimeUtil.parseLocalDate(endDate) : LocalDate.MAX;
        LocalTime startT = !StringUtils.isEmpty(startTime) ? DateTimeUtil.parseLocalTime(startTime) : LocalTime.MIN;
        LocalTime endT = !StringUtils.isEmpty(endTime) ? DateTimeUtil.parseLocalTime(endTime) : LocalTime.MAX;
        List<MealWithExceed> between = service.getBetween(AuthorizedUser.id(), startD, endD, startT, endT);
        model.addAttribute("meals", between);
        return "meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        service.deleteById(Integer.parseInt(id), AuthorizedUser.id());
        return "redirect:/meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        model.addAttribute("meal", service.findById(Integer.parseInt(id), AuthorizedUser.id()));
        return "meal";
    }

 /*   @GetMapping("/{id}")
    public String findById(Model model, @PathVariable*//*(name = "id") *//*int id) {
        LOG.info("findById {} user {}", id, AuthorizedUser.id());
        Meal byId = service.findById(id, AuthorizedUser.id());
        model.addAttribute(byId);
        return "meal";
    }*/

    @GetMapping("/new")
    public String createMeal(Model model) {
        model.addAttribute("meal",
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "meal";
    }

    @RequestMapping(method = RequestMethod.POST, value = "meals")
    public String createAndUpdate(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("date")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            service.save(meal, AuthorizedUser.id());
        } else {
            String id = Objects.requireNonNull(request.getParameter("id"));
            meal.setId(Integer.parseInt(id));
            service.edit(meal, AuthorizedUser.id());
        }
        return "redirect:/meals";
    }

/*    @PostMapping
    public String create(HttpServletRequest request) {
        createAndUpdate(request);
       *//* checkNew(meal);
        LOG.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);*//*
        return "redirect:meals";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, HttpServletRequest request) {
        createAndUpdate(request);
      *//*  LOG.info("update {} for User {}", meal, userId);
        service.edit(meal, userId);*//*
      return "meals";
    }*/
}
