package ru.javawebinar.topjava;

import lombok.Getter;
import lombok.Setter;
import ru.javawebinar.topjava.model.Role;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
public class LoggedUser {
    private int id = 0;
    private Set<Role> roles = Collections.singleton(Role.ROLE_USER);
    private boolean enabled = true;

    private static LoggedUser LOGGED_USER = new LoggedUser();

    public static LoggedUser getLoggedUser() {
        return LOGGED_USER;
    }
}
