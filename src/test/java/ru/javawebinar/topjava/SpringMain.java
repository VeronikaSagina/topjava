package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;

public class SpringMain {
    public static void main(String[] args) {
        try(GenericXmlApplicationContext context = new GenericXmlApplicationContext()){
            context.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            context.load(/*"spring/springTest-app.xml"*/"spring/spring-app.xml","spring/spring-db.xml");
            context.refresh();
            System.out.println("___________________________________________");
            System.out.println("Bean definition names: ");
            String[] beanDefinitionNames = context.getBeanDefinitionNames();
            for(String s : beanDefinitionNames){
                System.out.println(s);
            }
            System.out.println("___________________________________________");
            MealRestController controller = context.getBean(MealRestController.class);


          /*  MealRestController mealController = context.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.findBetween(
                            LocalDate.of(2018, Month.MAY, 30), LocalDate.of(2018, Month.NOVEMBER, 30),
                            LocalTime.of(7, 0), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);*/
        }
    }
   /* public static void main(String[] args) {
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
    }*/
}
