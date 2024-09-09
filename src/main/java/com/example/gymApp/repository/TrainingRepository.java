package com.example.gymApp.repository;

import com.example.gymApp.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
  // Дополнительные методы поиска, если нужны
}