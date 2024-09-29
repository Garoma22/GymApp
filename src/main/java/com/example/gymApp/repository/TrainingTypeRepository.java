package com.example.gymApp.repository;

import com.example.gymApp.model.TrainingType;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
  boolean existsByName(String name);

  Optional<TrainingType> findByName(String specialization);

  List<TrainingType> findAll();

}