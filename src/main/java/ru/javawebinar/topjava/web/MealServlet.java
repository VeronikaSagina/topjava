package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
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
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;
    private static MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    /* request - запрос, получить значения response - ответ, вернуть что-то */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                delete(req);
                resp.sendRedirect("meals");
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
                LOG.info("getAll");
                req.setAttribute("meals", controller.findAll());
                req.getRequestDispatcher("meals.jsp").forward(req, resp);
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
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
    }

    private void createAndUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("date")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            Meal meal1 = controller.create(meal);
            LOG.info("create meal{}", meal1);
        } else {
            controller.update(meal, getId(req));
            LOG.info("update meal{}", meal);
        }
        resp.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private void delete(HttpServletRequest req) {
        int mealD = getId(req);
        LOG.debug("delete {}", mealD);
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
