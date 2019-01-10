package ru.javawebinar.topjava.web;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.web.json.JacksonConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class JsonTest {
    @Test
    public void testRestReadWriteValue() throws Exception {
        ObjectWriter uiWriter = JacksonConfiguration.getMapper().writerWithView(View.JsonUI.class);
        String json = uiWriter.writeValueAsString(MealTestData.MEAL_TO_TEST_AD1);
        System.out.println(json);
        assertThat(json, containsString("dateTimeUI"));
    }
}
