package ru.javawebinar.topjava;

import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    static MealService mealService = new MealServiceImpl();
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
    }
}
