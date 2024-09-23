package com.example.gymApp.service;

import com.example.gymApp.model.Trainee;

import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.TrainingRepository;
import com.example.gymApp.repository.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TraineeService {

  private final TraineeRepository traineeRepository;
  private final UserRepository userRepository;
  private final TrainingRepository trainingRepository;
  private final TrainerRepository trainerRepository;

  @Autowired
  public TraineeService(TraineeRepository traineeRepository, UserRepository userRepository,
      TrainingRepository trainingRepository, TrainerRepository trainerRepository) {
    this.traineeRepository = traineeRepository;
    this.userRepository = userRepository;
    this.trainingRepository = trainingRepository;
    this.trainerRepository = trainerRepository;
  }

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

  public Trainee getTraineeById(Long id) {
    Optional<Trainee> trainee = traineeRepository.findById(id);
    if (trainee.isPresent()) {
      return trainee.get();
    } else {
      throw new IllegalArgumentException("Trainee not found with id: " + id);
    }
  }

  @Transactional
  public void deleteTrainee(Long id) {
    Trainee trainee = getTraineeById(id);
    traineeRepository.delete(trainee);
  }

  public Trainee getTraineeByUsername(String username) {
    return traineeRepository.findByUserUsername(username)
        .orElseThrow(
            () -> new NoSuchElementException("No trainee found with the username: " + username));
  }

  public Trainee updateTrainee(Trainee trainee, String newName, String newLastName,
      String newUsername,
      String newPassword, boolean activeStatus, String address,
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

    Optional<User> userOpt = userRepository.findByUsername(username);

    if (userOpt.isEmpty()) {
      throw new NoSuchElementException("No user found with the provided username");
    }

    User user = userOpt.get();

    Optional<Trainee> traineeOpt = traineeRepository.findByUser(user);

    if (traineeOpt.isEmpty()) {
      throw new NoSuchElementException("No trainee found for the provided user");
    }
    Trainee trainee = traineeOpt.get();

    //deleting connected entities manually!!!
    List<Training> trainings = trainingRepository.findByTrainee(trainee);
    trainingRepository.deleteAll(trainings);

    traineeRepository.delete(trainee);
    System.out.println("Trainee and related entities deleted successfully.");
  }

  public List<Trainee> getAllTraineesByTrainerUsername(String trainerUsername) {

    Optional<Trainer> trainerOpt = trainerRepository.findByUserUsername(trainerUsername);

    if (trainerOpt.isEmpty()) {
      throw new NoSuchElementException("No trainee found for the provided user");
    }
    Trainer trainer = trainerOpt.get();

    return trainingRepository.findDistinctTraineeByTrainer(trainer);
  }
}


