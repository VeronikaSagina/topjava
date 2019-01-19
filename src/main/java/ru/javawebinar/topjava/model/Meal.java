package ru.javawebinar.topjava.model;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.javawebinar.topjava.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "datetime"}, name = "meals_unique_user_datetime_idx")})
@NamedQueries({
        @NamedQuery(name = Meal.GET_ALL_MEALS, query = "SELECT m FROM Meal m WHERE m.user.id = :user_id ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.user.id = :user_id AND m.id = :id"),
        @NamedQuery(name = Meal.GET_BETWEEN,
                query = "SELECT m FROM Meal m WHERE m.user.id = :user_id AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
})
public class Meal extends BaseEntity {
    public static final String DELETE = "meal.delete";
    public static final String GET_BETWEEN = "meal.getBetween";
    public static final String GET_ALL_MEALS = "meal.getAllSorted";


    @Column(name = "dateTime", nullable = false)
    @NotNull(groups = {View.ValidatedUI.class, Default.class})
    @JsonView(View.JsonREST.class)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank(groups = {View.ValidatedUI.class, Default.class})
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000, groups = {View.ValidatedUI.class, Default.class})
    @NotNull(groups = {View.ValidatedUI.class, Default.class})
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)//каскадное удаление в базе
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        setId(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;

    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return " Meal{" +
                "id=" + getId() +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
