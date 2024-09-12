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
import java.util.List;
import java.util.NoSuchElementException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


@Transactional
  public void createTraining(String trainerUsername, String traineeUsername, String trainingName,
      LocalDate dateOfTraining, int durationInHours) {

    Trainer trainer = trainerRepository.findByUserUsername(trainerUsername)
        .orElseThrow(() -> new NoSuchElementException(
            "Trainer with username " + trainerUsername + " not found"));

    Trainee trainee = traineeRepository.findByUserUsername(traineeUsername)
        .orElseThrow(() -> new NoSuchElementException(
            "Trainee with username " + traineeUsername + " not found"));

    log.info(trainee.toString());


  if (!trainee.getUser().isActive() || !trainer.getUser().isActive()) {
    log.info("Training could not be created because of false status of trainee : " + trainee + " or trainer :" + trainer);
    throw new IllegalArgumentException("Training could not be created due to inactive trainee or trainer.");
  }

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

  public List<Training> getTrainingsByUserUsername(String traineeUsername, LocalDate startDate,
      LocalDate finishDate, String trainerName, String specialization) {

    return trainingRepository.getAllTrainingsByTraineeAndCriteria(traineeUsername, startDate,
        finishDate, trainerName, specialization);

  }

  public List<Training> getTraineesList(String trainerUsername, String traineeName,
      LocalDate startDate, LocalDate finishDate) {

    return trainingRepository.getAllTrainingsByTrainerAndCriteria(trainerUsername, traineeName,
        startDate, finishDate);
  }
}



