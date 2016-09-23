package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {

        if (meal.getUserId()!= AuthorizedUser.id()) throw new NotFoundException("This is not your food");
        LOG.info("Save " +meal.toString());
        return service.save(meal);
    }

    public void delete(int id) {
        if (service.get(id).getUserId()!= AuthorizedUser.id()) throw new NotFoundException("This is not your food");

        LOG.info("delete "+id);
        service.delete(id);
    }

    public Meal get(int id) {
        if (service.get(id).getUserId()!= AuthorizedUser.id()) throw new NotFoundException("This is not your food");

        LOG.info("get "+ id);
        return service.get(id);
    }

    public Collection<Meal> getAll() {
        LOG.info("getAll " +AuthorizedUser.id());
        return service.getAll(AuthorizedUser.id());
    }

    public void update(Meal meal) {
        if (meal.getUserId()!= AuthorizedUser.id()) throw new NotFoundException("This is not your food");

        LOG.info("update "+meal);
        service.save(meal);
    }

}
