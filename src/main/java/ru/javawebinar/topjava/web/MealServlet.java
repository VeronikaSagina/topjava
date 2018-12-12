package ru.javawebinar.topjava.web;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ru.javawebinar.topjava.Profiles;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    public static final String PATH_MEALS_JSP = "meals.jsp";
    public static final String LOCATION_MEALS = "meals";
   // private ClassPathXmlApplicationContext appCtx;
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
      /*  appCtx = new ClassPathXmlApplicationContext();
        appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        appCtx.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        appCtx.refresh();
        controller = appCtx.getBean(MealRestController.class);*/
        WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        controller = springContext.getBean(MealRestController.class);
    }

  /*  @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }*/

    /* request - запрос, получить значения response - ответ, вернуть что-то */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                delete(req);
                resp.sendRedirect(LOCATION_MEALS);
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action)
                        ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000)
                        : controller.findById(getId(req));
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("meal.jsp").forward(req, resp);//пересылка на jsp страничку респонса и реквеста
                break;
            case "all":
            default:
                req.setAttribute("meals", controller.findAll());
                req.getRequestDispatcher(PATH_MEALS_JSP).forward(req, resp);
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final String action = req.getParameter("action");
        if ("create".equals(action) || "update".equals(action)) {
            createAndUpdate(req, resp);
        } else if ("filter".equals(action)) {
            LocalDate startDate = DateTimeUtil.parseLocalDate(req.getParameter("startDate"));
            LocalDate endDate = DateTimeUtil.parseLocalDate(req.getParameter("endDate"));
            LocalTime startTime = DateTimeUtil.parseLocalTime(req.getParameter("startTime"));
            LocalTime endTime = DateTimeUtil.parseLocalTime(req.getParameter("endTime"));
            req.setAttribute("meals", controller.findBetween(startDate, endDate, startTime, endTime));
            req.getRequestDispatcher(PATH_MEALS_JSP).forward(req, resp);
        }
    }

    private void createAndUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("date")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            controller.create(meal);
        } else {
            controller.update(meal, getId(req));
        }
        resp.sendRedirect(LOCATION_MEALS);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private void delete(HttpServletRequest req) {
        int mealD = getId(req);
        controller.deleteById(mealD);
    }
   /* private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//редактировать
        Meal meal = controller.findById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("meal", meal);
        LOG.debug("edit meal{}", meal);
        req.getRequestDispatcher("meal.jsp").forward(req, resp);
    }
*/
}
