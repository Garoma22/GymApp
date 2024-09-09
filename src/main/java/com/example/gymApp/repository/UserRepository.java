package com.example.gymApp.repository;

import com.example.gymApp.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);


  boolean existsByUsername(String username);

//  List<String> findAllByUsernameStartingWith(String baseUsername);
@Query("SELECT u.username FROM User u WHERE u.username LIKE :baseUsername%")
List<String> findAllByUsernameStartingWith(@Param("baseUsername") String baseUsername);


  Optional<User> findByPassword(String password);
}
