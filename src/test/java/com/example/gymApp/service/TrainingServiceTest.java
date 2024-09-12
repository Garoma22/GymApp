package com.example.gymApp.service;

import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TrainingServiceTest {

  @Mock
  private TraineeRepository traineeRepository;

  @Mock
  private TrainerRepository trainerRepository;

  @Mock
  private TrainingRepository trainingRepository;

  @Mock
  private TrainingTypeRepository trainingTypeRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TrainingService trainingService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createTraining_ShouldCreateTraining_WhenDataIsValid() {

    String trainerUsername = "trainerUsername";
    String traineeUsername = "traineeUsername";
    String trainingName = "Yoga Session";
    LocalDate dateOfTraining = LocalDate.now();
    int durationInHours = 2;

    TrainingType trainingType = new TrainingType();
    trainingType.setName("Yoga");

    Trainer trainer = new Trainer();
    trainer.setUser(new User());
    trainer.getUser().setActive(true);
    trainer.setSpecialization(trainingType);

    Trainee trainee = new Trainee();
    trainee.setUser(new User());
    trainee.getUser().setActive(true);

    when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.of(trainer));
    when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(Optional.of(trainee));

    trainingService.createTraining(trainerUsername, traineeUsername, trainingName, dateOfTraining, durationInHours);

    verify(trainingRepository).save(any(Training.class));
  }


  @Test
  void createTraining_ShouldThrowException_WhenTrainerIsInactive() {
    String trainerUsername = "inactiveTrainer";
    String traineeUsername = "activeTrainee";
    String trainingName = "Session";
    LocalDate dateOfTraining = LocalDate.now();
    int durationInHours = 2;


    Trainer trainer = new Trainer();
    trainer.setUser(new User());
    trainer.getUser().setActive(false);


    TrainingType trainingType = new TrainingType();
    trainingType.setName("Yoga");
    trainer.setSpecialization(trainingType);


    Trainee trainee = new Trainee();
    trainee.setUser(new User());
    trainee.getUser().setActive(true);

    when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.of(trainer));
    when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(Optional.of(trainee));

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
        trainingService.createTraining(trainerUsername, traineeUsername, trainingName, dateOfTraining, durationInHours)
    );

    assertEquals("Training could not be created due to inactive trainee or trainer.", thrown.getMessage());

    verify(trainingRepository, never()).save(any(Training.class));
  }


  @Test
  void createTraining_ShouldThrowException_WhenTraineeNotFound() {
    String trainerUsername = "trainerUsername";
    String traineeUsername = "nonExistentTrainee";
    String trainingName = "Session";
    LocalDate dateOfTraining = LocalDate.now();
    int durationInHours = 2;

    when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.of(new Trainer()));
    when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(Optional.empty());

    NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () ->
        trainingService.createTraining(trainerUsername, traineeUsername, trainingName, dateOfTraining, durationInHours)
    );

    assertEquals("Trainee with username " + traineeUsername + " not found", thrown.getMessage());
    verify(trainingRepository, never()).save(any(Training.class));
  }

  @Test
  void getTrainingsByUserUsername_ShouldReturnTrainings() {
    String traineeUsername = "traineeUsername";
    LocalDate startDate = LocalDate.now().minusDays(10);
    LocalDate finishDate = LocalDate.now();
    String trainerName = "trainerName";
    String specialization = "Yoga";


    List<Training> trainings = List.of(new Training());
    when(trainingRepository.getAllTrainingsByTraineeAndCriteria(traineeUsername, startDate, finishDate, trainerName, specialization))
        .thenReturn(trainings);


    List<Training> result = trainingService.getTrainingsByUserUsername(traineeUsername, startDate, finishDate, trainerName, specialization);


    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(trainingRepository).getAllTrainingsByTraineeAndCriteria(traineeUsername, startDate, finishDate, trainerName, specialization);
  }

  @Test
  void getTraineesList_ShouldReturnTraineesTrainings() {
    String trainerUsername = "trainerUsername";
    String traineeName = "traineeName";
    LocalDate startDate = LocalDate.now().minusDays(10);
    LocalDate finishDate = LocalDate.now();

    List<Training> trainings = List.of(new Training());
    when(trainingRepository.getAllTrainingsByTrainerAndCriteria(trainerUsername, traineeName, startDate, finishDate))
        .thenReturn(trainings);

    List<Training> result = trainingService.getTraineesList(trainerUsername, traineeName, startDate, finishDate);

    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(trainingRepository).getAllTrainingsByTrainerAndCriteria(trainerUsername, traineeName, startDate, finishDate);
  }
}
