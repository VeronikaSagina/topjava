package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1")
    int delete(int id);

    User getByEmail(String email);

    @Query("SELECT u FROM User u join fetch u.roles where u.id = ?1")
    User findById(int id);

    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.FETCH)
    List<User> findAll(Sort sort);
}
