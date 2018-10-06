package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.stream.Stream;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final MealService mealService = new MealService();

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
                    req.setAttribute("formatter", formatter);
                    req.getRequestDispatcher("meal.jsp").forward(req, resp);
                    return;
            }
        }
        req.setAttribute("MealList", mealService.findAll());
        req.setAttribute("formatter", formatter);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);//пересылка на jsp страничку респонса и реквеста
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LOG.debug("in Post");
        final String action = req.getParameter("action");
        if (action != null) {
            if (action.equals("update")) {
                update(req, resp);
            } else if (action.equals("create")) {
                create(req, resp);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        mealService.create(new Meal(time, description, Integer.parseInt(calories)));
        resp.sendRedirect("meals");
    }

    @SuppressWarnings("Duplicates")
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String date = req.getParameter("date");
        LocalDateTime time = LocalDateTime.parse(date, formatter);
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        mealService.edit(new Meal(Integer.parseInt(id), time, description, Integer.parseInt(calories)));
        resp.sendRedirect("meals");
//        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("formatter", formatter);
        req.setAttribute("meal", mealService.findById(Integer.parseInt(req.getParameter("id"))));
        req.getRequestDispatcher("meal.jsp").forward(req, resp);
//        mealService.edit();
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        mealService.deleteById(Integer.parseInt(req.getParameter("id")));
    }
}
