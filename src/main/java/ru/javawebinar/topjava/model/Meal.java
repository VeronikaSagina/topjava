package ru.javawebinar.topjava.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meals")
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.user = :user AND m.id = :id"),
        @NamedQuery(name = Meal.GET_BETWEEN,
                query = "SELECT m FROM Meal m WHERE m.user = :user AND m.dateTime BETWEEN :startDate AND :endDate"),
        @NamedQuery(name = Meal.GET_ALL_MEALS, query = "SELECT m FROM Meal m WHERE m.user = :user ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.user = :user AND m.id = :id")
})
public class Meal extends BaseEntity {
    public static final String DELETE = "meal.delete";
    public static final String GET_BETWEEN = "meal.getBetween";
    public static final String GET_ALL_MEALS = "meal.getAllSorted";
    public static final String GET = "meal.get";

    @Column(name = "datetime")
    @NotBlank
  /*  @Convert(converter = LocalDateTimeAttributeConverter.class)*/
    private LocalDateTime dateTime;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "calories")
    @NotBlank
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {

    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;

    }

    public Integer getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return " Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
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
