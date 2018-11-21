package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;

import java.util.Collections;
import java.util.Set;

public class LoggedUser {
    private int id = 0;
    private Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    private boolean enabled = true;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }
}
