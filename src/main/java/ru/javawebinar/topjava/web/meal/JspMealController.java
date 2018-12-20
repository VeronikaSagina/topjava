package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    @Autowired
    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(required = false) String startDate,
                          @RequestParam(required = false) String endDate,
                          @RequestParam(required = false) String startTime,
                          @RequestParam(required = false) String endTime
    ) {
        if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate) && StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            List<MealWithExceed> all = super.findAll();
            model.addAttribute("meals", all);
            return "meals";
        }
        List<MealWithExceed> between = super.findAll(startDate, endDate, startTime, endTime);
        model.addAttribute("meals", between);
        return "meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        super.delete(Integer.parseInt(id));
        return "redirect:/meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Model model, @RequestParam int id) {
        Meal update = super.update(id);
        model.addAttribute("meal", update);
        return "meal";
    }

    @GetMapping("/new")
    public String createMeal(Model model) {
        model.addAttribute("meal", super.createMeal());
        return "meal";
    }

    @RequestMapping(method = RequestMethod.POST, value = "meals")
    public String createAndUpdate(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("date")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            super.save(meal);
        } else {
            String id = Objects.requireNonNull(request.getParameter("id"));
            meal.setId(Integer.parseInt(id));
            super.edit(meal);
        }
        return "redirect:/meals";
    }
}
