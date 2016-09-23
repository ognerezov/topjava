package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;


/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static AdminRestController adminUserController;
    private static MealRestController mealRestController;
   // private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
      //  repository = new InMemoryMealRepositoryImpl();

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));
            mealRestController = appCtx.getBean(MealRestController.class);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                AuthorizedUser.id());

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRestController.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            String paramId = request.getParameter("userid");
            int userId= paramId==null ? 0: paramId.isEmpty() ? 0:Integer.valueOf(paramId);
            LOG.info("getAll");
            AuthorizedUser.setId(userId);
            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.setAttribute("userid", userId);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000,AuthorizedUser.id()) :
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        } else if ("changeuser".equals(action)){
            String paramId = Objects.requireNonNull(request.getParameter("userid"));
            int userId= paramId==null ? 0: paramId.isEmpty() ? 0:Integer.valueOf(paramId);
            LOG.info("Cahnge user {}", userId );
            AuthorizedUser.setId(userId);
            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.setAttribute("userid", userId);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
