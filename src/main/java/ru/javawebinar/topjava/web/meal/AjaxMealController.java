package ru.javawebinar.topjava.web.meal;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/meals")
public class AjaxMealController extends AbstractMealController {

    @Override
    @GetMapping("/filter")
    @JsonView(View.JsonUI.class)
    public List<MealWithExceed> findAll
            (@RequestParam(value = "startDate", required = false) String startDate,
             @RequestParam(value = "endDate", required = false) String endDate,
             @RequestParam(value = "startTime", required = false) String startTime,
             @RequestParam(value = "endTime", required = false) String endTime) {
        return super.findAll(startDate, endDate, startTime, endTime);
    }

    @Override
    @GetMapping(value = "/{id}")
    @JsonView(View.JsonUI.class)
    public MealTo getOne(@PathVariable("id") int id) {
        return super.getOne(id);
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createAndUpdate(@Valid MealTo meal, BindingResult result) {
        if (result.hasErrors()) {
            return ValidationUtil.getErrorResponse(result);
        }
        if (meal.isNew()) {
            super.save(meal);
        } else {
            super.update(meal, meal.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.JsonUI.class)
    public List<MealWithExceed> findAll() {
        return super.findAll();
    }
}
