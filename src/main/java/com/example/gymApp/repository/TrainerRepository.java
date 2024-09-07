package com.example.gymApp.repository;

import com.example.gymApp.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
  // Кастомные запросы для Trainer можно добавить здесь
}
