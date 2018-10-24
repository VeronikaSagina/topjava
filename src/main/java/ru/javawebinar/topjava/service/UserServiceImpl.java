package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    /*public void setRepository(UserRepository repository) {
        this.repository = repository;
    }*/

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        boolean delete = repository.delete(id);
        if (!delete) {
            throw new NotFoundException("No meal for delete");
        }
    }

    @Override
    public User get(int id) throws NotFoundException {
        User user = repository.get(id);
        if (user != null) {
            return user;
        } else throw new NotFoundException("user does not exist");
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        User byEmail = repository.getByEmail(email);
        if (byEmail != null) {
            return byEmail;
        } else throw new NotFoundException("user with this email does not exist");
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
