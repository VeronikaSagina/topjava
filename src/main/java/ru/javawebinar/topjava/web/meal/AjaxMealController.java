package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealUtils;

import javax.validation.Valid;
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

    @PostMapping
    public ResponseEntity<String> createAndUpdate(@Valid MealTo meal, BindingResult result) {
        if (result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(f -> sb.append(f.getField()).append(" ").append(f.getDefaultMessage()).append("<br>"));
            return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (meal.isNew()) {
            super.save(MealUtils.createMealFromMealTo(meal));
        } else {
            super.update(MealUtils.createMealFromMealTo(meal), meal.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> findAll() {
        return super.findAll();
    }
}
