package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static org.junit.Assert.*;

/**
 * Created by Ognerezov on 28/09/16.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    protected MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }


    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(1000000,USER_ID);
        List<Meal> meals=getMealsWithID();
        meals.get(0).setId(1000000);
        MATCHER.assertEquals(meals.get(0), meal);

    }

    @Test(expected = NotFoundException.class)
    public void testGetWrongUser() throws Exception {
        Meal meal = service.get(1000000,ADMIN_ID);
        List<Meal> meals=getMealsWithID();
        MATCHER.assertEquals(meals.get(0), meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(1000001,USER_ID);
        List<Meal> meals=getMealsWithID();
        Meal removedMeal= meals.remove(1);
        MATCHER.assertCollectionEquals(meals, service.getAll(USER_ID));
        meals.add(1,removedMeal);

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWrongID() throws Exception {
        service.delete(1000001,ADMIN_ID);
        List<Meal> meals=getMealsWithID();
        Meal removedMeal= meals.remove(1);
        MATCHER.assertCollectionEquals(meals, service.getAll(USER_ID));
        meals.add(1,removedMeal);

    }


}