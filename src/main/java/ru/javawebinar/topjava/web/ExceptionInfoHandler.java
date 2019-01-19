package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest request, NotFoundException e) {
        return logAndGetErrorInfo(request, e, false);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest request, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(request, e, true);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ErrorInfo bindingError(HttpServletRequest request, BindingResult result) {
        return logAndGetValidatedErrorInfo(request, result);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest request, MethodArgumentNotValidException e) {
        return logAndGetValidatedErrorInfo(request, e.getBindingResult());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest request, Exception e) {
        return logAndGetErrorInfo(request, e, false);
    }

    private ErrorInfo logAndGetValidatedErrorInfo(HttpServletRequest request, BindingResult result) {
        String[] details = result.getFieldErrors().stream()
                .map(er -> er.getField() + ' ' + er.getDefaultMessage())
                .toArray(String[]::new);
        String uri = request.getRequestURI();
        LOG.warn("Validation exception at request {}: {}", uri, Arrays.toString(details));
        return new ErrorInfo(uri, "ValidationException", details);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest request, Exception e, boolean logException) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            LOG.error("Exception at request {}" + request.getRequestURI(), rootCause);
        } else {
            LOG.warn("Exception at request {}: {}", request.getRequestURI() + ": " + rootCause.toString());
        }
        return new ErrorInfo(request.getRequestURL(), rootCause);
    }
}
