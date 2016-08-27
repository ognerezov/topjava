package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Ognerezov on 27/08/16.
 * Это не та реализация
 */
public class UserMealsUtil {
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> meals,LocalDateTime startTime, LocalDateTime endTime, int coloriesLimit){

        Stream<UserMeal> mealStream =meals.stream();


        MealsPerDay.setMaxCalories(coloriesLimit);
        mealStream
                .filter(meal->
                        meal.getDateTime().compareTo(startTime)>=0 &&meal.getDateTime().compareTo(endTime)<=0)
                .forEach(meal ->MealsPerDay.addMealGetDay(new UserMealWithExceed(meal)) );


        return MealsPerDay.getProccessedList();
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
