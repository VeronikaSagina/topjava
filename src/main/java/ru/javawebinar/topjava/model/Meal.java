package ru.javawebinar.topjava.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    @NotNull
    /*  @Convert(converter = LocalDateTimeAttributeConverter.class)*/
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000)
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
               /* ", userId=" + user.getId() +*/
                '}';
    }
    /*

     */
    /* @Converter(autoApply = true)*//*

    public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

        @Override
        public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
            return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
        }

        @Override
        public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
            return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
        }
    }
*/
}
