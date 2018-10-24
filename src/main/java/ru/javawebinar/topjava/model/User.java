package ru.javawebinar.topjava.model;

import java.util.*;

public class User extends NamedEntity {
    private String email;
    private String password;
    private boolean enabled = true;//включен, разрешен
    private Date registered = new Date();
    private Set<Role> authorities;
    private List<Meal> mealList = new ArrayList<>();
    public User() {

    }

    public User(String name, String email, String password, Role role, Role... roles) {
        super(name);
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.authorities = EnumSet.of(role, roles);
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", authorities=" + authorities +
                '}';
    }
}
