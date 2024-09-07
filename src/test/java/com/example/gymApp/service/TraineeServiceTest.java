//package com.example.gymApp.service;
//
//
//import com.example.gymApp.dao.TraineeDAO;
//import com.example.gymApp.model.Trainee;
//import java.time.DateTimeException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class TraineeServiceTest {
//
//  @Mock
//  private TraineeDAO traineeDAO;
//
//  @InjectMocks
//  private TraineeService traineeService;
//
//  @BeforeEach
//  public void setUp() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Test
//  public void testCreateTraineeSuccess() {
//    Trainee trainee = new Trainee();
//    trainee.setId(1L);
//    trainee.setFirstName("John");
//    trainee.setLastName("Doe");
//    trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
//    trainee.setAddress("123 Main St");
//
//    when(traineeDAO.createTrainee(any(Trainee.class))).thenReturn(true);
//
//    boolean result = traineeService.createTrainee(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St");
//
//    assertTrue(result);
//
//  }
//
//  @Test
//  public void testCreateTraineeValidationError() {
//    boolean emptyName = traineeService.createTrainee(1L, "", "Doe", LocalDate.of(1990, 1, 1), "123 Main St");
//
//    boolean emptyLastName = traineeService.createTrainee(1L, "Ivan", "", LocalDate.of(1990, 1, 1), "123 Main St");
//
//    assertThrows(DateTimeException.class, () -> {
//      traineeService.createTrainee(1L, "Ivan", "Doe", LocalDate.of(99999999, 21, 1101), "123 Main St");
//    });
//
//
//
//    assertFalse(emptyName);
//    assertFalse(emptyLastName);
//
//
//    verify(traineeDAO, never()).createTrainee(any(Trainee.class));
//  }
//
//  @Test
//  public void testUpdateTraineeSuccess() {
//    Trainee existingTrainee = new Trainee();
//    existingTrainee.setId(1L);
//    existingTrainee.setFirstName("John");
//    existingTrainee.setLastName("Doe");
//
//    when(traineeDAO.getTraineeById(1L)).thenReturn(existingTrainee);
//    when(traineeDAO.updateTrainee(any(Trainee.class))).thenReturn(true);
//
//    boolean result = traineeService.updateTrainee(1L, "Jane", "Smith", LocalDate.of(1992, 2, 2), "456 Elm St");
//
//    assertTrue(result);
//
//  }
//
//  @Test
//  public void testUpdateTraineeNotFound() {
//    when(traineeDAO.getTraineeById(1L)).thenReturn(null);
//
//    assertThrows(NoSuchElementException.class, () -> {
//      traineeService.updateTrainee(1L, "Jane", "Smith", LocalDate.of(1992, 2, 2), "456 Elm St");
//    });
//
//    verify(traineeDAO, never()).updateTrainee(any(Trainee.class));
//  }
//
//  @Test
//  public void testDeleteTraineeSuccess() {
//    when(traineeDAO.deleteTrainee(1L)).thenReturn(true);
//
//    boolean result = traineeService.deleteTrainee(1L);
//
//    assertTrue(result);
//  }
//
//  @Test
//  public void testDeleteTraineeNotFound() {
//    when(traineeDAO.deleteTrainee(1L)).thenReturn(false);
//
//    boolean result = traineeService.deleteTrainee(1L);
//
//    assertFalse(result);
//
//  }
//
//  @Test
//  public void testGetTraineeByIdSuccess() {
//    Trainee trainee = new Trainee();
//    trainee.setId(1L);
//    trainee.setFirstName("John");
//
//    when(traineeDAO.getTraineeById(1L)).thenReturn(trainee);
//
//    assertNotNull(traineeService.getTraineeById(1L));
//
//    assertEquals(1L, traineeService.getTraineeById(1L).getId());
//
//  }
//
//  @Test
//  public void testGetTraineeByIdNotFound() {
//    when(traineeDAO.getTraineeById(1L)).thenReturn(null);
//
//    assertThrows(NoSuchElementException.class, () -> {
//      traineeService.getTraineeById(1L);
//    });
//
//    verify(traineeDAO, times(1)).getTraineeById(1L);
//  }
//
//  @Test
//  public void testGetAllTrainees() {
//    Trainee trainee1 = new Trainee();
//    trainee1.setId(1L);
//    Trainee trainee2 = new Trainee();
//    trainee2.setId(2L);
//
//    when(traineeDAO.getAllTrainees()).thenReturn(Arrays.asList(trainee1, trainee2));
//
//    List<Trainee> trainees = traineeService.getAllTrainees();
//
//    assertNotNull(trainees);
//    assertEquals(2, trainees.size());
//
//  }
//}
