package souzxvini.com.ToDoAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import souzxvini.com.ToDoAPI.model.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    Activity findByTaskId(Integer id);

    List<Activity> findByUserEmail(String username);

}
