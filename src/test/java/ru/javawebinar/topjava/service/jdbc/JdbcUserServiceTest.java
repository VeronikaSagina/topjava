package ru.javawebinar.topjava.service.jdbc;

import org.junit.Before;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;

@ActiveProfiles(Profiles.JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Before
    public void setUp() {
        service.evictCache();
    }

    @Override
    public void testValidation() throws Exception {

    }
}
