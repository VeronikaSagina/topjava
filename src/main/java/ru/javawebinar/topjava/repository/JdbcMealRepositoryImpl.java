package ru.javawebinar.topjava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
@Primary
public class JdbcMealRepositoryImpl implements MealRepository {
//    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    private static final RowMapper<Meal> ROW_MAPPER = (rs, rowNumber) -> {
    Timestamp dateTime = rs.getTimestamp("dateTime");
    String description = rs.getString("description");
    int calories = rs.getInt("calories");
    int id = rs.getInt("id");

    return new Meal(id, dateTime.toLocalDateTime(), description, calories);
};

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertMeal;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate,
                                  NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("userId", userId);
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update("Update meals SET datetime=:dateTime," +
                    " description=:description, calories=:calories WHERE id = :id AND user_id=:userId", map);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update(
                "DELETE FROM meals WHERE user_id=? and id=?", userId, id) != 0;

    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> query = jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? and id=?", ROW_MAPPER, userId, id);
        return DataAccessUtils.singleResult(query);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC", ROW_MAPPER, userId);
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate,
                                       LocalTime startTime, LocalTime endTime, int userId) {
        return jdbcTemplate.query
                ("SELECT * FROM meals WHERE user_id=? AND datetime >= ? \n" +
                        " AND  datetime <= ?", ROW_MAPPER, userId, LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime));
    }
}