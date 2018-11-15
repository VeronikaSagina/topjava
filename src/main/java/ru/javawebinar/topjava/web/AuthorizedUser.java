package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealUtils.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {
    public static int id(){
        return 100000;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
