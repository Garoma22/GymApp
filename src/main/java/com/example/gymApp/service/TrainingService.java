//package com.example.gymApp.service;
//
//import com.example.gymApp.dao.TrainingDAO;
//import com.example.gymApp.dao.TraineeDAO;
//import com.example.gymApp.dao.TrainerDAO;
//import com.example.gymApp.model.Training;
//import com.example.gymApp.model.Trainee;
//import com.example.gymApp.model.Trainer;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.NoSuchElementException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class TrainingService {
//
//  private final TrainingDAO trainingDAO;
//  private final TraineeDAO traineeDAO;
//  private final TrainerDAO trainerDAO;
//
//  @Autowired
//  public TrainingService(TrainingDAO trainingDAO, TraineeDAO traineeDAO, TrainerDAO trainerDAO) {
//    this.trainingDAO = trainingDAO;
//    this.traineeDAO = traineeDAO;
//    this.trainerDAO = trainerDAO;
//  }
//
//  public boolean createTraining(Long id, Long traineeId, Long trainerId, String trainingName, String trainingType, LocalDate trainingDate, int duration) {
//    try {
//
//      Trainee trainee = traineeDAO.getTraineeById(traineeId);
//      if (trainee == null) {
//        throw new NoSuchElementException("Trainee with ID " + traineeId + " not found.");
//      }
//
//      Trainer trainer = trainerDAO.getTrainerById(trainerId);
//      if (trainer == null) {
//        throw new NoSuchElementException("Trainer with ID " + trainerId + " not found.");
//      }
//
//
//      Training training = new Training();
//      training.setId(id);
//      training.setTrainee(trainee);
//      training.setTrainer(trainer);
//      training.setTrainingName(trainingName);
//      training.setTrainingDate(trainingDate);
//      training.setTrainingDuration(duration);
//      training.setTrainingType(trainingType);
//
//
//      if (!trainingDAO.createTraining(training)) {
//        throw new NoSuchElementException("Training with ID " + id + " could not be created.");
//      }
//      log.info("Training {} has been created successfully.", id);
//      return true;
//
//    } catch (NoSuchElementException e) {
//      log.error("Error creating training: {}", e.getMessage());
//      return false; // Возвращаем false, если произошла ошибка
//    }
//  }
//
//  public Training getTrainingById(Long id) {
//    Training training = trainingDAO.getTrainingById(id);
//    if (training == null) {
//      throw new NoSuchElementException("Training with ID " + id + " not found.");
//    }
//    return training;
//  }
//
//  public List<Training> getAllTrainings() {
//    try {
//      List<Training> trainings = trainingDAO.getAllTrainings();
//      log.info("Successfully retrieved list of trainings.");
//      return trainings;
//    } catch (Exception e) {
//      log.error("Error fetching all trainings: {}", e.getMessage());
//      throw new RuntimeException("Unable to retrieve trainings.");
//    }
//  }
//}
