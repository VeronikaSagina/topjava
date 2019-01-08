package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.TestUtil.mockAuthorize;
import static ru.javawebinar.topjava.UserTestData.USER;

public class SpringMain {
    public static void main(String[] args) {
        try(GenericXmlApplicationContext context = new GenericXmlApplicationContext()){
            context.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            context.load(/*"spring/springTest-app.xml,"*/"spring/spring-app.xml","spring/spring-db.xml", "spring/spring-mvc.xml");
            context.refresh();
            System.out.println("___________________________________________");
            System.out.println("Bean definition names: ");
            String[] beanDefinitionNames = context.getBeanDefinitionNames();
            for(String s : beanDefinitionNames){
                System.out.println(s);
            }
            System.out.println("___________________________________________");
            mockAuthorize(USER);

            System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));
            AdminRestController adminUserController = context.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            System.out.println();

            MealRestController mealController = context.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.findAllLocalDateTime(
                            LocalDateTime.of(2018, Month.NOVEMBER, 1,7, 0),
                            LocalDateTime.of(2018, Month.NOVEMBER, 17,21, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
