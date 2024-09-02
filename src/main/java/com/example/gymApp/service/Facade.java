package com.example.gymApp.service;


import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@Component
public class Facade {

  private final TrainerService trainerService;
  private final TraineeService traineeService;
  private final TrainingService trainingService;

  @Autowired
  public Facade(TrainerService trainerService, TraineeService traineeService,
      TrainingService trainingService) {
    this.trainerService = trainerService;
    this.traineeService = traineeService;
    this.trainingService = trainingService;
  }

  // TRAINEE SERVICE


  public boolean createTrainee(Trainee trainee) {
    try {
      traineeService.createTrainee(trainee);
      log.info("Trainee {} is successfully created", trainee.getId());
      return true;
    } catch (NoSuchElementException e) {
      log.info("Error during creation of trainee {}: {}", trainee.getId(), e.getMessage());
      return false;
    }
  }


  public boolean updateTrainee(Trainee trainee) {
    try {
      traineeService.updateTrainee(trainee);
      log.info("Trainee {} is successfully updated", trainee.getId());
      return true;

    } catch (NoSuchElementException e) {
      log.error("Error during update of trainee {}: {}", trainee.getId(), e.getMessage());
      return false;
    }
  }


  public boolean deleteTrainee(Long id) {
    try {
       traineeService.deleteTrainee(id);

        log.info("Trainee {} is successfully deleted", id);
        return true;



    } catch (NoSuchElementException e) {
      log.info("Error during deletion of trainee {}: {}", id, e.getMessage());
      return false;
    }
  }


  public Trainee getTraineeById(Long id) {
    try {
      Trainee trainee = traineeService.getTraineeById(id);
      if (trainee != null) {
        log.info("Trainee {} found", id);
        return trainee;
      }
    } catch (NoSuchElementException e) {
      log.error("Error fetching trainee by id {}: {}", id, e.getMessage());

    }
    return null;
  }

  public List<Trainee> getAllTrainees() {
    try {
      List<Trainee> trainees = traineeService.getAllTrainees();
      log.info("Successfully retrieved {} trainees", trainees.size());
      return trainees;
    } catch (Exception e) {
      log.error("Error fetching all trainees: {}", e.getMessage());

    }
    return null;
  }
  // TRAINER SERVICE

  public void createTrainer(Trainer trainer) {
    try {
      trainerService.createTrainer(trainer);
      log.info("Trainer {} is successfully created", trainer.getId());
    } catch (NoSuchElementException e) {
      log.error("Error during creation of trainer {}: {}", trainer.getId(), e.getMessage());
    }
  }

  public void updateTrainer(Trainer trainer) {
    try {
      trainerService.updateTrainer(trainer);
      log.info("Trainer {} is successfully updated", trainer.getId());

    } catch (NoSuchElementException e) {
      log.error("Error during update of trainer {}: {}", trainer.getId(), e.getMessage());

    }
  }

  public boolean deleteTrainer(Long id) {
    try {
      boolean result = trainerService.deleteTrainer(id);
      if (result) {
        log.info("Trainer {} is successfully deleted", id);
      } else {
        log.warn("Trainer {} could not be found for deletion", id);
      }
      return result;
    } catch (NoSuchElementException e) {
      log.error("Error during deletion of trainer {}: {}", id, e.getMessage());
      return false;
    }
  }

  public Trainer getTrainerById(Long id) {
    try {
      Trainer trainer = trainerService.getTrainerById(id);
      if (trainer != null) {
        log.info("Trainer {} found", id);
      } else {
        log.warn("Trainer {} not found", id);
      }
      return trainer;
    } catch (NoSuchElementException e) {
      log.error("Error fetching trainer by id {}: {}", id, e.getMessage());
      return null;
    }
  }

  public List<Trainer> getAllTrainers() {
    try {
      List<Trainer> trainers = trainerService.getAllTrainers();
      log.info("Successfully retrieved {} trainers", trainers.size());
      return trainers;
    } catch (NoSuchElementException e) {
      log.error("Error fetching all trainers: {}", e.getMessage());
      return null;
    }
  }

  // TRAINING SERVICE

  public void createTraining(Training training) {
    try {
      trainingService.createTraining(training);
      log.info("Training {} is successfully created", training.getId());
    } catch (Exception e) {
      log.error("Error during creation of training {}: {}", training.getId(), e.getMessage());
    }
  }

  public void updateTraining(Training training) {
    try {
      trainingService.updateTraining(training);
      log.info("Training {} is successfully updated", training.getId());
    } catch (Exception e) {
      log.error("Error during update of training {}: {}", training.getId(), e.getMessage());
    }
  }

  public void deleteTraining(Long id) {
    try {
      trainingService.deleteTraining(id);
      log.info("Training {} is successfully deleted", id);
    } catch (Exception e) {
      log.error("Error during deletion of training {}: {}", id, e.getMessage());
    }
  }

  public Training getTrainingById(Long id) {
    try {
      Training training = trainingService.getTrainingById(id);
      if (training != null) {
        log.info("Training {} found", id);
      } else {
        log.warn("Training {} not found", id);
      }
      return training;
    } catch (Exception e) {
      log.error("Error fetching training by id {}: {}", id, e.getMessage());
      return null;
    }
  }

  public List<Training> getAllTrainings() {
    try {
      List<Training> trainings = trainingService.getAllTrainings();
      log.info("Successfully retrieved {} trainings", trainings.size());
      return trainings;
    } catch (Exception e) {
      log.error("Error fetching all trainings: {}", e.getMessage());
      return null;
    }
  }
}

