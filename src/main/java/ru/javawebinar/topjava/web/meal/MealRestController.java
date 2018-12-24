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

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.findAll();
    }

   /* @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> findAllLocalDateTime(@RequestParam("startDateTime")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime startDateTime,
                                                     @RequestParam("endDateTime")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime endDateTime) {
        return super.findAll(startDateTime, endDateTime);
    }*/

    @GetMapping(value = "/{id}")
    public Meal getOne(@PathVariable("id") int id) {
        return super.getOne(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }


    @PutMapping(value = {"/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal update(@RequestBody Meal meal, @PathVariable("id") Integer id) {
        checkIdConsistent(meal, id);
        return super.update(meal);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal create(@RequestBody Meal meal) {
        checkNew(meal);
        return super.save(meal);
    }
}
