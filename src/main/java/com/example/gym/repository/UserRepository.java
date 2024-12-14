package com.example.gym.repository;

import com.example.gym.model.User;
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


  @Query("SELECT u.username FROM User u WHERE u.username LIKE :baseUsername%")
  List<String> findAllByUsernameStartingWith(@Param("baseUsername") String baseUsername);

  Optional<User> findByPassword(String password);


  @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
  Optional<User> findByUsernameAndPassword(@Param("username") String username,
      @Param("password") String password);


}
