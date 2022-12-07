package souzxvini.com.ToDoAPI.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import souzxvini.com.ToDoAPI.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserEmail(String name, Pageable pageable);
    List<Task> findByUserEmail(String name);

}
