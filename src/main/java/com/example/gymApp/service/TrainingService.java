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
      log.info("Training could not be created because of false status of trainee : " + trainee
          + " or trainer :" + trainer);
      throw new IllegalArgumentException(
          "Training could not be created due to inactive trainee or trainer.");
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


  @Transactional
  public void createTraining2args(List<Trainer> trainers, Trainee trainee) {

    for (Trainer trainer : trainers) {

      List<Training> existingTrainings = trainingRepository.findTrainings(trainer, trainee,
          LocalDate.parse("2222-11-11"));

      if (existingTrainings.isEmpty()) {

        //todo : add some logic for isActive status of users

        Training training = new Training();

        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainer.getSpecialization());
        training.setTrainingName(trainer.getUser().getUsername() + " - " + trainee.getUsername()
            + " test training with hardcode data");
        training.setTrainingDate(LocalDate.parse("2222-11-11")); //hardcode here!
        training.setTrainingDuration(1);  //hardcode!
        trainingRepository.save(training);
        System.out.println("New training is created: " + training);
      }
    }
  }


  @Transactional
  public Training createTraining5args(Trainer trainer, Trainee trainee, String trainingName,
      LocalDate trainingDate, Integer trainingDuration) {

    //todo : add some logic for isActive status of users
    //todo : add some logic for uniques of trainings

    Training training = new Training();

    training.setTrainer(trainer);
    training.setTrainee(trainee);
    training.setTrainingType(trainer.getSpecialization());
    training.setTrainingName(trainer.getUser().getUsername() + " - " + trainee.getUsername() + " - "
        + trainingName);
    training.setTrainingDate(trainingDate); //hardcode here!
    training.setTrainingDuration(trainingDuration);  //hardcode!
    trainingRepository.save(training);
    System.out.println("New training is created: " + training);

  return training;
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

  public List<Trainer> getAllTrainersByTraineeUsername(String traineeUsername) {
    return trainingRepository.getAllTrainersByTrainee(traineeUsername);
  }


  public List<Training> findTraineeTrainingsByUsername(String traineeUsername) {

    return trainingRepository.findTraineeTrainingsByUsername(traineeUsername);
  }

  public List<Training> findTrainerTrainingsByUsername(String trainerUsername) {
    return trainingRepository.findTrainerTrainingsByUsername(trainerUsername);
  }
}




