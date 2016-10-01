package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by Ognerezov on 29/09/16.
 */
public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    private static final List<Meal> USER_MEALS = new ArrayList(Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 14, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 17, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 15, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 18, 0), "Ужин", 1000)
    ));

    public static List<Meal> getMealsWithID(){

        int mealId=1000000;

        for (Meal meal: USER_MEALS){
            if (meal.getId()==null){
                meal.setId(mealId);
                mealId++;
            }
        }
        return USER_MEALS;
    }

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                    )
    );
}
