package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.util.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView applicationErrorHandler(HttpServletRequest req, ApplicationException e) throws Exception {
        return getView(req, e, messageUtil.getMessage(e.getMsgCode(), e.getArgs()));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e){
        return getView(request, e, null);
    }

    private ModelAndView getView(HttpServletRequest req, Exception e, String msg) {
        LOG.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView("exception/exception");
        modelAndView.addObject("exception", e);
        modelAndView.addObject("message", msg);
        return modelAndView;
    }
}
