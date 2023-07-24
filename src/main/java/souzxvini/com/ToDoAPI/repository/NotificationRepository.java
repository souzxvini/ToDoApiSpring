package souzxvini.com.ToDoAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import souzxvini.com.ToDoAPI.model.Activity;
import souzxvini.com.ToDoAPI.model.Notification;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserEmail(String username);

    Optional<Notification> findByUserEmailAndId(String username, Integer id);

}
