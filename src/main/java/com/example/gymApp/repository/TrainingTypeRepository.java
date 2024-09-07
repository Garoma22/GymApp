package com.example.gymApp.repository;

import com.example.gymApp.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
  TrainingType findByTypeName(String typeName);
}