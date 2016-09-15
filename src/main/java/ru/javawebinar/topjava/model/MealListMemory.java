package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.UserMealsUtil.getFilteredWithExceeded;
import static ru.javawebinar.topjava.util.UserMealsUtil.getTestList;

/**
 * Created by Ognerezov on 13/09/16.
 */
public class MealListMemory {
    private List<Meal> list;

    public MealListMemory(List<Meal> list){
        this.list=list;
    }

    public List<MealWithExceed> getListWithExceed(){
        return getFilteredWithExceeded(list, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }

    public static MealListMemory getTestObject(){
        return new MealListMemory(getTestList());
    }

    public void Add(Meal meal){
        list.add(meal);
    }

    public Meal getById(int id){
        for(Meal meal: list){
            if(meal.getId()==id) return meal;
        }
        return null;
    }

    public void Delete(int id){
        Meal meal=getById(id);
        if(meal!=null) list.remove(meal);
    }

    public void Edit(int id, LocalDateTime dateTime, String  description, int calories){
        Meal meal=getById(id);
        if(meal!=null) meal=new Meal(id,dateTime,description,calories);

    }

}
