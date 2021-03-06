package ru.javawebinar.topjava.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.util.UserUtil;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "unique_email")})
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id = :id"),
        @NamedQuery(name = User.BY_EMAIL, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.name, u.email")
})

public class User extends NamedEntity {

    public static final String DELETE = "User.delete";
    public static final String ALL_SORTED = "User.getAllSorted";
    public static final String BY_EMAIL = "User.getByEmail";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Length(min = 5)
    @JsonView(View.JsonREST.class)
    @SafeHtml
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;//включен, разрешен

    @Column(name = "registered", columnDefinition = "timestamp default now()")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Europe/Moscow")
    private Instant registered = Instant.now();

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @Column(name = "calories_per_day", columnDefinition = "int default 2000")
    @Range(min = 10, max = 10000)
    private int caloriesPerDay = UserUtil.DEFAULT_CALORIES_PER_DAY;

    @OneToMany( /*cascade = CascadeType.REMOVE,orphanRemoval = true*/  mappedBy = "user", fetch = FetchType.LAZY)
//какскадно удалить, удалить сирот
    @OrderBy("dateTime DESC")
    //@Transient
    @JsonIgnore
    private List<Meal> meals;

    public User() {

    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay(), user.isEnabled(), user.getRegistered(), user.getRoles());
    }

    public User(Integer id, String name, String email, String password,int caloriesPerDay, Role role, Role... roles) {
        this(id, name, email, password, caloriesPerDay, true, Instant.now(), EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enabled, Instant registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public User(int id, String name, String email, String password, int calories_per_day, boolean enabled, Instant registered) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.caloriesPerDay = calories_per_day;
        this.registered = registered;
        this.enabled = enabled;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Instant getRegistered() {
        return registered;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void setEnabled(boolean enable) {
        enabled = enable;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", email='" + email + '\'' +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                ", meals: " + meals +
                '}';
    }
}
