package souzxvini.com.ToDoAPI.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import souzxvini.com.ToDoAPI.dto.TasksFilterRequest;
import souzxvini.com.ToDoAPI.model.Category;
import souzxvini.com.ToDoAPI.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByCategoryId(Integer id);

    Optional<Task> findByCategoryUserEmailAndId(String email, Integer id);

    @Query(value = "SELECT t.id, \n" +
            "t.conclusion_status, \n" +
            "t.description, \n" +
            "t.initial_date, \n" +
            "t.deadline, \n" +
            "t.priority, \n" +
            "t.status, \n" +
            "t.category_id \n" +
            "FROM tasks t \n" +
            "WHERE (initial_date >= :initialDate OR initial_date IS NULL) \n" +
            "and (deadline <= :deadline OR deadline IS NULL) \n" +
            "and priority = ifnull(:priority , priority)\n" +
            "and category_id = :categoryId\n" +
            "ORDER BY t.id DESC",
            nativeQuery = true)
    List<Task> getTasksByFilter(@Param("priority") String priority, @Param("initialDate") LocalDate initialDate, @Param("initialDate") LocalDate finalDate);
}
