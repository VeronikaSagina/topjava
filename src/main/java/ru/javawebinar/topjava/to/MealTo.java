package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.View;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MealTo implements HasId {
    private Integer id;

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    @NotNull(groups = {View.ValidatedUI.class, Default.class})
    @JsonView(View.JsonREST.class)
    private LocalDateTime dateTime;

    @NotBlank(groups = {View.ValidatedUI.class, Default.class})
    private String description;

    @NotNull(groups = {View.ValidatedUI.class, Default.class})
    @Range(min = 10, max = 5000, groups = {View.ValidatedUI.class, Default.class})
    private Integer calories;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @JsonGetter
    @JsonView(View.JsonUI.class)
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    public LocalDateTime getDateTimeUI() {
        return dateTime;
    }

    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    public void setDateTimeUI(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
