package souzxvini.com.ToDoAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import souzxvini.com.ToDoAPI.model.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByUserEmail(String email);

    Optional<Category> findByUserEmailAndId(String email, Integer id);

    List<Category> findAllByUserEmail(String email);


}
