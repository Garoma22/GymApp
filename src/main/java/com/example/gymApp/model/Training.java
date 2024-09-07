//package com.example.gymApp.model;
//
//import java.time.LocalDate;
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Data
//public class Training {
//
//  private Long id;
//
//  private Trainee trainee;
//
//  private Trainer trainer;
//
//  private String trainingName;
//
//  private String trainingType;
//
//  private LocalDate trainingDate;
//
//  private Integer trainingDuration;
//
//
//  public Training() {
//  }
//
//  public Training(Long id, Trainee trainee, Trainer trainer, String trainingName,
//      String trainingType, LocalDate trainingDate, Integer trainingDuration) {
//
//    this.id = id;
//    this.trainee = trainee;
//    this.trainer = trainer;
//    this.trainingName = trainingName;
//    this.trainingType = trainingType;
//    this.trainingDate = trainingDate;
//    this.trainingDuration = trainingDuration;
//  }
//}

package com.example.gymApp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trainings")
public class Training {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  //may be need to add referencedColumnName = "id"

  @ManyToOne
  @JoinColumn(name = "trainer_id", nullable = false)
  private Trainer trainer;

  @ManyToOne
  @JoinColumn(name = "trainee_id", nullable = false)
  private Trainee trainee;

  @ManyToOne
  @JoinColumn(name = "training_type_id", nullable = false)
  private TrainingType trainingType;

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  public Training() {}

  public Training(Trainer trainer, Trainee trainee, TrainingType trainingType, LocalDateTime startTime, LocalDateTime endTime) {
    this.trainer = trainer;
    this.trainee = trainee;
    this.trainingType = trainingType;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public String toString() {
    return "Training{" +
        "id=" + id +
        ", trainer=" + trainer.getUsername() +
        ", trainee=" + trainee.getUsername() +
        ", trainingType=" + " NO GETTER YET " +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        '}';
  }
}

