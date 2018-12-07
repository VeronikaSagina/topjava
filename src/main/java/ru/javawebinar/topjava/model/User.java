package ru.javawebinar.topjava.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealUtils.DEFAULT_CALORIES_PER_DAY;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "unique_email")})
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id = :id"),
        @NamedQuery(name = User.BY_EMAIL, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email")
})

public class User extends NamedEntity {
    public static final String DELETE = "User.delete";
    public static final String ALL_SORTED = "User.getAllSorted";
    public static final String BY_EMAIL = "User.getByEmail";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Length(min = 5)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;//включен, разрешен

    @Column(name = "registered", columnDefinition = "timestamp default now()")
    private Instant registered = Instant.now();

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection
   // @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @Column(name = "calories_per_day", columnDefinition = "int default 2000")
    @Range(min = 10, max = 10000)
    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

    @OneToMany( cascade = CascadeType.REMOVE, mappedBy = "user")
    @OrderBy("dateTime DESC")
   // @Transient
    private List<Meal> meals;

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

    public User(int id, String name, String email, String password, int caloriesPerDay, Instant registered, boolean enabled) {
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
                "id=" + getId() +
                ", email='" + email + '\'' +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                ", meals: " + meals +
                '}';
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
}
