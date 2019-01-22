package ru.javawebinar.topjava.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {
    private final String msgCode;
    private final HttpStatus httpStatus;
    private final String[] args;

    public ApplicationException(String msgCode, HttpStatus httpStatus, String... args) {
        this.msgCode = msgCode;
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public ApplicationException(String msgCode, HttpStatus httpStatus) {
        this(msgCode, httpStatus, new String[]{});
    }
}
