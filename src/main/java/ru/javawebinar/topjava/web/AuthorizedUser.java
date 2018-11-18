package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.BaseEntity;

import static ru.javawebinar.topjava.util.MealUtils.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {
    public static int id;
    public static int id(){
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
