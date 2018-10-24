package ru.javawebinar.topjava.repository.mock;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.NamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
//@Primary
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static Map<Integer, User> mapRepository = new ConcurrentHashMap<>();
    private static final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public User getByEmail(String email) {
        Optional<Map.Entry<Integer, User>> integerUserEntry = mapRepository.entrySet().stream()
                .filter(m -> m.getValue().getEmail().equals(email))
                .findFirst();
        return integerUserEntry
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new NotFoundException("user with this email does not exist"));
    }

    @SuppressWarnings("Duplicates")
    @Override
    public User save(User user) {
        LOG.info("Save " + user);
        if (user.isNew()) {
            user.setId(currentId.incrementAndGet());
        }
        mapRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        LOG.info("Delete " + id);
        User remove = mapRepository.remove(id);
        return remove != null;
    }

    @Override
    public User get(int id) {
        LOG.info("Get " + id);
        return mapRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("Get all users.");
        List<User> values = new ArrayList<>( mapRepository.values());
        values.sort(Comparator.comparing(User::getName).thenComparing(User::getEmail));
        return values;
    }
}
