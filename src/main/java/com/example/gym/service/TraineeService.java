package com.example.gym.service;

import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainee.TraineeMapper;
import com.example.gym.dto.trainee.TraineeWithTrainerListDto;
import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.dto.trainer.TrainerMapper;
import com.example.gym.dto.trainer.TrainerResponseDto;
import com.example.gym.model.Role;
import com.example.gym.model.Trainee;

import com.example.gym.model.Trainer;

import com.example.gym.model.User;
import com.example.gym.repository.TraineeRepository;
import com.example.gym.repository.TrainingRepository;
import com.example.gym.repository.UserRepository;
import java.util.NoSuchElementException;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.converter.Converter;
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
  private final TraineeMapper traineeMapper;
  private final Converter<Trainer, TrainerDto> trainerToTrainerDtoConverter;


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
    // userRepository.save(user);

    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setDateOfBirth(dateOfBirth);
    trainee.setAddress(address);
    trainee.getUser().setRole(Role.TRAINEE);

    return traineeRepository.saveAndFlush(trainee);
  }

  public List<Trainee> getAllTrainees() {
    return traineeRepository.findAll();
  }


  public Trainee getTraineeById(Long id) {
    return traineeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Trainee not found with id: " + id));
  }



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

    //remove trainings
    trainingRepository.findByTrainee(trainee)
        .forEach(trainingRepository::delete);

    //remove trainee
    traineeRepository.delete(trainee);
    log.info("Trainee and related entities deleted successfully.");
  }


  public TraineeWithTrainerListDto getTraineeProfileWithTrainersList(String username) {
    Trainee trainee = getTraineeByUsername(username);
    List<TrainerDto> trainersList = getAllTrainersDtoByTrainee(username);
    return traineeMapper.toTraineeWithTrainerListDto(trainee, trainersList); //mapstruct
  }
  // this method is private because it works only inside this class

  List<TrainerDto> getAllTrainersDtoByTrainee(String traineeUsername) {
    Set<Trainer> trainers = trainerService.getAlltrainersByTrainee(traineeUsername);

    return trainers.stream()
        .map(trainerToTrainerDtoConverter::convert)  //using converter
        .collect(Collectors.toList());
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

    List<TrainerDto> trainersDtoList = getAllTrainersDtoByTrainee(username);

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




