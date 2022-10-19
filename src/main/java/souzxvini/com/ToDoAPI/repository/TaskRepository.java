package souzxvini.com.ToDoAPI.repository;

import souzxvini.com.ToDoAPI.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserName(String name);

}
