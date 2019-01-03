package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/meals")
public class AjaxMealController extends AbstractMealController {

    @GetMapping("/filter")
    public List<MealWithExceed> findAll
            (@RequestParam(value = "startDate", required = false) String startDate,
             @RequestParam(value = "endDate", required = false) String endDate,
             @RequestParam(value = "startTime", required = false) String startTime,
             @RequestParam(value = "endTime", required = false) String endTime) {
        return super.findAll(startDate, endDate, startTime, endTime);
    }

    @GetMapping(value = "/{id}")
    public Meal getOne(@PathVariable("id") int id) {
        return super.getOne(id);
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal createAndUpdate(@RequestParam("id") Integer id,
                                @RequestParam("dateTime")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                                @RequestParam("description") String description,
                                @RequestParam("calories") Integer calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        if (meal.isNew()) {
            return super.save(meal);
        }
        return super.update(meal, id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> findAll() {
        return super.findAll();
    }
}
