package com.example.gymApp.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.Data;
import java.time.LocalDateTime;
//
//@Entity
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



import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
//
//@Entity
//@Table(name = "trainings")  // Указываем имя таблицы
//@Data
//public class Training {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "id")  // Явно указываем колонку для id
//  private Long id;
//
//  @ManyToOne
//  @JoinColumn(name = "trainee_id", nullable = false)
//  private Trainee trainee;
//
//  @ManyToOne
//  @JoinColumn(name = "trainer_id", nullable = false)
//  private Trainer trainer;
//
//  @Column(name = "training_name", nullable = false)
//  private String trainingName;
//
//  @Column(name = "training_type", nullable = false)
//  private String trainingType;
//
//  @Column(name = "training_date", nullable = false)
//  private LocalDate trainingDate;
//
//  @Column(name = "training_duration", nullable = false)
//  private Integer trainingDuration;
//
//  public Training() {
//  }
//
//  public Training(Long id, Trainee trainee, Trainer trainer, String trainingName,
//      String trainingType, LocalDate trainingDate, Integer trainingDuration) {
//    this.id = id;
//    this.trainee = trainee;
//    this.trainer = trainer;
//    this.trainingName = trainingName;
//    this.trainingType = trainingType;
//    this.trainingDate = trainingDate;
//    this.trainingDuration = trainingDuration;
//  }
//}


@Entity
@Table(name = "trainings")  // Указываем имя таблицы
@Data
public class Training {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)  // Primary Key
  private Long id;

  @ManyToOne
  @JoinColumn(name = "trainee_id", referencedColumnName = "id", nullable = false)
  private Trainee trainee;  // Foreign Key на Trainee

  @ManyToOne
  @JoinColumn(name = "trainer_id", referencedColumnName = "id", nullable = false)
  private Trainer trainer;  // Foreign Key на Trainer

  @Column(name = "training_name", nullable = false)
  private String trainingName;  // Название тренировки (обязательно)

  @ManyToOne
  @JoinColumn(name = "training_type_id", referencedColumnName = "id", nullable = false)
  private TrainingType trainingType;  // Foreign Key на TrainingType (обязательно)

  @Column(name = "training_date", nullable = false)
  private LocalDate trainingDate;  // Дата тренировки (обязательно)

  @Column(name = "training_duration", nullable = false)
  private Integer trainingDuration;  // Продолжительность тренировки (обязательно)

  // Конструкторы
  public Training() {
  }

//  public Training(Long id, Trainee trainee, Trainer trainer, String trainingName,
//      TrainingType trainingType, LocalDate trainingDate, Integer trainingDuration) {
//    this.id = id;
//    this.trainee = trainee;
//    this.trainer = trainer;
//    this.trainingName = trainingName;
//    this.trainingType = trainingType;
//    this.trainingDate = trainingDate;
//    this.trainingDuration = trainingDuration;
//  }

  public Training(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, LocalDate trainingDate, Integer trainingDuration) {
    this.trainee = trainee;
    this.trainer = trainer;
    this.trainingName = trainingName;
    this.trainingType = trainingType;
    this.trainingDate = trainingDate;
    this.trainingDuration = trainingDuration;
  }

}