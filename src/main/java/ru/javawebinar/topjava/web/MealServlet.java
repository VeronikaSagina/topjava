package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private static MealService mealService;
    {
        if (mealService == null) {
            mealService = appCtx.getBean(MealService.class);
        }
    }

    /*
    request - запрос, получить значения
    response - ответ, вернуть что-то
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LOG.debug("mealServlet Get");
        final String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "edit":
                    edit(req, resp);
                    return;
                case "delete":
                    delete(req, resp);
                    break;
                case "create":
                    req.setAttribute("meal", new Meal(AuthorizedUser.id(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
                    req.getRequestDispatcher("meal.jsp").forward(req, resp);
                    return;
            }
        }
        req.setAttribute("MealList", mealService.findAll(AuthorizedUser.id()));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);//пересылка на jsp страничку респонса и реквеста
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LOG.debug("in Post");
        final String action = req.getParameter("action");
        if (action != null) {
            if (action.equals("update") || action.equals("create")) {
                createAndUpdate(req, resp);
            }
        }
    }

    private void createAndUpdate(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String date = req.getParameter("date");
        LocalDateTime time = LocalDateTime.parse(date);
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        String id = req.getParameter("id");
        if (id != null && !id.equals("")) {
            Meal update = mealService.edit(new Meal(AuthorizedUser.id(), Integer.parseInt(id), time, description, Integer.parseInt(calories)), AuthorizedUser.id());
            LOG.debug("update meal{}", update);
        } else {
            Meal create = mealService.edit(new Meal(AuthorizedUser.id(), time, description, Integer.parseInt(calories)) , AuthorizedUser.id());
            LOG.debug("create meal{}", create);
        }
        resp.sendRedirect("meals");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//редактировать
        Meal meal = mealService.findById(Integer.parseInt(req.getParameter("id")), AuthorizedUser.id());
        req.setAttribute("meal", meal);
        LOG.debug("edit meal{}", meal);
        req.getRequestDispatcher("meal.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        int mealD = Integer.parseInt(req.getParameter("id"));
        LOG.debug("delete {}", mealD);
        mealService.deleteById(mealD, AuthorizedUser.id());
    }
}
