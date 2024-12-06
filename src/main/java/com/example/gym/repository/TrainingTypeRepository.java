package com.example.gym.repository;

import com.example.gym.model.TrainingType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
  boolean existsByName(String name);

  Optional<TrainingType> findByName(String specialization);

  List<TrainingType> findAll();

}