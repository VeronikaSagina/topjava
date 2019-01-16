package ru.javawebinar.topjava.util.exception;

import lombok.Getter;

@Getter
public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String detail;

    public ErrorInfo(CharSequence url, Throwable exception){
        this.url = url.toString();
        cause = exception.getClass().getSimpleName();
        detail = exception.getLocalizedMessage();
    }
}
