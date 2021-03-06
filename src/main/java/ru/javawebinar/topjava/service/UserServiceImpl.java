package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.UserUtil.prepareToSave;
import static ru.javawebinar.topjava.util.UserUtil.updateFromUserTo;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository repository;
    private MealRepository mealRepository;

    @Autowired
    public UserServiceImpl( UserRepository repository, MealRepository mealRepository) {
        this.repository = repository;
        this.mealRepository = mealRepository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    @Transactional
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    @Transactional
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(prepareToSave(user));
    }

    @Override
    public User get(int id) throws NotFoundException {
      return checkNotFoundWithId(repository.get(id), id);
    }


    @Transactional
    @Override
    public User getWithMeal(int id) throws NotFoundException {
        User user = get(id);
        Collection<Meal> all = mealRepository.getAll(id);
        user.setMeals(new ArrayList<>(all));
        return user;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
       return checkNotFound(repository.getByEmail(email), email);
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        repository.save(prepareToSave(user));
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        User user1 = updateFromUserTo(user, userTo);
        repository.save(prepareToSave(user1));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void evictCache() {

    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    @Override
    public void enable(int id, boolean enable) {
        User user = get(id);
        user.setEnabled(enable);
        repository.save(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null){
            throw new UsernameNotFoundException(String.format("User %s is not found", email));
        }
        return new AuthorizedUser(user);
    }
}
