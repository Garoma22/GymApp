package com.example.gymApp.service;

import com.example.gymApp.dao.TrainingDAO;
import com.example.gymApp.dao.TraineeDAO;
import com.example.gymApp.dao.TrainerDAO;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrainingServiceTest {

  @Mock
  private TrainingDAO trainingDAO;

  @Mock
  private TraineeDAO traineeDAO;

  @Mock
  private TrainerDAO trainerDAO;

  @InjectMocks
  private TrainingService trainingService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateTrainingSuccess() {

    Long traineeId = 1L;
    Long trainerId = 1L;
    Trainee trainee = new Trainee();
    trainee.setId(traineeId);

    Trainer trainer = new Trainer();
    trainer.setId(trainerId);

    when(traineeDAO.getTraineeById(traineeId)).thenReturn(trainee);
    when(trainerDAO.getTrainerById(trainerId)).thenReturn(trainer);
    when(trainingDAO.createTraining(any(Training.class))).thenReturn(true);


    boolean result = trainingService.createTraining(1L, traineeId, trainerId, "Cardio", "Intense", LocalDate.now(), 60);


    assertTrue(result);
  }

  @Test
  public void testCreateTrainingTraineeNotFound() {

    Long traineeId = 1L;
    Long trainerId = 1L;

    when(traineeDAO.getTraineeById(traineeId)).thenReturn(null);

    boolean result = trainingService.createTraining(1L, traineeId, trainerId, "Cardio", "Intense", LocalDate.now(), 60);


    assertFalse(result);
    verify(trainingDAO, never()).createTraining(any(Training.class));
  }

  @Test
  public void testCreateTrainingTrainerNotFound() {

    Long traineeId = 1L;
    Long trainerId = 1L;
    Trainee trainee = new Trainee();
    trainee.setId(traineeId);

    when(traineeDAO.getTraineeById(traineeId)).thenReturn(trainee);
    when(trainerDAO.getTrainerById(trainerId)).thenReturn(null);

    boolean result = trainingService.createTraining(1L, traineeId, trainerId, "Cardio", "Intense", LocalDate.now(), 60);

    assertFalse(result);
    verify(trainingDAO, never()).createTraining(any(Training.class));
  }

  @Test
  public void testGetTrainingByIdSuccess() {

    Long trainingId = 1L;
    Training training = new Training();
    training.setId(trainingId);

    when(trainingDAO.getTrainingById(trainingId)).thenReturn(training);


    Training result = trainingService.getTrainingById(trainingId);


    assertNotNull(result);
    assertEquals(trainingId, result.getId());
    verify(trainingDAO).getTrainingById(trainingId);
  }

  @Test
  public void testGetTrainingByIdNotFound() {

    Long trainingId = 1L;

    when(trainingDAO.getTrainingById(trainingId)).thenReturn(null);


    assertThrows(NoSuchElementException.class, () -> trainingService.getTrainingById(trainingId));
    verify(trainingDAO).getTrainingById(trainingId);
  }

  @Test
  public void testGetAllTrainingsSuccess() {
    Training training1 = new Training();
    training1.setId(1L);

    Training training2 = new Training();
    training2.setId(2L);

    when(trainingDAO.getAllTrainings()).thenReturn(Arrays.asList(training1, training2));


    List<Training> result = trainingService.getAllTrainings();


    assertNotNull(result);
    assertEquals(2, result.size());
    verify(trainingDAO).getAllTrainings();
  }

  @Test
  public void testGetAllTrainingsError() {

    when(trainingDAO.getAllTrainings()).thenThrow(new RuntimeException("Database error"));


    RuntimeException exception = assertThrows(RuntimeException.class, () -> trainingService.getAllTrainings());
    assertEquals("Unable to retrieve trainings.", exception.getMessage());
    verify(trainingDAO).getAllTrainings();
  }
}
