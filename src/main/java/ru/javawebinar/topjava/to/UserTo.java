package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.util.UserUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTo implements Serializable, HasId {
    private static final long SERIAL_VERSION_UID = 1L;

    private Integer id;

    @NotBlank
    @SafeHtml
    private String name;

    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @JsonView(View.JsonREST.class)
    @Length(min = 5, max = 32)
    @SafeHtml
    private String password;

    @Range(min = 10, max = 10000)
    @NotNull
    private Integer caloriesPerDay = UserUtil.DEFAULT_CALORIES_PER_DAY;

    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", caloriesPerDay=" + caloriesPerDay +
                '}';
    }
}
