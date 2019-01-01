package ru.javawebinar.topjava.to;

import org.springframework.beans.BeanUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class UserLite {
    private int id;
    private int caloriesPerDay;
    private String name;
    private String email;
    private Set<Role> roles;
    private boolean enabled;
    private Instant registered;

    private UserLite() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserLite(User user) {
        BeanUtils.copyProperties(user, this, "meals");
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getRegistered() {
        return registered;
    }

    public void setRegistered(Instant registered) {
        this.registered = registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLite lite = (UserLite) o;
        return id == lite.id &&
                caloriesPerDay == lite.caloriesPerDay &&
                enabled == lite.enabled &&
                name.equals(lite.name) &&
                email.equals(lite.email) &&
                roles.equals(lite.roles) &&
                registered.equals(lite.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caloriesPerDay, name, email, enabled, registered);
    }

    @Override
    public String toString() {
        return "UserLite{" +
                "id=" + id +
                ", caloriesPerDay=" + caloriesPerDay +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", enabled=" + enabled +
                ", registered=" + registered +
                '}';
    }
}
