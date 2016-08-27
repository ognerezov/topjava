package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.model.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;


/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Topjava Enterprise! This is HW0 test case!");
        ArrayList<UserMeal> meals=new ArrayList<UserMeal>();

        meals.add(new UserMeal(LocalDateTime.of(2016, Month.APRIL, 1, 13, 31), "Tea",50));
        meals.add(new UserMeal(LocalDateTime.of(2016, Month.APRIL, 1, 13, 30), "Egg",150));
        meals.add(new UserMeal(LocalDateTime.of(2016, Month.APRIL, 1, 14, 30), "Pizza",5000));
        meals.add(new UserMeal(LocalDateTime.of(2016, Month.APRIL, 1, 13, 30), "Cherry",150));
        meals.add(new UserMeal(LocalDateTime.of(2016, Month.APRIL, 2, 12, 30), "Green Tea",49));
        meals.add(new UserMeal(LocalDateTime.of(2016, Month.APRIL, 2, 12, 40), "Bread",1950));

        for(UserMealWithExceed meal: UserMealsUtil.getFilteredWithExceeded(meals,
                LocalDateTime.of(2016, Month.APRIL, 1, 0, 0),LocalDateTime.of(2016, Month.APRIL, 2, 23, 59),
                2000)){
            System.out.println(meal.toString());
        }
    }
}
