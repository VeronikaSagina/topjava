package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/springTest-app.xml","spring/spring-app.xml","spring/spring-db.xml")) {
            System.out.println("___________________________________________");
            System.out.println("Bean definition names: ");
            String[] beanDefinitionNames = appCtx.getBeanDefinitionNames();
            for(String s : beanDefinitionNames){
                System.out.println(s);
            }
            System.out.println("___________________________________________");

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.findBetween(
                            LocalDate.of(2018, Month.MAY, 30), LocalDate.of(2018, Month.NOVEMBER, 30),
                            LocalTime.of(7, 0), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);

        }
    }
}
