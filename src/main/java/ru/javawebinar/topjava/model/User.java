package ru.javawebinar.topjava.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static ru.javawebinar.topjava.util.MealUtils.DEFAULT_CALORIES_PER_DAY;

public class User extends NamedEntity {
    private String email;
    private String password;
    private boolean enabled = true;//включен, разрешен
    private LocalDateTime registered = LocalDateTime.now();
    private Set<Role> roles;
    private List<Meal> mealList = new ArrayList<>();
    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

    public User() {

    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay(), user.isEnabled(), user.getRoles());
    }

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

    public User(int id, String name, String email, String password, int caloriesPerDay, LocalDateTime registered, boolean enabled) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.registered = registered;
        this.enabled = enabled;
    }

    /*  public User(String name, String email, String password, Role role, Role... roles) {
        super(name);
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.authorities = EnumSet.of(role, roles);
    }
*/

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    private Set<Role> getRoles() {
        return roles;
    }

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

    public LocalDateTime getRegistered() {
        return registered;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }
}
