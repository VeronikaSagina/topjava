package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    Collection<Meal> findAllMealsByUser_id(int id, Sort sort);

    List<Meal> findMealsByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Meal getByIdAndUserId(int id, int userId);

    @EntityGraph(attributePaths = {"user", "user.roles"}, type = EntityGraph.EntityGraphType.FETCH)
    Meal findFirstByIdAndUserId(int id, int userId);

    int deleteMealByIdAndUserId(int id, int userId);
}
