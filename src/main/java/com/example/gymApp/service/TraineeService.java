//package com.example.gymApp.service;
//
//import com.example.gymApp.dao.TraineeDAO;
//import com.example.gymApp.model.Trainee;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.NoSuchElementException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class TraineeService {
//
//  private final TraineeDAO traineeDAO;
//
//  @Autowired
//  public TraineeService(TraineeDAO traineeDAO) {
//    this.traineeDAO = traineeDAO;
//  }
//
//  public boolean createTrainee(Long id, String firstName, String lastName, LocalDate dateOfBirth,
//      String address) {
//    try {
//      Trainee trainee = new Trainee();
//      trainee.setId(id);
//      trainee.setFirstName(firstName);
//      trainee.setLastName(lastName);
//      trainee.setDateOfBirth(dateOfBirth);
//      trainee.setAddress(address);
//
//      validateTrainee(trainee);
//
//
//      if (trainee.getUsername() == null || trainee.getUsername().isEmpty()) {
//        String generatedUsername = ProfileService.generateUsername(firstName, lastName);
//        trainee.setUsername(generatedUsername);
//        log.info("Generated username for trainee {}: {}", id, generatedUsername);
//      }
//      try {
//        if (trainee.getPassword() == null || trainee.getPassword().isEmpty()) {
//          String generatedPassword = ProfileService.generateRandomPassword();
//          trainee.setPassword(generatedPassword);
//          log.info("Generated password for trainee {}: {}", id, generatedPassword);
//        }
//      } catch (Exception e) {
//        log.error("Error generating password for trainee {}: {}", id, e.getMessage());
//        throw new RuntimeException("Failed to generate password for trainee.");
//      }
//
//
//      if (!traineeDAO.createTrainee(trainee)) {
//        throw new NoSuchElementException("Trainee with ID " + id + " could not be created.");
//      }
//      log.info("Trainee {} has been created successfully.", id);
//      return true;
//
//    } catch (IllegalArgumentException e) {
//      log.error("Error creating trainee: {}", e.getMessage());
//      return false; // Возвращаем false, если произошла ошибка валидации
//    }
//  }
//
//
//
//  public boolean updateTrainee(Long id, String firstName, String lastName, LocalDate dateOfBirth,
//      String address) {
//    try {
//
//      Trainee trainee = getTraineeById(id);
//
//      trainee.setFirstName(firstName);
//      trainee.setLastName(lastName);
//      trainee.setDateOfBirth(dateOfBirth);
//      trainee.setAddress(address);
//
//      validateTrainee(trainee);
//
//      if (!traineeDAO.updateTrainee(trainee)) {
//        throw new NoSuchElementException("Trainee with ID " + id + " could not be updated.");
//      }
//      log.info("Trainee {} has been updated successfully.", id);
//      return true;
//
//    } catch (IllegalArgumentException e) {
//      log.error("Error updating trainee: {}", e.getMessage());
//      return false; // Возвращаем false или сообщение об ошибке
//    }
//  }
//
//  public boolean deleteTrainee(Long id) {
//    try {
//      if (!traineeDAO.deleteTrainee(id)) {
//        throw new NoSuchElementException("Trainee with ID " + id + " not found.");
//      }
//      log.info("Trainee {} has been deleted successfully.", id);
//      return true;
//    } catch (NoSuchElementException e) {
//      log.error("Error deleting trainee: {}", e.getMessage());
//      return false; // Возвращаем false или сообщение об ошибке
//    }
//  }
//
//  public Trainee getTraineeById(Long id) {
//    Trainee trainee = traineeDAO.getTraineeById(id);
//    if (trainee == null) {
//      throw new NoSuchElementException("Trainee with ID " + id + " not found.");
//    }
//    return trainee;
//  }
//
//  public List<Trainee> getAllTrainees() {
//    try {
//      List<Trainee> trainees = traineeDAO.getAllTrainees();
//      log.info("Successfully retrieved list of trainees.");
//      return trainees;
//    } catch (Exception e) {
//      log.error("Error fetching all trainees: {}", e.getMessage());
//      throw new RuntimeException("Unable to retrieve trainees.");
//    }
//  }
//
//  private void validateTrainee(Trainee trainee) {
//    if (trainee.getId() == null || trainee.getId() <= 0) {
//      throw new IllegalArgumentException("Invalid ID: " + trainee.getId());
//    }
//    if (trainee.getFirstName() == null || trainee.getFirstName().trim().isEmpty()) {
//      throw new IllegalArgumentException("First name cannot be empty.");
//    }
//    if (trainee.getLastName() == null || trainee.getLastName().trim().isEmpty()) {
//      throw new IllegalArgumentException("Last name cannot be empty.");
//    }
//    if (trainee.getDateOfBirth() == null || trainee.getDateOfBirth().isAfter(LocalDate.now())) {
//      throw new IllegalArgumentException("Invalid date of birth.");
//    }
//    if (trainee.getAddress() == null || trainee.getAddress().trim().isEmpty()) {
//      throw new IllegalArgumentException("Address cannot be empty.");
//    }
//  }
//}


package com.example.gymApp.service;

import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.UserRepository;
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

  @Autowired
  public TraineeService(TraineeRepository traineeRepository, UserRepository userRepository) {
    this.traineeRepository = traineeRepository;
    this.userRepository = userRepository;
  }

  // Метод для создания нового Trainee
  @Transactional
  public Trainee createTrainee(String firstName, String lastName, String username, String password,
      LocalDate dateOfBirth, String address) {
    // Проверка, существует ли пользователь с таким username
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Username already exists.");
    }

    // Создание нового пользователя
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setUsername(username);
    user.setPassword(password);
    user.setActive(true); // Выставляем активность пользователя

    // no need to save it separately! И
//    userRepository.save(user);

    // Создание нового трейни и привязка его к пользователю
    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setDateOfBirth(dateOfBirth);
    trainee.setAddress(address);

    // Сохранение трейни в базе
    return traineeRepository.save(trainee);
  }

  // Метод для получения списка всех трейни
  public List<Trainee> getAllTrainees() {
    return traineeRepository.findAll();
  }

  // Метод для получения трейни по ID
  public Trainee getTraineeById(Long id) {
    Optional<Trainee> trainee = traineeRepository.findById(id);
    if (trainee.isPresent()) {
      return trainee.get();
    } else {
      throw new IllegalArgumentException("Trainee not found with id: " + id);
    }
  }

  // Метод для обновления данных о трейни
//  @Transactional
//  public Trainee updateTrainee(Long id, String firstName, String lastName, LocalDate dateOfBirth, String address) {
//    Trainee trainee = getTraineeById(id); // Получение существующего трейни
//
//    // Обновление данных
//    trainee.getUser().setFirstName(firstName);
//    trainee.getUser().setLastName(lastName);
//    trainee.setDateOfBirth(dateOfBirth);
//    trainee.setAddress(address);
//
//    // Сохранение изменений
//    return traineeRepository.save(trainee);
//  }

  // Метод для удаления трейни
  @Transactional
  public void deleteTrainee(Long id) {
    Trainee trainee = getTraineeById(id); // Проверка существования трейни
    traineeRepository.delete(trainee);    // Удаление трейни
  }

  public Trainee getTraineeByUsername(String username) {
    return traineeRepository.findByUserUsername(username)
        .orElseThrow(
            () -> new IllegalArgumentException("No trainee found with the username: " + username));
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

    traineeRepository.save(trainee);

    return trainee;


  }
}


