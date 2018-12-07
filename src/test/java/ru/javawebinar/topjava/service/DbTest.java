package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.instanceOf;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class DbTest {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private static Map<String, Long> timesMap = new ConcurrentHashMap<>();

    static {
        SLF4JBridgeHandler.install();
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            timesMap.put(description.getMethodName(), nanos);
            LOG.info(description.getMethodName(), nanos);
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            timesMap.put(description.getMethodName() + " failed", nanos);
            LOG.info(description.getMethodName() + " failed", e, nanos);
        }

        @Override
        protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
            LOG.info(description.getMethodName() + " skipped", e, nanos);
        }

        @Override
        protected void finished(long nanos, Description description) {
            timesMap.put(description.getMethodName(), nanos);
            LOG.info(description.getMethodName(), nanos);
        }
    };

    @AfterClass
    public static void statistics() {
        System.out.println("_______________________________________");
        for (Map.Entry<String, Long> entry : timesMap.entrySet()) {
            System.out.println(String.format("%s %s мс",
                    entry.getKey(), TimeUnit.NANOSECONDS.toMillis(entry.getValue())));
        }
        System.out.println("_______________________________________");
    }

    public static <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(getRootCause(e), instanceOf(exceptionClass));
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;
        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
