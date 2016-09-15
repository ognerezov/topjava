package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealListMemory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.UserMealsUtil.getFilteredWithExceeded;
import static ru.javawebinar.topjava.util.UserMealsUtil.getTestList;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final MealListMemory model=MealListMemory.getTestObject();
    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("redirect to userList");
        request.setAttribute("meals",model.getListWithExceed());
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
 //       response.sendRedirect("mealList.jsp");
    }
}
