package com.example.gymApp.service;

import com.example.gymApp.dao.TraineeDAO;
import com.example.gymApp.model.Trainee;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class TraineeService {

  private TraineeDAO traineeDAO;

  @Autowired
  public void setTraineeDAO(TraineeDAO traineeDAO) {
    this.traineeDAO = traineeDAO;
  }


  public boolean createTrainee(Trainee trainee) {

    if (trainee.getUsername() == null || trainee.getUsername().isEmpty()) {
      String generatedUsername = ProfileService.generateUsername(trainee.getFirstName(),
          trainee.getLastName());
      trainee.setUsername(generatedUsername);
      log.info("Generated username for trainee {}: {}", trainee.getId(), generatedUsername);
    }

    if (trainee.getPassword() == null || trainee.getPassword().isEmpty()) {
      String generatedPassword = ProfileService.generateRandomPassword();
      trainee.setPassword(generatedPassword);
      log.info("Generated password for trainee {}: {}", trainee.getId(), generatedPassword);
    }

    if (!traineeDAO.createTrainee(trainee)) {
      throw new NoSuchElementException("Trainee with ID " + trainee.getId() + " is not created.");

    }
    log.info("Trainee {} has been saved successfully.", trainee.getId());
    return true;
  }

  public boolean updateTrainee(Trainee trainee) {

    if (!traineeDAO.updateTrainee(trainee)) {
      throw new NoSuchElementException("Trainee with ID " + trainee.getId() + " is not updated.");
    }
    return true;
  }


  public boolean deleteTrainee(Long id) {
    if (!traineeDAO.deleteTrainee(id)) {
      throw new NoSuchElementException("Trainee with ID " + id + " not found.");
    }
    return true;
  }


  public Trainee getTraineeById(Long id) {
    Trainee trainee = traineeDAO.getTraineeById(id);
    if (trainee == null) {
      throw new NoSuchElementException("Trainee with ID " + id + " not found.");
    }

    return trainee;
  }


  public List<Trainee> getAllTrainees() {
    try {
      List<Trainee> trainees = traineeDAO.getAllTrainees();
      log.info("Successfully got a list of trainees");
      return trainees;
    } catch (Exception e) {
      log.error("Error fetching all trainees: {}", e.getMessage());
    }
    return null;
  }
}