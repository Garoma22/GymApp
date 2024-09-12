package com.example.gymApp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "trainings")
public class Training {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @ManyToOne
  @JoinColumn(name = "trainer_id", nullable = false)
  private Trainer trainer;


  @ManyToOne
  @JoinColumn(name = "trainee_id", nullable = false)
  private Trainee trainee;

  @ManyToOne
  @JoinColumn(name = "training_type_id", nullable = false)
  private TrainingType trainingType;

  @Column(name = "training_name", nullable = false)
  private String trainingName;

  @Column(name = "training_date", nullable = false)
  private LocalDate trainingDate;

  @Column(name = "training_duration", nullable = false)
  private Integer trainingDuration;

  public Training() {}

  public Training(Trainer trainer, Trainee trainee, TrainingType trainingType, String trainingName, LocalDate trainingDate, Integer trainingDuration) {
    this.trainer = trainer;
    this.trainee = trainee;
    this.trainingType = trainingType;
    this.trainingName = trainingName;
    this.trainingDate = trainingDate;
    this.trainingDuration = trainingDuration;
  }

  @Override
  public String toString() {
    return "Training{" +
        "id=" + id +
        ", trainer=" + trainer.getUsername() +
        ", trainee=" + trainee.getUsername() +
        ", trainingType=" + trainingType.getName() +
        ", trainingName='" + trainingName + '\'' +
        ", trainingDate=" + trainingDate +
        ", trainingDuration=" + trainingDuration +
        '}';
  }
}
