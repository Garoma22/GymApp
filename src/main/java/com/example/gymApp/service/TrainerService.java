package com.example.gymApp.service;

import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.TrainingRepository;
import com.example.gymApp.repository.TrainingTypeRepository;
import com.example.gymApp.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
public class TrainerService {

  private final TrainerRepository trainerRepository;
  private final UserRepository userRepository;
  private final TrainingTypeRepository trainingTypeRepository;
  private final TraineeRepository traineeRepository;
  private final TrainingRepository trainingRepository;
  private final Converter<Trainer, TrainerDto> trainerToTrainerDtoConverter;


  @Autowired
  public TrainerService(TrainerRepository trainerRepository, UserRepository userRepository,
      TrainingTypeRepository trainingTypeRepository, TraineeRepository traineeRepository,
      TrainingRepository trainingRepository,
      Converter<Trainer, TrainerDto> trainerToTrainerDtoConverter) {
    this.trainerRepository = trainerRepository;
    this.userRepository = userRepository;

    this.trainingTypeRepository = trainingTypeRepository;
    this.traineeRepository = traineeRepository;
    this.trainingRepository = trainingRepository;
    this.trainerToTrainerDtoConverter = trainerToTrainerDtoConverter;
  }

  public List<TrainerDto> getAllTrainersByTrainee(String traineeUsername) {
    List<Trainer> trainers = getAlltrainersByTrainee(traineeUsername);
    return trainers.stream()
        .map(trainerToTrainerDtoConverter::convert)  //using converter
        .collect(Collectors.toList());
  }

  public Trainer saveTrainer(Trainer trainer) {
    return trainerRepository.saveAndFlush(trainer);
  }



  @Transactional
  public Trainer updateTrainer(Trainer trainer, String name, String lastName, String username,
      String password, Boolean activeStatus, String specialization) {

    Optional<TrainingType> trainingTypeOptional = trainingTypeRepository.findByName(specialization);
    if (trainingTypeOptional.isEmpty()) {
      throw new IllegalArgumentException("Specialization not found in the database");
    }

    TrainingType trainingType = trainingTypeOptional.get();

    trainer.getUser().setFirstName(name);
    trainer.getUser().setLastName(lastName);
    trainer.getUser().setUsername(username);
    trainer.getUser().setPassword(password);
    trainer.getUser().setActive(activeStatus);
    trainer.setSpecialization(trainingType);

    trainerRepository.save(trainer);

    return trainer;
  }

  public TrainingType checkSpecializationCorrectness(String specialization) {
    Optional<TrainingType> trainingType = trainingTypeRepository.findByName(specialization);
    if (trainingType.isEmpty()) {
      throw new IllegalArgumentException("Specialization not found in the database");
    }
    return trainingType.get();
  }


  public Trainer createTrainer(String firstName, String lastName, String username, String password,
      String specialization) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Username of trainer already exists");
    }

    TrainingType trainingType = checkSpecializationCorrectness(specialization);

    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setUsername(username);
    user.setPassword(password);
    user.setActive(true);

    Trainer trainer = new Trainer(user, trainingType);

    trainerRepository.save(trainer);

    log.info("Trainer created successfully: {}", trainer.getUser().getUsername());
    return trainer;
  }


  public Trainer getTrainerByUsername(String username) {
    return trainerRepository.findByUserUsername(username)
        .orElseThrow(
            () -> new NoSuchElementException("No trainer found with the username: " + username));
  }

  public User getTrainerByPassword(String password) {

    Optional<User> userOpt = userRepository.findByPassword(password);

    if (userOpt.isEmpty()) {
      throw new IllegalArgumentException("No user found with the provided password");
    }

    User user = userOpt.get();

    return user;


  }


  public List<Trainer> getAllTrainersNotAssignedToTrainee(String traineeUsername) {

    return trainerRepository.getAllTrainersNotAssignedToTrainee(traineeUsername);

  }

  public List<Trainer> getAlltrainersByTrainee(String traineeUsername) {

    Optional<Trainee> traineeOpt = traineeRepository.findByUserUsername(traineeUsername);

    if (traineeOpt.isEmpty()) {
      throw new NoSuchElementException("No trainee found for the provided user");
    }
    Trainee trainee = traineeOpt.get();

    return trainingRepository.findDistinctTrainersByTrainee(trainee);
  }


  public List<Trainer> getAllActiveTrainersNotAssignedToTrainee(Trainee trainee) {

    return trainingRepository.findAllActiveTrainersNotAssignedToTrainee(trainee.getUsername());



  }

  public List<Trainer> findByUsernameIn(List<String> newTrainersNames) {

    return trainerRepository.findByUserUsernameIn(newTrainersNames);
  }
}

