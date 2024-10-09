package com.example.gymApp.service;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeWithTrainerListDto;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.dto.trainer.TrainerResponseDto;
import com.example.gymApp.model.Trainee;

import com.example.gymApp.model.Trainer;

import com.example.gymApp.model.User;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.TrainingRepository;
import com.example.gymApp.repository.UserRepository;
import java.util.NoSuchElementException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class TraineeService {

  private final TraineeRepository traineeRepository;
  private final UserRepository userRepository;
  private final TrainingRepository trainingRepository;
  private final TrainerService trainerService;
  private final TrainingService trainingService;
  private final TrainerMapper trainerMapper;


  @Transactional
  public Trainee createTrainee(String firstName, String lastName, String username, String password,
      LocalDate dateOfBirth, String address) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Username already exists.");
    }

    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setUsername(username);
    user.setPassword(password);
    user.setActive(true);

    // no need to save it separately! И
//    userRepository.save(user);

    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setDateOfBirth(dateOfBirth);
    trainee.setAddress(address);

    return traineeRepository.save(trainee);
  }

  public List<Trainee> getAllTrainees() {
    return traineeRepository.findAll();
  }

//  public Trainee getTraineeById(Long id) {
//    Optional<Trainee> trainee = traineeRepository.findById(id);
//    if (trainee.isPresent()) {
//      return trainee.get();
//    } else {
//      throw new IllegalArgumentException("Trainee not found with id: " + id);
//    }
//  }

  public Trainee getTraineeById(Long id) {
    return traineeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Trainee not found with id: " + id));
  }


//  @Transactional
//  public void deleteTrainee(Long id) {
//    Trainee trainee = getTraineeById(id);
//    traineeRepository.delete(trainee);
//  }

  public Trainee getTraineeByUsername(String username) {
    return traineeRepository.findByUserUsername(username)
        .orElseThrow(
            () -> new NoSuchElementException("No trainee found with the username: " + username));
  }

  public Trainee updateTrainee(Trainee trainee, String newName, String newLastName,
      String newUsername,
      String newPassword,
      boolean activeStatus,
      String address,
      LocalDate dateOfBirth) {

    trainee.getUser().setFirstName(newName);
    trainee.getUser().setLastName(newLastName);
    trainee.getUser().setUsername(newUsername);
    trainee.getUser().setPassword(newPassword);
    trainee.getUser().setActive(activeStatus);
    trainee.setAddress(address);
    trainee.setDateOfBirth(dateOfBirth);

    traineeRepository.save(trainee);

    return trainee;
  }

  @Transactional
  public void deleteTraineeByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No user found with the provided username"));

    Trainee trainee = traineeRepository.findByUser(user)
        .orElseThrow(() -> new NoSuchElementException("No trainee found for the provided user"));

    trainingRepository.findByTrainee(trainee)
        .forEach(trainingRepository::delete);

    traineeRepository.delete(trainee);
    log.info("Trainee and related entities deleted successfully.");
  }

//  public List<Trainee> getAllTraineesByTrainerUsername(String trainerUsername) {
//
//    Optional<Trainer> trainerOpt = trainerRepository.findByUserUsername(trainerUsername);
//
//    if (trainerOpt.isEmpty()) {
//      throw new NoSuchElementException("No trainee found for the provided user");
//    }
//    Trainer trainer = trainerOpt.get();
//
//    return trainingRepository.findDistinctTraineeByTrainer(trainer);
//
//  }

  public TraineeWithTrainerListDto getTraineeProfileWithTrainersList(String username) {

    Trainee trainee = getTraineeByUsername(username);

    List<TrainerDto> trainersList = trainerService.getAllTrainersDtoByTrainee(username);

    TraineeWithTrainerListDto responseDTO = new TraineeWithTrainerListDto(
        trainee.getUser().getUsername(),
        trainee.getUser().getFirstName(),
        trainee.getUser().getLastName(),
        trainee.getDateOfBirth(),
        trainee.getAddress(),
        trainee.getUser().isActive(),
        trainersList
    );

    return responseDTO;
  }


  public TraineeWithTrainerListDto updateTraineeProfile(TraineeDto traineeDto, String username) {
    Trainee trainee = getTraineeByUsername(username);
    log.info("Before Update: " + trainee);

    trainee.getUser().setFirstName(traineeDto.getFirstName());
    trainee.getUser().setLastName(traineeDto.getLastName());
    trainee.getUser().setActive(traineeDto.isActive());
    trainee.setDateOfBirth(LocalDate.parse(traineeDto.getDateOfBirth()));
    trainee.setAddress(traineeDto.getAddress());
    traineeRepository.save(trainee);

    List<TrainerDto> trainersDtoList = trainerService.getAllTrainersDtoByTrainee(username);

    TraineeWithTrainerListDto responseDto = new TraineeWithTrainerListDto(
        trainee.getUser().getUsername(),
        trainee.getUser().getFirstName(),
        trainee.getUser().getLastName(),
        trainee.getDateOfBirth(),
        trainee.getAddress(),
        trainee.getUser().isActive(),
        trainersDtoList
    );

    return responseDto;
  }

  public List<TrainerResponseDto> updateTraineeTrainers(String traineeUsername,
      List<String> newTrainersUsernames) {

    List<Trainer> foundedTrainers = trainerService.findByUsernameIn(newTrainersUsernames);
    Trainee trainee = getTraineeByUsername(traineeUsername);
    trainingService.createTraining(foundedTrainers, trainee);
    List<Trainer> trainers = trainingService.getAllTrainersByTraineeUsername(traineeUsername);
    log.info("All trainers by trainee " + trainee.getUsername() + " : " + trainers);
    return trainerMapper.toTrainerResponseDto(
        trainers);
  }
}




