package ru.javawebinar.topjava.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            String[] names = appCtx.getBeanDefinitionNames();
            System.out.println("\n");
            for (String n : names){
                System.out.println(n);
            }
            System.out.println("\n");
            //MockUserRepository mu = appCtx.getBean(MockUserRepository.class);

        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
