package com.example.gymApp.service;

import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.TrainingRepository;
import com.example.gymApp.repository.TrainingTypeRepository;
import com.example.gymApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TrainerServiceTest {

  @Mock
  private TrainerRepository trainerRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TrainingTypeRepository trainingTypeRepository;

  @Mock
  private TraineeRepository traineeRepository;

  @Mock
  private TrainingRepository trainingRepository;

  @InjectMocks
  private TrainerService trainerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createTrainer_ShouldCreateTrainer_WhenValidDataProvided() {

    String firstName = "John";
    String lastName = "Doe";
    String username = "john.doe";
    String password = "password";
    String specialization = "Fitness";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    when(trainingTypeRepository.findByName(specialization)).thenReturn(Optional.of(new TrainingType()));

    Trainer createdTrainer = trainerService.createTrainer(firstName, lastName, username, password, specialization);

    assertNotNull(createdTrainer);
    assertEquals(username, createdTrainer.getUser().getUsername());
    verify(trainerRepository).save(any(Trainer.class));
  }

  @Test
  void createTrainer_ShouldThrowException_WhenUsernameExists() {
    String username = "john.doe";
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
        trainerService.createTrainer("John", "Doe", username, "password", "Fitness")
    );

    assertEquals("Username of trainer already exists", thrown.getMessage());
    verify(trainerRepository, never()).save(any(Trainer.class));
  }

  @Test
  void updateTrainer_ShouldUpdateTrainer_WhenValidDataProvided() {
    Trainer trainer = new Trainer();
    trainer.setUser(new User());

    String newName = "UpdatedName";
    String newLastName = "UpdatedLastName";
    String newUsername = "updated.username";
    String newPassword = "newPassword";
    Boolean activeStatus = true;
    String specialization = "Yoga";

    TrainingType trainingType = new TrainingType();
    when(trainingTypeRepository.findByName(specialization)).thenReturn(Optional.of(trainingType));

    Trainer updatedTrainer = trainerService.updateTrainer(trainer, newName, newLastName, newUsername, newPassword, activeStatus, specialization);

    assertEquals(newName, updatedTrainer.getUser().getFirstName());
    assertEquals(newLastName, updatedTrainer.getUser().getLastName());
    assertEquals(newUsername, updatedTrainer.getUser().getUsername());
    assertEquals(newPassword, updatedTrainer.getUser().getPassword());
    assertTrue(updatedTrainer.getUser().isActive());
    assertEquals(trainingType, updatedTrainer.getSpecialization());

    verify(trainerRepository).save(trainer);
  }

  @Test
  void updateTrainer_ShouldThrowException_WhenSpecializationNotFound() {
    Trainer trainer = new Trainer();
    when(trainingTypeRepository.findByName("NonExistingSpecialization")).thenReturn(Optional.empty());

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
        trainerService.updateTrainer(trainer, "John", "Doe", "john.doe", "password", true, "NonExistingSpecialization")
    );

    assertEquals("Specialization not found in the database", thrown.getMessage());
    verify(trainerRepository, never()).save(any(Trainer.class));
  }

  @Test
  void deleteTraineeByUsername_ShouldDeleteTrainee_WhenTraineeExists() {
    String username = "traineeUsername";
    User user = new User();
    Trainee trainee = new Trainee();
    trainee.setUser(user);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    when(traineeRepository.findByUser(user)).thenReturn(Optional.of(trainee));
    when(trainingRepository.findByTrainee(trainee)).thenReturn(Collections.emptyList());

    trainerService.deleteTraineeByUsername(username);

    verify(trainingRepository).findByTrainee(trainee);
    verify(trainingRepository).deleteAll(Collections.emptyList());
    verify(traineeRepository).delete(trainee);
  }

  @Test
  void deleteTraineeByUsername_ShouldThrowException_WhenUserNotFound() {
    when(userRepository.findByUsername("nonExistentUsername")).thenReturn(Optional.empty());

    NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () ->
        trainerService.deleteTraineeByUsername("nonExistentUsername")
    );

    assertEquals("No user found with the provided username", thrown.getMessage());
    verify(traineeRepository, never()).delete(any(Trainee.class));
  }

  @Test
  void getAllTrainersNotAssignedToTrainee_ShouldReturnTrainers() {
    String traineeUsername = "traineeUsername";
    List<Trainer> trainers = List.of(new Trainer());

    when(trainerRepository.getAllTrainersNotAssignedToTrainee(traineeUsername)).thenReturn(trainers);

    List<Trainer> result = trainerService.getAllTrainersNotAssignedToTrainee(traineeUsername);

    assertEquals(trainers, result);
    verify(trainerRepository).getAllTrainersNotAssignedToTrainee(traineeUsername);
  }

  @Test
  void getTrainerByUsername_ShouldReturnTrainer_WhenTrainerExists() {
    String username = "trainerUsername";
    Trainer trainer = new Trainer();

    when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));

    Trainer result = trainerService.getTrainerByUsername(username);

    assertEquals(trainer, result);
  }

  @Test
  void getTrainerByUsername_ShouldThrowException_WhenTrainerNotFound() {
    String username = "trainerUsername";
    when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.empty());

    NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () ->
        trainerService.getTrainerByUsername(username)
    );

    assertEquals("No trainer found with the username: " + username, thrown.getMessage());
  }
}
