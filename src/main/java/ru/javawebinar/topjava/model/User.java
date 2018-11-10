package ru.javawebinar.topjava.model;

import java.util.*;

import static ru.javawebinar.topjava.util.MealUtils.DEFAULT_CALORIES_PER_DAY;

public class User extends NamedEntity {
    private String email;
    private String password;
    private boolean enabled = true;//включен, разрешен
    private Date registered = new Date();
    private Set<Role> roles;
    private List<Meal> mealList = new ArrayList<>();
    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;
    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_CALORIES_PER_DAY, true, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.roles = roles;
    }
  /*  public User(String name, String email, String password, Role role, Role... roles) {
        super(name);
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.authorities = EnumSet.of(role, roles);
    }
*/
    public List<Meal> getMealList() {
        return mealList;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getAuthorities() {
        return roles;
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
                ", authorities=" + roles +
                '}';
    }
}
