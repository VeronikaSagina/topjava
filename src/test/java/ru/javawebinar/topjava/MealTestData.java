package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MealTestData {
    public static final Meal MEAL_TEST_1 = new Meal(100002, LocalDateTime.of(2018, 11, 18, 13, 30), "обед", 800);
    public static final Meal MEAL_TEST_2 = new Meal(100003, LocalDateTime.of(2018, 11, 18, 21, 25), "ужин", 500);
    public static final Meal MEAL_TEST_3 = new Meal(100004, LocalDateTime.of(2018, 11, 18, 8, 0), "завтрак", 600);
    public static final Meal MEAL_TEST_4 = new Meal(100005, LocalDateTime.of(2018, 11, 17, 7, 30), "завтрак", 1000);
    public static final Meal MEAL_TEST_5 = new Meal(100006, LocalDateTime.of(2018, 11, 17, 13, 15), "обед", 1000);
    public static final Meal MEAL_TEST_6 = new Meal(100007, LocalDateTime.of(2018, 11, 17, 20, 45), "ужин", 1000);
    public static final Meal MEAL_TEST_AD1 = new Meal(100008, LocalDateTime.of(2018, 11, 19, 8, 0), "завтрак", 600);
    public static final Meal MEAL_TEST_AD2 = new Meal(100009, LocalDateTime.of(2018, 11, 19, 14, 35), "обед", 800);
    public static final Meal MEAL_TEST_AD3 = new Meal(100001, LocalDateTime.parse("2018-11-20 07:35:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), "завтрак", 600);

    public static final ModelMatcher<MealWithExceed> MATCHER = new ModelMatcher<>(
     /*       (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.getDescription(), actual.getDescription()))*/
    );

    public static final ModelMatcher<Meal> MATCHER_MEAL= new ModelMatcher<>();

    private static void parse(int id, String str, int i) {
        str = str.substring(str.indexOf("'")).replaceAll("'", "\"");
        String[] elements = str.split(", ");
        String dateTimeS = String.format("LocalDateTime.parse(%s, DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\"))", elements[0]);
        String result = String.format("public static final Meal MEAL_TEST_%d = new Meal(%d, %s, %s, %s);", i, id, dateTimeS, elements[1], elements[2]);
        System.out.println(result);
    }

    public static void main(String[] args) {
        parse(BaseEntity.START_SEQ + 1, "insert into meals(datetime, description, calories, user_id) values ('2018-11-20 07:35:00', 'завтрак', 600, 100001);", 1);
    }

    private static void parse1(int id, String str, int i) {
        str = str.substring(str.indexOf("'") + 1);
        String dateTimeS = str.substring(0, str.indexOf("'"));
        str = str.substring(dateTimeS.length()).trim();
        String[] elements = str.split(", ");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeS, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String result = String.format("public static final Meal MEAL_TEST_%d =" +
                        " new Meal(%d, LocalDateTime.of(%s), \"%s\", %s);", i, id, dateTime.getYear()
                        + ", " + dateTime.getMonthValue()
                        + ", " + dateTime.getDayOfMonth()
                        + ", " + dateTime.getHour()
                        + ", " + dateTime.getMinute(),
                elements[1].replaceAll("'", ""), elements[2]);
        System.out.println(result);
    }
}
