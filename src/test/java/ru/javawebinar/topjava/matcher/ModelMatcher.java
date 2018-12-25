package ru.javawebinar.topjava.matcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.web.json.JacksonConfiguration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * сравнивает бины и колекции, преобразуя их в строку
 * оборачивает каждую сущность перед вызовом Assert.assertEquals
 */
public class ModelMatcher<T> {

    public interface Equality<T> {
        boolean areEqual(T expected, T actual);
    }

    private static Map<Class, ObjectReader> readerMap = new HashMap<>();

    private static ObjectMapper objectMapper = JacksonConfiguration.getMapper();

    private Equality<T> equality;

    private Class<T> entityClass;

    private ModelMatcher(Class<T> entityClass, Equality<T> equality) {
        this.entityClass = entityClass;
        this.equality = equality;
    }

    public static <T> ModelMatcher<T> of(Class<T> entityClass) {
        return of(entityClass, (T expected, T actual) -> expected == actual || String.valueOf(expected).equals(String.valueOf(actual)));
    }

    public static <T> ModelMatcher<T> of(Class<T> entityClass, Equality<T> equality) {
        return new ModelMatcher<>(entityClass, equality);
    }

    public Wrapper wrap(T entity) {
        return new Wrapper(entity);
    }

    public List<Wrapper> wrap(Collection<T> collection) {
        return collection.stream().map(this::wrap).collect(Collectors.toList());
    }

    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    public void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
//        Assert.assertEquals(wrap(expected), wrap(actual));
        Assert.assertArrayEquals(wrap(expected).toArray(), wrap(actual).toArray());
    }

    public ResultMatcher contentMatcher(T expected) {
        return content().string(
                new TestMatcher<T>(expected) {
                    @Override
                    protected boolean compare(T expected, String actual) {
                        Wrapper expectedForCompare = wrap(expected);
                        Wrapper actualForCompare = wrap(fromJsonValue(actual));
                        return expectedForCompare.equals(actualForCompare);
                    }
                }
        );
    }

    @SafeVarargs
    public final ResultMatcher contentListMatcher(T... expected) {
        return contentListMatcher(Arrays.asList(expected));
    }

    public final ResultMatcher contentListMatcher(List<T> expected) {
        return content().string(
                new TestMatcher<List<T>>(expected) {
                    @Override
                    protected boolean compare(List<T> expected, String actual) {
                        List<Wrapper> expectedList = wrap(expected);
                        List<Wrapper> actualList = wrap(fromJsonValues(actual));
                        return expectedList.equals(actualList);
                    }
                });
    }

    private T fromJsonValue(String json) {
        return readValue(json, entityClass);
    }

    private List<T> fromJsonValues(String json) {
        return readValues(json, entityClass);
    }

    private List<T> readValues(String json, Class<T> entityClass) {
        try {
            return readerMap.computeIfAbsent(entityClass, objectMapper::readerFor).<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    private T readValue(String json, Class<T> tClass) {
        try {
            return readerMap.computeIfAbsent(tClass, objectMapper::readerFor).readValue(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public T fromJsonAction(ResultActions action) throws UnsupportedEncodingException {
        return fromJsonValue(TestUtil.getContent(action));
    }

    private class Wrapper {
        private T entity;

        Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper wrapper = (Wrapper) o;
            return entity != null ? equality.areEqual(entity, wrapper.entity) : wrapper.entity == null;
        }

        @Override
        public String toString() {
            return String.valueOf(entity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(entity);
        }
    }
}

