package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
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

        MealsPerDay.setMaxCalories(coloriesLimit);
        for(UserMeal meal:meals){
            MealsPerDay.addMealGetDay(new UserMealWithExceed(meal));
        }

        List<UserMealWithExceed> fullList=MealsPerDay.getProccessedList();

        return fullList.stream().filter(meal->
                       TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime))
                .collect(Collectors.toList());

    }



    public static class MealsPerDay {
        private static final List<MealsPerDay> days=new LinkedList<MealsPerDay>();
        private List<UserMealWithExceed> meals=new LinkedList<UserMealWithExceed>();
        private LocalDate date;
        private int calories =0;
        private boolean exceeded =false;

        private static int maxCalories;

        private MealsPerDay(){

        }

        public static List<MealsPerDay> getDays() {
            return Collections.unmodifiableList(days);
        }

        public static List<UserMealWithExceed> getProccessedList(){
            LinkedList<UserMealWithExceed> result=new LinkedList<UserMealWithExceed>();

            for(MealsPerDay day: days){
                result.addAll(day.meals);
            }
            return result;
        }

        public  LocalDate getDate() {
            return date;
        }

        public boolean isExeeded() {
            return exceeded;
        }

        public List<UserMealWithExceed> getMeals() {
            return meals;
        }

        public static int getMaxCalories() {
            return maxCalories;
        }

        public static void setMaxCalories(int maxCalories) {
            MealsPerDay.maxCalories = maxCalories;
            recount();

        }

        public static void recount(){
            /**
             * При необоходимости здесь можно пересчитать превышение максимума колорий, если он изменится
             */
        }

        private void addMeal(UserMealWithExceed meal){

            if (exceeded) {
                meals.add(new UserMealWithExceed(meal,true));
            } else {
                meals.add(meal);
            }

            calories +=meal.getCalories();
            if(!exceeded && calories > maxCalories){
                exceeded =true;
                LinkedList<UserMealWithExceed> newMeals=new LinkedList<UserMealWithExceed>();
                for (UserMealWithExceed mealWithExceed: meals){
                    newMeals.add(new UserMealWithExceed(mealWithExceed,true));
                }

                meals=newMeals;
            }

        }

        public static MealsPerDay addMealGetDay(UserMealWithExceed meal){
            for (MealsPerDay day: days){
                if (day.getDate().equals(meal.getDateTime().toLocalDate())){
                    day.addMeal(meal);
                    return day;
                }
            }
            MealsPerDay newDay=new MealsPerDay();
            newDay.date=meal.getDateTime().toLocalDate();
            newDay.addMeal(meal);
            days.add(newDay);

            return newDay;

        }
    }


}

