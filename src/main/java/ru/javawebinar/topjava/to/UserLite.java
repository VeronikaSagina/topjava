package ru.javawebinar.topjava.to;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLite {
    private int id;
    private int caloriesPerDay;
    private String name;
    private String email;
    private Set<Role> roles;
    private boolean enabled;
    private Instant registered;
    private String password;

    public UserLite(User user) {
        BeanUtils.copyProperties(user, this, "meals");
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
                password.equals(lite.password) &&
                email.equals(lite.email) &&
                roles.equals(lite.roles) &&
                registered.equals(lite.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caloriesPerDay, name, email, enabled, registered, password);
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
