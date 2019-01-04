package ru.javawebinar.topjava.web;


import ru.javawebinar.topjava.util.UserUtil;

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
        return UserUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
