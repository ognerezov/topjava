package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    public JdbcMealRepositoryImpl(DataSource dataSource) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if(userId!= AuthorizedUser.id) return null;
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET datetime=:datetime, description=:description, calories=:calories, " +
                            "user_id=:user_id WHERE id=:id", map);
        }return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if(userId!= AuthorizedUser.id) return false;
        return jdbcTemplate.update("DELETE FROM meals WHERE id=?", id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        if(userId!= AuthorizedUser.id) return null;
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        if(userId!= AuthorizedUser.id) return null;
        return jdbcTemplate.query("SELECT * FROM meals ORDER BY datetime, description", ROW_MAPPER);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        if(userId!= AuthorizedUser.id) return null;
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? AND datetime BETWEEN ? AND ? ORDER BY datetime, description ", ROW_MAPPER, userId, startDate,endDate);

    }
}
