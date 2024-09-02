package com.example.gymApp.service;


import com.example.gymApp.dao.TraineeDAO;
import com.example.gymApp.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TraineeServiceTest {

  private TraineeService traineeService;
  private TraineeDAO traineeDAO;

  @BeforeEach
  void setUp() {
    traineeDAO = Mockito.mock(TraineeDAO.class);
    traineeService = new TraineeService();
    traineeService.setTraineeDAO(traineeDAO);
  }

  @Test
  void testCreateTrainee_success() {
    // Arrange
    Trainee trainee = new Trainee();
    trainee.setId(1L);
    trainee.setFirstName("John");
    trainee.setLastName("Doe");
    trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
    trainee.setAddress("123 Main St");
    when(traineeDAO.createTrainee(any(Trainee.class))).thenReturn(true);

    // Act
    boolean result = traineeService.createTrainee(trainee);

    // Assert
    assertTrue(result);
    verify(traineeDAO, times(1)).createTrainee(trainee);
  }


  @Test
  void testCreateTrainee_exceptionWhenDaoFails() {
    // Arrange
    Trainee trainee = new Trainee();
    trainee.setId(1L);
    trainee.setFirstName("John");
    trainee.setLastName("Doe");
    trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
    trainee.setAddress("123 Main St");
    trainee.setUsername("john.doe");
    trainee.setPassword("password123");

    when(traineeDAO.createTrainee(any(Trainee.class))).thenReturn(false);

    // Act & Assert
    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      traineeService.createTrainee(trainee);
    });
    assertEquals("Trainee with ID 1 is not created.", exception.getMessage());
    verify(traineeDAO, times(1)).createTrainee(trainee);
  }


  @Test
  void testUpdateTrainee_success() {
    // Arrange
    Trainee trainee = new Trainee();
    trainee.setId(1L);
    trainee.setFirstName("John");
    trainee.setLastName("Doe");
    trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
    trainee.setAddress("123 Main St");
    trainee.setUsername("john.doe");
    trainee.setPassword("password123");
    trainee.setActive(true);

    when(traineeDAO.updateTrainee(any(Trainee.class))).thenReturn(true);

    // Act
    boolean result = traineeService.updateTrainee(trainee);

    // Assert
    assertTrue(result);
    verify(traineeDAO, times(1)).updateTrainee(trainee);
  }

  @Test
  void testUpdateTrainee_exceptionWhenDaoFails() {
    // Arrange
    Trainee trainee = new Trainee();
    trainee.setId(1L);
    trainee.setFirstName("John");
    trainee.setLastName("Doe");
    trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
    trainee.setAddress("123 Main St");
    trainee.setUsername("john.doe");
    trainee.setPassword("password123");
    trainee.setActive(true);

    when(traineeDAO.updateTrainee(any(Trainee.class))).thenReturn(false);

    // Act & Assert
    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      traineeService.updateTrainee(trainee);
    });
    assertEquals("Trainee with ID 1 is not updated.", exception.getMessage());
    verify(traineeDAO, times(1)).updateTrainee(trainee);
  }


  @Test
  void testDeleteTrainee_success() {
    Long traineeId = 1L;
    when(traineeDAO.deleteTrainee(traineeId)).thenReturn(true);

    boolean result = traineeService.deleteTrainee(traineeId);

    assertTrue(result);
    verify(traineeDAO, times(1)).deleteTrainee(traineeId);
  }

  @Test
  void testDeleteTrainee_exceptionWhenDaoFails() {

    Long traineeId = 1L;
    when(traineeDAO.deleteTrainee(traineeId)).thenReturn(false);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      traineeService.deleteTrainee(traineeId);
    });
    assertEquals("Trainee with ID 1 not found.", exception.getMessage());
    verify(traineeDAO, times(1)).deleteTrainee(traineeId);
  }

  @Test
  void testGetTraineeById_success() {

    Long traineeId = 1L;
    Trainee trainee = new Trainee();
    trainee.setId(traineeId);
    when(traineeDAO.getTraineeById(traineeId)).thenReturn(trainee);

    Trainee result = traineeService.getTraineeById(traineeId);

    assertNotNull(result);
    assertEquals(traineeId, result.getId());
    verify(traineeDAO, times(1)).getTraineeById(traineeId);
  }

  @Test
  void testGetTraineeById_exceptionWhenNotFound() {

    Long traineeId = 1L;
    when(traineeDAO.getTraineeById(traineeId)).thenReturn(null);

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      traineeService.getTraineeById(traineeId);
    });
    assertEquals("Trainee with ID 1 not found.", exception.getMessage());
    verify(traineeDAO, times(1)).getTraineeById(traineeId);
  }

  @Test
  void testGetAllTrainees_success() {

    List<Trainee> trainees = List.of(new Trainee(), new Trainee());
    when(traineeDAO.getAllTrainees()).thenReturn(trainees);

    List<Trainee> result = traineeService.getAllTrainees();

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(traineeDAO, times(1)).getAllTrainees();
  }
}
