package com.example.gym.repository;

import com.example.gym.model.Trainee;
import com.example.gym.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

  Optional<Trainee> findByUserUsername(String username);
  Optional<Trainee> findByUser(User user);
}