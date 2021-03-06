package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    //    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcMealRepositoryImpl.class);
    private final SimpleJdbcInsert insertMeal;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private PlatformTransactionManager transactionManager;

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private static final RowMapper<Meal> ROW_MAPPER = (rs, rowNumber) -> new Meal(
            rs.getInt("id"),
            rs.getTimestamp("dateTime").toLocalDateTime(),
            rs.getString("description"),
            rs.getInt("calories"));

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
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            LOG.debug("save meal {} for user with id {}", meal, userId);
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
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);
        boolean update;
        try {
            LOG.debug("delete meal with id: " + id + " for user with id: " + userId);
            update = jdbcTemplate.update(
                    "DELETE FROM meals WHERE user_id=? and id=?", userId, id) != 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        return update;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.debug("get meal with id: " + id + " for user with id: " + userId);
        List<Meal> mealList = jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? and id=?", ROW_MAPPER, userId, id);
       /* for (Meal meal : mealList) {
            User user = new User();
            user.setId(userId);
            meal.setUser(user);
        }*/
        return CollectionUtils.isEmpty(mealList) ? null : DataAccessUtils.requiredSingleResult(mealList);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        LOG.debug("get all meals for user with id: " + userId);
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC", ROW_MAPPER, userId);
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        LOG.debug(String.format("get meals between %s and %s for user with id: %d",
                String.valueOf(startDateTime), String.valueOf(endDateTime), userId));
        return jdbcTemplate.query
                ("SELECT * FROM meals WHERE user_id=? AND datetime between ? AND ? ORDER BY datetime DESC",
                        ROW_MAPPER, userId, startDateTime, endDateTime);
    }

}
