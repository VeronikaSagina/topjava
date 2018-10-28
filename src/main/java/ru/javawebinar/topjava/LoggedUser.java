package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;

import java.util.Collections;
import java.util.Set;

public class LoggedUser {
    protected int id = 0;
    protected Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    protected boolean enabled = true;

    private static LoggedUser LOGGED_USER = new LoggedUser();

    public static LoggedUser getLoggedUser() {
        return LOGGED_USER;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getId() {
        return id;
    }
}
