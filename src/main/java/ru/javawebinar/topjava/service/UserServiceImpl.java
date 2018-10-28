package ru.javawebinar.topjava.service;


import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
/*public void setRepository(UserRepository repository) {
        this.repository = repository;
    }*/

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(int id) throws NotFoundException {
/*        boolean delete = repository.delete(id);
        if (!delete) {
            throw new NotFoundException("No meal for delete");
        }*/
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
      /*  User user = repository.get(id);
        if (user != null) {
            return user;
        } else throw new NotFoundException("user does not exist");*/
      return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
       /* User byEmail = repository.getByEmail(email);
        if (byEmail != null) {
            return byEmail;
        } else throw new NotFoundException("user with this email does not exist");*/
       return checkNotFound(repository.getByEmail(email), email);
    }


    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(User user) {
        repository.save(user);
    }
}
