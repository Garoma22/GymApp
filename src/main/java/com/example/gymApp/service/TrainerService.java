package com.example.gymApp.service;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainer.TrainerDto;

import com.example.gymApp.dto.trainer.TrainerResponseDto;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import com.example.gymApp.dto.trainee.TraineeMapper;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.dto.trainer.TrainerWithTraineeListDto;
import com.example.gymApp.model.Training;


@Slf4j
@Service
@AllArgsConstructor
public class TrainerService {

  private final TrainerRepository trainerRepository;
  private final UserRepository userRepository;
  private final TrainingTypeRepository trainingTypeRepository;
  private final TraineeRepository traineeRepository;
  private final TrainingRepository trainingRepository;
  private final Converter<Trainer, TrainerDto> trainerToTrainerDtoConverter;
  private final TraineeMapper traineeMapper;
  private final TrainerMapper trainerMapper;



  public TrainerWithTraineeListDto updateTrainerProfile(TrainerDto trainerDto) {
    Trainer trainer = trainerRepository.findByUserUsername(trainerDto.getUsername())
        .orElseThrow(() -> new NoSuchElementException(
            "No trainer found with the username: " + trainerDto.getUsername()));

    trainer.getUser().setFirstName(trainerDto.getFirstName());
    trainer.getUser().setLastName(trainerDto.getLastName());
    trainer.setSpecialization(
        checkSpecializationCorrectness(trainerDto.getTrainingType().getName()));

    log.info("THIS IS UPDATED TRAINER FROM DB : " + trainer);

    List<Trainee> trainees = trainer.getTrainings().stream().map(Training::getTrainee).toList();
    log.info("THIS ARE HIS TRAINEES: " + trainees);

    trainerRepository.save(trainer);
    log.info("UPDATED trainer is SAVED : " + trainer);

    return trainerMapper.toDto(trainer, trainees);
  }


  public List<TrainerDto> getAllTrainersDtoByTrainee(String traineeUsername) {
    List<Trainer> trainers = getAlltrainersByTrainee(traineeUsername);

    return trainers.stream()
        .map(trainerToTrainerDtoConverter::convert)  //using converter
        .collect(Collectors.toList());
  }

  public List<Trainer> getAlltrainersByTrainee(String traineeUsername) {
    Trainee trainee = traineeRepository.findByUserUsername(traineeUsername)
        .orElseThrow(() -> new NoSuchElementException("No trainee found for the provided user"));
    return trainingRepository.findDistinctTrainersByTrainee(trainee);
  }

  public Trainer saveTrainer(Trainer trainer) {
    return trainerRepository.saveAndFlush(trainer);
  }

  @Transactional
  public Trainer updateTrainer(Trainer trainer, String name, String lastName, String username,
      String password, Boolean activeStatus, String specialization) {
    TrainingType trainingType = trainingTypeRepository.findByName(specialization)
        .orElseThrow(() -> new IllegalArgumentException("Specialization not found in the database"));


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
    return trainingTypeRepository.findByName(specialization)
        .orElseThrow(() -> new IllegalArgumentException("Specialization not found in the database"));
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

  public List<Trainer> getAllTrainersNotAssignedToTrainee(String traineeUsername) {
    return trainerRepository.getAllTrainersNotAssignedToTrainee(traineeUsername);
  }

  public List<TrainerResponseDto> getAllActiveTrainersNotAssignedToTrainee(String traineeusername) {
    Trainee trainee = traineeRepository.findByUserUsername(traineeusername)
        .orElseThrow(
            () -> new NoSuchElementException(
                "No trainer found with the username: " + traineeusername));
    List<Trainer> trainers = trainingRepository.findAllActiveTrainersNotAssignedToTrainee(trainee.getUsername());
    return trainerMapper.toTrainerResponseDto(trainers);
  }

  public List<Trainer> findByUsernameIn(List<String> newTrainersUserNames) {
    return trainerRepository.findByUserUsernameIn(newTrainersUserNames);
  }

  public TrainerWithTraineeListDto getTrainerWithTrainees(String username) {
    Trainer trainer = getTrainerByUsername(username);
    List<Trainee> trainees =  trainingRepository.findDistinctTraineeByTrainer(trainer);
    List<TraineeDto> traineeDtosList = traineeMapper.toTraineeDtoList(trainees);
    return trainerMapper.toTrainerWithTraineeListDto(trainer,
        traineeDtosList
    );





  }


}

