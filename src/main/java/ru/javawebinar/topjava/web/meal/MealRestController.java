package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Autowired
    protected MealRestController(MealService service) {
        super(service);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> findAll
            (@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
             @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        if (StringUtils.isEmpty(startDate)
                && StringUtils.isEmpty(endDate)
                && StringUtils.isEmpty(startTime)
                && StringUtils.isEmpty(endTime)) {
            return super.findAll();
        }
        return super.findAll(startDate, endDate, startTime, endTime);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List getAll() {
        return super.findAll();
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> findAllLocalDateTime(@RequestParam("startDateTime")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime startDateTime,
                                                     @RequestParam("endDateTime")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime endDateTime) {
        return super.findAll(startDateTime, endDateTime);
    }

    @GetMapping(value = "/{id}")
    public Meal getOne(@PathVariable("id") int id) {
        return super.getOne(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }


    @PostMapping(value = {"/create/{id}", "/update"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal createAndUpdate(@PathVariable("id") Integer id, @RequestParam("date") String date,
                                @RequestParam("description") String description, @RequestParam("calories") String calories) {
        if (id != null) {
            Meal update = super.update(id);
            Meal meal = new Meal(update.getId(), LocalDateTime.parse(date), description, Integer.parseInt(calories));
            super.edit(meal);
            return meal;
        }
        Meal meal = new Meal(LocalDateTime.parse(date), description, Integer.parseInt(calories));
        super.save(meal);
        return meal;
    }
/* @PutMapping(value = "/create/{date}/{description}/{calories}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal createMeal( @PathVariable("date") String date,
                           @PathVariable("description") String description,  @PathVariable("calories") String calories) {
        Meal meal = new Meal(LocalDateTime.parse(date), description, Integer.parseInt(calories));
        super.save(meal);
        return meal;
    }*/

}
