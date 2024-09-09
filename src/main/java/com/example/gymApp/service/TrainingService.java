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


package com.example.gymApp.service;

import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.TrainingRepository;
import com.example.gymApp.repository.TrainingTypeRepository;
import com.example.gymApp.repository.UserRepository;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class TrainingService {


  private final TraineeRepository traineeRepository;
  private final UserRepository userRepository;
  private final TrainerRepository trainerRepository;
  private final TrainingTypeRepository trainingTypeRepository;
  private final TrainingRepository trainingRepository;


  public TrainingService(TraineeRepository traineeRepository, UserRepository userRepository,
      TrainerRepository trainerRepository, TrainingTypeRepository trainingTypeRepository,
      TrainingRepository trainingRepository) {
    this.traineeRepository = traineeRepository;
    this.userRepository = userRepository;
    this.trainerRepository = trainerRepository;
    this.trainingTypeRepository = trainingTypeRepository;
    this.trainingRepository = trainingRepository;
  }

  public void createTraining(String trainerUsername, String traineeUsername,String trainingName,
      LocalDate dateOfTraining, int durationInHours) {

    Trainer trainer = trainerRepository.findByUserUsername(trainerUsername)
        .orElseThrow(() -> new NoSuchElementException(
            "Trainer with username " + trainerUsername + " not found"));

    Trainee trainee = traineeRepository.findByUserUsername(traineeUsername)
        .orElseThrow(() -> new NoSuchElementException(
            "Trainee with username " + traineeUsername + " not found"));

    log.info(trainee.toString());

    Training training = new Training();
    training.setTrainer(trainer);
    training.setTrainee(trainee);
    training.setTrainingType(trainer.getSpecialization());
    training.setTrainingName(trainingName);
    training.setTrainingDate(dateOfTraining);
    training.setTrainingDuration(durationInHours);

    trainingRepository.save(training);

    log.info("Training " + training + " successfully saved");
  }
}



