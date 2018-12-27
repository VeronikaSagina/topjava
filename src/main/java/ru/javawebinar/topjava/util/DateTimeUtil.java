package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private DateTimeUtil() {
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public static boolean isBetween(LocalDate mDate, LocalTime mTime, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return mDate.compareTo(startDate) >= 0 && mDate.compareTo(endDate) <= 0 && mTime.compareTo(startTime) >= 0 && mTime.compareTo(endTime) <= 0;
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T m, T start, T end) {
        return m.compareTo(start) >= 0 && m.compareTo(end) <= 0;
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }

    public static LocalDate parseLocalDate(String date) {
        if ( date.isEmpty()) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalTime parseLocalTime(String time) {
        if (time.isEmpty()) {
            return null;
        }
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }
}
