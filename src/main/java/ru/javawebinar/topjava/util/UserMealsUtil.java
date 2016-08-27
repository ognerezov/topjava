package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    private static final HashMap<LocalDate,Integer> caloriesPerDay=new HashMap<LocalDate,Integer>();
    private static final LinkedList<UserMeal> mealsAtTime=new LinkedList<UserMeal>();
    private static final LinkedList<UserMealWithExceed> result=new LinkedList<UserMealWithExceed>();
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> meals=getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        for(UserMealWithExceed meal: meals){
            System.out.println(meal.toString());
        }
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> meals,LocalTime startTime, LocalTime endTime, int coloriesLimit){

        mealsAtTime.clear();
        result.clear();

       meals.stream().forEach(meal -> addMeal(meal,startTime,endTime));

      mealsAtTime.stream().forEach(meal -> proccessMeal(meal,coloriesLimit));
        return result;

    }

    private static UserMeal addMeal(UserMeal meal, LocalTime startTime, LocalTime endTime){
        LocalDate date=meal.getDateTime().toLocalDate();

        if (caloriesPerDay.containsKey(date)){
            caloriesPerDay.put(date, caloriesPerDay.get(date)+meal.getCalories());
        }else  caloriesPerDay.put(date,meal.getCalories());

        if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime )){
            mealsAtTime.add(meal);
        }
        return meal;
    }

    private static void proccessMeal(UserMeal meal, int caloriesLimit){
        int calories=caloriesPerDay.get(meal.getDateTime().toLocalDate());

        result.add(new UserMealWithExceed(meal,calories>caloriesLimit));
    }

    private static boolean getExceed(UserMeal meal,int caloriesLimit){
        int calories=caloriesPerDay.get(meal.getDateTime().toLocalDate());
        return calories>caloriesLimit;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded0(List<UserMeal> meals,LocalTime startTime, LocalTime endTime, int coloriesLimit) {
    return getFilteredWithExceeded(meals,startTime,endTime,coloriesLimit);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded1(List<UserMeal> meals,LocalTime startTime, LocalTime endTime, int coloriesLimit) {
        return getFilteredWithExceeded(meals,startTime,endTime,coloriesLimit);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded2(List<UserMeal> meals,LocalTime startTime, LocalTime endTime, int coloriesLimit) {
        return getFilteredWithExceeded(meals,startTime,endTime,coloriesLimit);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded3(List<UserMeal> meals,LocalTime startTime, LocalTime endTime, int coloriesLimit) {
        return getFilteredWithExceeded(meals,startTime,endTime,coloriesLimit);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded4(List<UserMeal> meals,LocalTime startTime, LocalTime endTime, int coloriesLimit) {
        return getFilteredWithExceeded(meals,startTime,endTime,coloriesLimit);

    }




}

