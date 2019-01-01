package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;

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
        }
    }
}
