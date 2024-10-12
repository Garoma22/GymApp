package com.example.gymApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.gymApp.dto.trainee.TraineeMapper;
import com.example.gymApp.dto.trainee.TraineeTrainingRequestDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.dto.trainer.TrainerTrainingRequestDto;
import com.example.gymApp.dto.training.TrainingForTraineeResponseDto;
import com.example.gymApp.dto.training.TrainingForTrainerResponseDto;
import com.example.gymApp.dto.trainingType.TrainingForTraineeMapper;
import com.example.gymApp.dto.trainingType.TrainingForTrainerMapper;
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
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {


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

  @Mock
  private TrainerMapper trainerMapper;

  @Mock
  private TraineeMapper traineeMapper;

  @Mock
  private TrainingService trainingService;

  @Mock
  private TrainerService trainerService;

  @Mock
  private TrainingForTraineeMapper trainingForTraineeMapper;


  @BeforeEach
  void setUp() {
    trainingService = new TrainingService(traineeRepository, userRepository, trainerRepository,
        trainingTypeRepository, trainingRepository, trainingForTraineeMapper, trainerMapper);
  }


  @Test
  void testCreateTraining_success() {
    String trainerUsername = "trainerUsername";
    String traineeUsername = "traineeUsername";
    Trainer trainer = createMockTrainer(trainerUsername, "cardio");
    Trainee trainee = createMockTrainee(traineeUsername, "someAddress");
    when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.of(trainer));
    when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(Optional.of(trainee));
    LocalDate trainingDate = LocalDate.now();
    int durationInHours = 2;

    trainingService.createTraining(trainerUsername, traineeUsername, "Fitness Training", trainingDate, durationInHours);
    verify(trainingRepository, times(1)).save(any(Training.class));
  }


    @Test
    void testCreateTraining_trainerNotFound() {
      // Arrange
      String trainerUsername = "trainerUsername";
      String traineeUsername = "traineeUsername";

      when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.empty());

      LocalDate trainingDate = LocalDate.now();
      int durationInHours = 2;

      // Act & Assert
      NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
        trainingService.createTraining(trainerUsername, traineeUsername, "Fitness Training", trainingDate, durationInHours);
      });

      assertEquals("Trainer with username trainerUsername not found", exception.getMessage());
      verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void testCreateTraining_traineeNotFound() {

      String trainerUsername = "trainerUsername";
      String traineeUsername = "traineeUsername";

      Trainer trainer = createMockTrainer(trainerUsername, "cardio");

      when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.of(trainer));
      when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(Optional.empty());

      LocalDate trainingDate = LocalDate.now();
      int durationInHours = 2;


      NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
        trainingService.createTraining(trainerUsername, traineeUsername, "Fitness Training", trainingDate, durationInHours);
      });

      assertEquals("Trainee with username traineeUsername not found", exception.getMessage());
      verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void testCreateTraining_inactiveTrainer() {

      String trainerUsername = "trainerUsername";
      String traineeUsername = "traineeUsername";

      Trainer trainer = createMockTrainer(trainerUsername, "cardio");
      trainer.getUser().setActive(false);

      Trainee trainee = createMockTrainee(traineeUsername, "someAddress");
      trainee.getUser().setActive(true);


      when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.of(trainer));
      when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(Optional.of(trainee));

      LocalDate trainingDate = LocalDate.now();
      int durationInHours = 2;

      IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        trainingService.createTraining(trainerUsername, traineeUsername, "Fitness Training", trainingDate, durationInHours);
      });

      assertEquals("Training could not be created due to inactive trainee or trainer.", exception.getMessage());
      verify(trainingRepository, never()).save(any(Training.class));
    }


  @Test
  void testCreateTraining_inactiveTrainee() {

    String trainerUsername = "trainerUsername";
    String traineeUsername = "traineeUsername";

    Trainer trainer = createMockTrainer(trainerUsername, "cardio");
    trainer.getUser().setActive(true);

    Trainee trainee = createMockTrainee(traineeUsername, "someAddress");
    trainee.getUser().setActive(false);

    when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.of(trainer));
    when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(Optional.of(trainee));

    LocalDate trainingDate = LocalDate.now();
    int durationInHours = 2;

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      trainingService.createTraining(trainerUsername, traineeUsername, "Fitness Training", trainingDate, durationInHours);
    });

    assertEquals("Training could not be created due to inactive trainee or trainer.", exception.getMessage());
    verify(trainingRepository, never()).save(any(Training.class));
  }

  @Test
  void testGetTrainingsByUserUsername() {

    String traineeUsername = "traineeUsername";
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate finishDate = LocalDate.of(2023, 12, 31);
    String trainerName = "trainerName";
    String specialization = "cardio";

    Training training1 = new Training();
    training1.setTrainingName("Training 1");

    Training training2 = new Training();
    training2.setTrainingName("Training 2");

    List<Training> expectedTrainings = List.of(training1, training2);

    when(trainingRepository.getAllTrainingsByTraineeAndCriteria(
        traineeUsername, startDate, finishDate, trainerName, specialization))
        .thenReturn(expectedTrainings);

    List<Training> actualTrainings = trainingService.getTrainingsByUserUsername(
        traineeUsername, startDate, finishDate, trainerName, specialization);

    assertNotNull(actualTrainings);
    assertEquals(2, actualTrainings.size());
    assertEquals("Training 1", actualTrainings.get(0).getTrainingName());
    assertEquals("Training 2", actualTrainings.get(1).getTrainingName());

    verify(trainingRepository, times(1)).getAllTrainingsByTraineeAndCriteria(
        traineeUsername, startDate, finishDate, trainerName, specialization);
  }

  @Test
  void testGetTraineesList() {

    String trainerUsername = "trainerUsername";
    String traineeName = "traineeName";
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate finishDate = LocalDate.of(2023, 12, 31);

    Training training1 = new Training();
    training1.setTrainingName("Training 1");

    Training training2 = new Training();
    training2.setTrainingName("Training 2");

    List<Training> expectedTrainings = List.of(training1, training2);

    when(trainingRepository.getAllTrainingsByTrainerAndCriteria(
        trainerUsername, traineeName, startDate, finishDate))
        .thenReturn(expectedTrainings);

    List<Training> actualTrainings = trainingService.getTraineesList(
        trainerUsername, traineeName, startDate, finishDate);

    assertNotNull(actualTrainings);
    assertEquals(2, actualTrainings.size());
    assertEquals("Training 1", actualTrainings.get(0).getTrainingName());
    assertEquals("Training 2", actualTrainings.get(1).getTrainingName());

    verify(trainingRepository, times(1)).getAllTrainingsByTrainerAndCriteria(
        trainerUsername, traineeName, startDate, finishDate);
  }


  @Test
  void testGetAllTrainersByTraineeUsername() {

    String traineeUsername = "traineeUsername";

    Trainer trainer1 = createMockTrainer("trainer1", "cardio");
    Trainer trainer2 = createMockTrainer("trainer2", "cardio");

    List<Trainer> expectedTrainers = List.of(trainer1, trainer2);

    when(trainingRepository.getAllTrainersByTrainee(traineeUsername))
        .thenReturn(expectedTrainers);


    List<Trainer> actualTrainers = trainingService.getAllTrainersByTraineeUsername(traineeUsername);

    assertNotNull(actualTrainers);
    assertEquals(2, actualTrainers.size());
    assertEquals("trainer1", actualTrainers.get(0).getUser().getUsername());
    assertEquals("trainer2", actualTrainers.get(1).getUser().getUsername());

    verify(trainingRepository, times(1)).getAllTrainersByTrainee(traineeUsername);
  }

  @Test
  void testFindTraineeTrainingsByUsername() {
    String traineeUsername = "traineeUsername";

    Training training1 = new Training();
    training1.setTrainingName("Training 1");

    Training training2 = new Training();
    training2.setTrainingName("Training 2");


    List<Training> expectedTrainings = List.of(training1, training2);

    when(trainingRepository.findTraineeTrainingsByUsername(traineeUsername))
        .thenReturn(expectedTrainings);

    List<Training> actualTrainings = trainingService.findTraineeTrainingsByUsername(traineeUsername);


    assertNotNull(actualTrainings);
    assertEquals(2, actualTrainings.size());
    assertEquals("Training 1", actualTrainings.get(0).getTrainingName());
    assertEquals("Training 2", actualTrainings.get(1).getTrainingName());

    verify(trainingRepository, times(1)).findTraineeTrainingsByUsername(traineeUsername);
  }


  @Test
  void testFindTrainerTrainingsByUsername() {

    String trainerUsername = "trainerUsername";

    Training training1 = new Training();
    training1.setTrainingName("Training 1");

    Training training2 = new Training();
    training2.setTrainingName("Training 2");

    List<Training> expectedTrainings = List.of(training1, training2);


    when(trainingRepository.findTrainerTrainingsByUsername(trainerUsername))
        .thenReturn(expectedTrainings);

    List<Training> actualTrainings = trainingService.findTrainerTrainingsByUsername(trainerUsername);


    assertNotNull(actualTrainings);
    assertEquals(2, actualTrainings.size());
    assertEquals("Training 1", actualTrainings.get(0).getTrainingName());
    assertEquals("Training 2", actualTrainings.get(1).getTrainingName());

    verify(trainingRepository, times(1)).findTrainerTrainingsByUsername(trainerUsername);
  }



  @Test
  void testFindTraineeTrainingsWithFilters() {
    // RequestDto - input DTO in arguments
    TraineeTrainingRequestDto requestDto = new TraineeTrainingRequestDto();
    requestDto.setUsername("traineeUsername");
    requestDto.setPeriodFrom(LocalDate.of(2024, 10, 1));
    requestDto.setPeriodTo(LocalDate.of(2024, 10, 31));
    requestDto.setTrainerName("John");
    requestDto.setSpecialization("cardio");

    // Mock trainings and users
    User trainerUser = new User();
    trainerUser.setFirstName("John");

    Trainer trainer = new Trainer();
    trainer.setUser(trainerUser);

    TrainingType specialization = new TrainingType();
    specialization.setName("cardio");

    trainer.setSpecialization(specialization);

    Training training = new Training();
    training.setTrainingDate(LocalDate.of(2024, 10, 10));
    training.setTrainer(trainer);

    List<Training> mockTrainings = List.of(training);

    when(trainingRepository.findTraineeTrainingsByUsername("traineeUsername"))
        .thenReturn(mockTrainings);

    List<TrainingForTraineeResponseDto> mockResponseDtos = List.of(new TrainingForTraineeResponseDto());

    when(trainingForTraineeMapper.toDtoList(anyList())).thenReturn(mockResponseDtos);

    // Act
    List<TrainingForTraineeResponseDto> result = trainingService.findTraineeTrainingsWithFilters(requestDto);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(trainingRepository, times(1)).findTraineeTrainingsByUsername("traineeUsername");
    verify(trainingForTraineeMapper, times(1)).toDtoList(anyList());
  }

  @Test
  void testGetTrainerTrainingsByCriteria_withAllFilters() {

    String username = "trainerUsername";
    String periodFrom = "2024-10-01";
    String periodTo = "2024-10-31";
    String traineeFirstName = "TraineeFirstName";

    TrainerTrainingRequestDto requestDto = new TrainerTrainingRequestDto();
    requestDto.setUsername(username);
    requestDto.setPeriodFrom(LocalDate.parse(periodFrom));
    requestDto.setPeriodTo(LocalDate.parse(periodTo));
    requestDto.setTraineeFirstName(traineeFirstName);

    // Mock mappings and trainings
    when(trainerMapper.toDto(username, periodFrom, periodTo, traineeFirstName))
        .thenReturn(requestDto);

    User traineeUser = new User();
    traineeUser.setFirstName(traineeFirstName);

    Trainee trainee = new Trainee();
    trainee.setUser(traineeUser);

    Training training = new Training();  //trainings
    training.setTrainingDate(LocalDate.of(2024, 10, 10));
    training.setTrainee(trainee);

    List<Training> mockTrainings = List.of(training);

    when(trainingRepository.findTrainerTrainingsByUsername(username))
        .thenReturn(mockTrainings);


    List<TrainingForTrainerResponseDto> mockResponseDtos = TrainingForTrainerMapper.INSTANCE.toDtoList(mockTrainings);

    // Act
    List<TrainingForTrainerResponseDto> result = trainingService.getTrainerTrainingsByCriteria(
        username, periodFrom, periodTo, traineeFirstName);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(trainingRepository, times(1)).findTrainerTrainingsByUsername(username);
    assertEquals(traineeFirstName, result.get(0).getTraineeName());
  }


  private Trainee createMockTrainee(String username, String address) {
    User user = new User();
    user.setUsername(username);
    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setAddress(address);
    trainee.getUser().setActive(true);
    return trainee;
  }

  private Trainer createMockTrainer(String username, String specializationName) {
    User userTrainer = new User();
    userTrainer.setUsername(username);

    TrainingType specialization = new TrainingType();
    specialization.setName(specializationName);

    Trainer trainer = new Trainer();
    trainer.setUser(userTrainer);
    trainer.setSpecialization(specialization);
    trainer.getUser().setActive(true);

    return trainer;
  }
}
