package com.example.gymApp.repository;

import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

  Optional<Trainer> findByUserUsername(String username);

  Optional<Object> findByUser(User user);
}