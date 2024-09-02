package com.example.gymApp.service;

import com.example.gymApp.dao.TrainerDAO;
import com.example.gymApp.model.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class TrainerService {

  private TrainerDAO trainerDAO;

  @Autowired
  public void setTrainerDAO(TrainerDAO trainerDAO) {
    this.trainerDAO = trainerDAO;
  }


  public boolean createTrainer(Trainer trainer) {

    if (trainer.getUsername() == null || trainer.getUsername().isEmpty()) {
      String generatedUsername = ProfileService.generateUsername(trainer.getFirstName(),
          trainer.getLastName());
      trainer.setUsername(generatedUsername);
      log.info("Generated username for trainer {}: {}", trainer.getId(), generatedUsername);
    }

    if (trainer.getPassword() == null || trainer.getPassword().isEmpty()) {
      String generatedPassword = ProfileService.generateRandomPassword();
      trainer.setPassword(generatedPassword);
      log.info("Generated password for trainer {}: {}", trainer.getId(), generatedPassword);
    }

    if (!trainerDAO.createTrainer(trainer)) {
      throw new NoSuchElementException("Trainer with ID " + trainer.getId() + " is not created.");
    }
    log.info("Trainer {} has been saved successfully.", trainer.getId());
    return true;
  }


  public boolean updateTrainer(Trainer trainer) {
    if (!trainerDAO.updateTrainer(trainer)) {
      throw new NoSuchElementException("Trainer with ID " + trainer.getId() + " is not updated.");
    }
    return true;
  }


  public boolean deleteTrainer(Long id) {

   if(!trainerDAO.deleteTrainer(id)){
     throw new NoSuchElementException("Trainer with ID : " + id + " is not deleted.");
    }
    log.info("Trainer {} has been deleted successfully.", id);
    return true;
  }

  public Trainer getTrainerById(Long id) {
    Trainer trainer = trainerDAO.getTrainerById(id);

    if (trainer == null) {
      log.error("Trainer with ID: " + id + " not found.");
      throw new NoSuchElementException("Trainer with ID: " + id + " not found.");
    }
    log.info("Trainer with ID: " + id + " successfully retrieved.");
    return trainer;
  }

  public List<Trainer> getAllTrainers() {
    List<Trainer> trainers = trainerDAO.getAllTrainers();

    if (trainers == null) {
      log.error("Failed to retrieve the list of trainers. The list is null.");
      throw new NoSuchElementException("Failed to retrieve the list of trainers. The list is null.");
    }

    return trainers;
  }
}
