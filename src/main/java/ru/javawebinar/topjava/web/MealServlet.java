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
import java.time.temporal.ChronoUnit;


public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

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
                    req.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
                    req.getRequestDispatcher("meal.jsp").forward(req, resp);
                    return;
            }
        }
        req.setAttribute("MealList", mealService.findAll());
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

    private void createAndUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");
        LocalDateTime time = LocalDateTime.parse(date);
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        if (req.getParameter("id") != null && !req.getParameter("id").equals("")) {
            mealService.edit(new Meal(Integer.parseInt(req.getParameter("id")), time, description, Integer.parseInt(calories)));
        } else {
            mealService.edit(new Meal(time, description, Integer.parseInt(calories)));
        }
        resp.sendRedirect("meals");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//редактировать
        req.setAttribute("meal", mealService.findById(Integer.parseInt(req.getParameter("id"))));
        req.getRequestDispatcher("meal.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        mealService.deleteById(Integer.parseInt(req.getParameter("id")));
    }
}
