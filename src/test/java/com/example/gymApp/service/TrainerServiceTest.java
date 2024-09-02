package com.example.gymApp.service;

import com.example.gymApp.dao.TrainerDAO;
import com.example.gymApp.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

  private TrainerService trainerService;
  private TrainerDAO trainerDAO;

  @BeforeEach
  void setUp() {
    trainerDAO = Mockito.mock(TrainerDAO.class);
    trainerService = new TrainerService();
    trainerService.setTrainerDAO(trainerDAO);
  }

  @Test
  void testCreateTrainer_success() {
    // Arrange
    Trainer trainer = new Trainer();
    trainer.setId(1L);
    trainer.setFirstName("John");
    trainer.setLastName("Doe");
    trainer.setSpecialization("Fitness");
    trainer.setUsername("john.doe");
    trainer.setPassword("password123");
    trainer.setActive(true);

    when(trainerDAO.createTrainer(any(Trainer.class))).thenReturn(true);

    boolean result = trainerService.createTrainer(trainer);

    assertTrue(result);
    verify(trainerDAO, times(1)).createTrainer(trainer);
  }

  @Test
  void testCreateTrainer_exceptionWhenDaoFails() {

    Trainer trainer = new Trainer();
    trainer.setId(1L);
    trainer.setFirstName("John");
    trainer.setLastName("Doe");
    trainer.setSpecialization("Fitness");

    when(trainerDAO.createTrainer(any(Trainer.class))).thenReturn(false);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      trainerService.createTrainer(trainer);
    });
    assertEquals("Trainer with ID 1 is not created.", exception.getMessage());
    verify(trainerDAO, times(1)).createTrainer(trainer);
  }

  @Test
  void testUpdateTrainer_success() {

    Trainer trainer = new Trainer();
    trainer.setId(1L);
    trainer.setFirstName("John");
    trainer.setLastName("Doe");
    trainer.setSpecialization("Fitness");
    trainer.setUsername("john.doe");
    trainer.setPassword("password123");
    trainer.setActive(true);

    when(trainerDAO.updateTrainer(any(Trainer.class))).thenReturn(true);

    boolean result = trainerService.updateTrainer(trainer);

    assertTrue(result);
    verify(trainerDAO, times(1)).updateTrainer(trainer);
  }


  @Test
  void testUpdateTrainer_exceptionWhenDaoFails() {

    Trainer trainer = new Trainer();
    trainer.setId(1L);
    trainer.setFirstName("John");
    trainer.setLastName("Doe");
    trainer.setSpecialization("Fitness");

    when(trainerDAO.updateTrainer(any(Trainer.class))).thenReturn(false);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      trainerService.updateTrainer(trainer);
    });

    assertEquals("Trainer with ID 1 is not updated.", exception.getMessage());

    verify(trainerDAO, times(1)).updateTrainer(trainer);
  }


  @Test
  void testDeleteTrainer_success() {

    Long trainerId = 1L;
    when(trainerDAO.deleteTrainer(trainerId)).thenReturn(true);

    boolean result = trainerService.deleteTrainer(trainerId);

    assertTrue(result);
    verify(trainerDAO, times(1)).deleteTrainer(trainerId);
  }

  @Test
  void testGetTrainerById_success() {

    Long trainerId = 1L;
    Trainer trainer = new Trainer();
    trainer.setId(trainerId);
    trainer.setFirstName("John");
    trainer.setLastName("Doe");
    trainer.setSpecialization("Fitness");

    when(trainerDAO.getTrainerById(trainerId)).thenReturn(trainer);

    Trainer result = trainerService.getTrainerById(trainerId);

    assertNotNull(result);
    assertEquals(trainerId, result.getId());
    verify(trainerDAO, times(1)).getTrainerById(trainerId);
  }

  @Test
  void testGetAllTrainers_success() {

    List<Trainer> trainers = List.of(new Trainer(), new Trainer());
    when(trainerDAO.getAllTrainers()).thenReturn(trainers);

    List<Trainer> result = trainerService.getAllTrainers();

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(trainerDAO, times(1)).getAllTrainers();
  }
}
