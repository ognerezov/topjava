package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Ognerezov on 16/10/16.
 */
@Controller
public class MealsController {
    @Autowired
    private UserService service;
    @Autowired
    private MealService mealService;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model, @RequestParam(value="action", required=false) String action,
                        @RequestParam(value="id", required=false) Integer id)
    {
        int userId = AuthorizedUser.id();
        if ("delete".equals(action)){
            try {
                mealService.delete(id,userId);
            } catch (NotFoundException e){
            }
        }

        model.addAttribute("meals",
                MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "meals", method = RequestMethod.POST)
    public String changeMeals(HttpServletRequest request,
                           Model model) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        int userId = AuthorizedUser.id();
        if ("filter".equals(action)) {

            LocalDate startDate=null;
            try {
                startDate= LocalDate.parse(request.getParameter("startDate"));
            } catch (Exception e){

            }
            LocalDate endDate=null;
            try {
                endDate = LocalDate.parse(request.getParameter("endDate"));
            } catch (Exception e){

            }
            LocalTime startTime=null;
            try {
                startTime = LocalTime.parse(request.getParameter("startTime"));
            } catch (Exception e){

            }
            LocalTime endTime=null;
            try {
                endTime = LocalTime.parse(request.getParameter("endTime"));
            } catch (Exception e){

            }
            model.addAttribute("meals",
            MealsUtil.getFilteredWithExceeded(
                    mealService.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, userId),
                    startTime != null ? startTime : LocalTime.MIN,
                    endTime != null ? endTime : LocalTime.MAX,
                    AuthorizedUser.getCaloriesPerDay()
            ));
        } else if ("update".equals(action)){
            Integer id=null;
            try{
                id=Integer.parseInt(request.getParameter("id"));
            } catch (Exception e){

            }
            LocalDateTime dateTime=LocalDateTime.parse(request.getParameter("dateTime"));
            String description=request.getParameter("description");
            int calories=Integer.parseInt(request.getParameter("calories"));
            Meal meal=new Meal(id,dateTime,description,calories);
            mealService.save(meal,userId);
            model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));

        } else if ("cancel".equals(action)){
            model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));

        }
        return "meals";
    }

    @RequestMapping(value = "/meal", method = RequestMethod.GET)
    public String meal(Model model, @RequestParam(value="action", required=true) String action,
                        @RequestParam(value="id", required=false) Integer id) {
        int userId = AuthorizedUser.id();
        Meal meal=null;
        if ("create".equals(action)){
            meal=mealService.getDefault();
        } else {
            try
            {
                meal=mealService.get(id,userId);
            } catch (NotFoundException e)
            {
                meal=mealService.getDefault();
            }
        }
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.DELETE)
    public String deleteMeal(Model model,@RequestParam(value="ation", required=false) String adtion,
                             @RequestParam(value="id", required=false) Integer id) {
        int userId = AuthorizedUser.id();
        try {
            mealService.delete(id,userId);
        } catch (NotFoundException e){

        }
        model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }
}
