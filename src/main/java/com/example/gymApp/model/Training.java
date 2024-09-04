package com.example.gymApp.model;

import java.time.LocalDate;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Training {

  private Long id;

  private Trainee trainee;

  private Trainer trainer;

  private String trainingName;

  private String trainingType;

  private LocalDate trainingDate;

  private Integer trainingDuration;


  public Training() {
  }

  public Training(Long id, Trainee trainee, Trainer trainer, String trainingName,
      String trainingType, LocalDate trainingDate, Integer trainingDuration) {

    this.id = id;
    this.trainee = trainee;
    this.trainer = trainer;
    this.trainingName = trainingName;
    this.trainingType = trainingType;
    this.trainingDate = trainingDate;
    this.trainingDuration = trainingDuration;
  }
}
