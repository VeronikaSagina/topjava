package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.AuthorizedUser;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    private void setId(){
        if (AuthorizedUser.id() == 0){
            AuthorizedUser.setId(100000);
        }
    }
    @GetMapping
    public List<MealWithExceed> findAll
            (@RequestParam(value = "startDate", required = false) String startDate,
             @RequestParam(value = "endDate", required = false) String endDate,
             @RequestParam(value = "startTime", required = false) String startTime,
             @RequestParam(value = "endTime", required = false) String endTime) {
        setId();
        return super.findAll(startDate, endDate, startTime, endTime);
    }

    @GetMapping(value = "/filter")
    public List<MealWithExceed> findAllLocalDateTime(@RequestParam("startDateTime")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime startDateTime,
                                                     @RequestParam("endDateTime")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             LocalDateTime endDateTime) {
        setId();
        return super.findAll(startDateTime, endDateTime);
    }

    @GetMapping(value = "/{id}")
    public Meal getOne(@PathVariable("id") int id) {
        setId();
        return super.getOne(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        setId();
        super.delete(id);
    }


    @PutMapping(value = {"/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Meal update(@RequestBody Meal meal, @PathVariable("id") Integer id) {
        setId();
        return super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@RequestBody Meal meal) {
        setId();
        Meal created = super.save(meal);
        URI uriOfNew = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNew);
        return new ResponseEntity<>(created, httpHeaders, HttpStatus.CREATED);
    }
}
