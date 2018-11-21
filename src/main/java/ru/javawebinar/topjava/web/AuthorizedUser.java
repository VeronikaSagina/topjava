package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealUtils.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {
    private AuthorizedUser() {
    }

    private static int id;

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
