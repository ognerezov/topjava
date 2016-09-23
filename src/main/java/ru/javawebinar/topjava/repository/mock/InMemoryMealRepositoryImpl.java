package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        MealsUtil.MEALS.forEach(this::save);
    }


    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {

        return  repository.values().stream()
                .filter(meal -> meal.getUserId()==userId)
                .sorted(new Comparator<Meal>() {
            @Override
            public int compare(Meal meal1, Meal meal2) {
                if (!meal1.getDateTime().isEqual(meal2.getDateTime())) {
                    return meal1.getDateTime().isAfter(meal2.getDateTime()) ? 1:-1;
                }
                else return meal1.toString().compareTo(meal2.toString());
            }
        }).collect(Collectors.toList());
    }

}

