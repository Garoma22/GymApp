//package com.example.gymApp.service;
//
//import com.example.gymApp.dao.TrainerDAO;
//import com.example.gymApp.model.Trainer;
//import java.util.List;
//import java.util.NoSuchElementException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class TrainerService {
//
//  private final TrainerDAO trainerDAO;
//
//  @Autowired
//  public TrainerService(TrainerDAO trainerDAO) {
//    this.trainerDAO = trainerDAO;
//  }
//
//  public boolean createTrainer(Long id, String firstName, String lastName, String specialization) {
//    try {
//      Trainer trainer = new Trainer();
//      trainer.setId(id);
//      trainer.setFirstName(firstName);
//      trainer.setLastName(lastName);
//      trainer.setSpecialization(specialization);
//
//      validateTrainer(trainer);
//
//      if (trainer.getUsername() == null || trainer.getUsername().isEmpty()) {
//        String generatedUsername = ProfileService.generateUsername(firstName, lastName);
//        trainer.setUsername(generatedUsername);
//        log.info("Generated username for trainer {}: {}", id, generatedUsername);
//      }
//      try {
//        if (trainer.getPassword() == null || trainer.getPassword().isEmpty()) {
//          String generatedPassword = ProfileService.generateRandomPassword();
//          trainer.setPassword(generatedPassword);
//          log.info("Generated password for trainer {}: {}", id, generatedPassword);
//        }
//      } catch (Exception e) {
//        log.error("Error generating password for trainer {}: {}", id, e.getMessage());
//        throw new RuntimeException("Failed to generate password for trainer.");
//      }
//
//
//      if (!trainerDAO.createTrainer(trainer)) {
//        throw new NoSuchElementException("Trainer with ID " + id + " could not be created.");
//      }
//      log.info("Trainer {} has been created successfully.", id);
//      return true;
//
//    } catch (IllegalArgumentException e) {
//      log.error("Error creating trainer: {}", e.getMessage());
//      return false;
//    }
//  }
//
//  public boolean updateTrainer(Long id, String firstName, String lastName, String specialization) {
//    try {
//
//      Trainer trainer = getTrainerById(id);
//
//      trainer.setFirstName(firstName);
//      trainer.setLastName(lastName);
//      trainer.setSpecialization(specialization);
//
//      validateTrainer(trainer);
//
//      // Сохраняем изменения
//      if (!trainerDAO.updateTrainer(trainer)) {
//        throw new NoSuchElementException("Trainer with ID " + id + " could not be updated.");
//      }
//      log.info("Trainer {} has been updated successfully.", id);
//      return true;
//
//    } catch (IllegalArgumentException e) {
//      log.error("Error updating trainer: {}", e.getMessage());
//      return false;
//    }
//  }
//
//  public boolean deleteTrainer(Long id) {
//    try {
//      if (!trainerDAO.deleteTrainer(id)) {
//        throw new NoSuchElementException("Trainer with ID " + id + " not found.");
//      }
//      log.info("Trainer {} has been deleted successfully.", id);
//      return true;
//    } catch (NoSuchElementException e) {
//      log.error("Error deleting trainer: {}", e.getMessage());
//      return false;
//    }
//  }
//
//  public Trainer getTrainerById(Long id) {
//    Trainer trainer = trainerDAO.getTrainerById(id);
//    if (trainer == null) {
//      throw new NoSuchElementException("Trainer with ID " + id + " not found.");
//    }
//    return trainer;
//  }
//
//  public List<Trainer> getAllTrainers() {
//    try {
//      List<Trainer> trainers = trainerDAO.getAllTrainers();
//      log.info("Successfully retrieved list of trainers.");
//      return trainers;
//    } catch (Exception e) {
//      log.error("Error fetching all trainers: {}", e.getMessage());
//      throw new RuntimeException("Unable to retrieve trainers.");
//    }
//  }
//
//  private void validateTrainer(Trainer trainer) {
//    if (trainer.getId() == null || trainer.getId() <= 0) {
//      throw new IllegalArgumentException("Invalid ID: " + trainer.getId());
//    }
//    if (trainer.getFirstName() == null || trainer.getFirstName().trim().isEmpty()) {
//      throw new IllegalArgumentException("First name cannot be empty.");
//    }
//    if (trainer.getLastName() == null || trainer.getLastName().trim().isEmpty()) {
//      throw new IllegalArgumentException("Last name cannot be empty.");
//    }
//    if (trainer.getSpecialization() == null || trainer.getSpecialization().trim().isEmpty()) {
//      throw new IllegalArgumentException("Specialization cannot be empty.");
//    }
//  }
//}
