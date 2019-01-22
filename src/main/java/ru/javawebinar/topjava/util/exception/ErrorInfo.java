package ru.javawebinar.topjava.util.exception;

import lombok.Getter;

@Getter
public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String[] details;

    public ErrorInfo(CharSequence url, String cause, String...details) {
        this.url = url.toString();
        this.cause = cause;
        this.details = details;
    }

    public ErrorInfo(CharSequence url, Throwable exception){
       this(url, exception.getClass().getSimpleName(), exception.getLocalizedMessage());
    }
}
