package com.example.gym.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.gym.dto.trainee.TraineeTrainingRequestDto;
import com.example.gym.dto.training.TrainingForTraineeResponseDto;
import com.example.gym.dto.trainingtype.TrainingForTraineeMapper;
import com.example.gym.model.Trainee;
import com.example.gym.model.Trainer;
import com.example.gym.model.Training;
import com.example.gym.model.TrainingType;
import com.example.gym.repository.TraineeRepository;
import com.example.gym.repository.TrainerRepository;
import com.example.gym.repository.TrainingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

  @Mock
  private TraineeRepository traineeRepository;
  @Mock
  private TrainerRepository trainerRepository;
  @Mock
  private TrainingRepository trainingRepository;

  @Mock
  private TrainingForTraineeMapper trainingForTraineeMapper;

  @InjectMocks
  private TrainingService trainingService;

  @BeforeEach
  void setUp() {
  }

  private Trainee createMockTrainee(String username, String address){
    com.example.gym.model.User user = new com.example.gym.model.User();
    user.setUsername(username);
    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setAddress(address);
    return trainee;
  }

  private Trainer createMockTrainer(String username, String specializationName) {
    com.example.gym.model.User userTrainer = new com.example.gym.model.User();
    userTrainer.setUsername(username);

    TrainingType specialization = new TrainingType();
    specialization.setName(specializationName);

    Trainer trainer = new Trainer();
    trainer.setUser(userTrainer);
    trainer.setSpecialization(specialization);

    return trainer;
  }



  @Test
  void whenCreateTrainingWithNonExistentTrainer_thenThrowException() {
    String trainerUsername = "nonExistentUsername";
    String traineeUsername = "traineeUsername";
    String trainingName = "Yoga Class";
    LocalDate date = LocalDate.now();
    int duration = 1;

    when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> {
      trainingService.createTraining(trainerUsername, traineeUsername, trainingName, date, duration);
    });
  }






  @Test
  void testCreateTrainingSuccess() {

    List<Trainer> trainers = new ArrayList<>();

    Trainer trainer1 = createMockTrainer("trainerUsername1", "yoga");
    Trainer trainer2 = createMockTrainer("trainerUsername2", "yoga");

    trainers.add(trainer1);
    trainers.add(trainer2);

    Trainee trainee1 = createMockTrainee("traineeUsername1", "some address");

    List<Training> emptyTrainings = new ArrayList<>();

    when(trainingRepository.findTrainings(any(Trainer.class), any(Trainee.class), any(LocalDate.class)))
        .thenReturn(emptyTrainings);

    trainingService.createTraining(trainers, trainee1);


    verify(trainingRepository, times(2)).save(any(Training.class));
  }


  @Test
  void testCreateTrainingWhenTrainingsExist() {
    List<Trainer> trainers = List.of(
        createMockTrainer("trainerUsername1", "yoga"),
        createMockTrainer("trainerUsername2", "yoga")
    );
    Trainee trainee1 = createMockTrainee("traineeUsername1", "some address");

    List<Training> existingTrainings = List.of(new Training());

    when(trainingRepository.findTrainings(any(Trainer.class), any(Trainee.class), any(LocalDate.class)))
        .thenReturn(existingTrainings);

    trainingService.createTraining(trainers, trainee1);

    verify(trainingRepository, times(0)).save(any(Training.class));
  }

  @Test
  void testFindTraineeTrainingsWithFilters_onlyUsername() {

    String username = "john_doe";
    List<Training> mockTrainings = List.of(new Training(), new Training());

    when(trainingRepository.findTraineeTrainingsByUsername(username)).thenReturn(mockTrainings);
    when(trainingForTraineeMapper.toDtoList(mockTrainings)).thenReturn(List.of(new TrainingForTraineeResponseDto(),
        new TrainingForTraineeResponseDto()));

    TraineeTrainingRequestDto requestDto = new TraineeTrainingRequestDto();
    requestDto.setUsername(username);


    List<TrainingForTraineeResponseDto> result = trainingService.findTraineeTrainingsWithFilters(requestDto);


    assertNotNull(result);
    assertEquals(2, result.size());
    verify(trainingRepository, times(1)).findTraineeTrainingsByUsername(username);
    verify(trainingForTraineeMapper, times(1)).toDtoList(mockTrainings);
  }


  @Test
  void testFindTraineeTrainingsWithFilters_periodFilter() {

    String username = "john_doe";


    LocalDate periodFrom = LocalDate.of(2024, 1, 1);
    LocalDate periodTo = LocalDate.of(2024, 1, 31);


    Training trainingInPeriod = new Training();
    trainingInPeriod.setTrainingDate(LocalDate.of(2024, 1, 15));

    Training trainingOutOfPeriod = new Training();
    trainingOutOfPeriod.setTrainingDate(LocalDate.of(2023, 12, 31));

    List<Training> mockTrainings = List.of(trainingInPeriod, trainingOutOfPeriod);


    when(trainingRepository.findTraineeTrainingsByUsername(username)).thenReturn(mockTrainings);


    when(trainingForTraineeMapper.toDtoList(anyList()))
        .thenReturn(List.of(new TrainingForTraineeResponseDto()));

    TraineeTrainingRequestDto requestDto = new TraineeTrainingRequestDto();
    requestDto.setUsername(username);
    requestDto.setPeriodFrom(periodFrom);
    requestDto.setPeriodTo(periodTo);


    List<TrainingForTraineeResponseDto> result = trainingService.findTraineeTrainingsWithFilters(requestDto);


    assertNotNull(result);
    assertEquals(1, result.size());
    verify(trainingRepository, times(1)).findTraineeTrainingsByUsername(username);
    verify(trainingForTraineeMapper, times(1)).toDtoList(argThat(trainings ->
        trainings.size() == 1 && trainings.get(0).getTrainingDate().equals(LocalDate.of(2024, 1, 15))
    ));
  }

  @Test
  void testFindTraineeTrainingsWithFilters_trainerNameFilter() {

    String username = "john_doe";
    String trainerName = "John Trainer";


    Training trainingWithCorrectTrainer = new Training();
    trainingWithCorrectTrainer.setTrainer(createMockTrainer("John", "Trainer"));
    trainingWithCorrectTrainer.setTrainingDate(LocalDate.of(2024, 1, 15));

    Training trainingWithWrongTrainer = new Training();
    trainingWithWrongTrainer.setTrainer(createMockTrainer("Jane", "Doe"));
    trainingWithWrongTrainer.setTrainingDate(LocalDate.of(2024, 1, 16));

    List<Training> mockTrainings = List.of(trainingWithCorrectTrainer, trainingWithWrongTrainer);

    when(trainingRepository.findTraineeTrainingsByUsername(username)).thenReturn(mockTrainings);

    when(trainingForTraineeMapper.toDtoList(anyList()))
        .thenReturn(List.of(new TrainingForTraineeResponseDto()));

    TraineeTrainingRequestDto requestDto = new TraineeTrainingRequestDto();
    requestDto.setUsername(username);
    requestDto.setTrainerName(trainerName);


    List<TrainingForTraineeResponseDto> result = trainingService.findTraineeTrainingsWithFilters(requestDto);


    assertNotNull(result);
    assertEquals(1, result.size());
    verify(trainingRepository, times(1)).findTraineeTrainingsByUsername(username);
    verify(trainingForTraineeMapper, times(1)).toDtoList(argThat(trainings ->
        trainings.size() == 1 &&
            trainings.get(0).getTrainer().getUser().getFirstName().equals("John") &&
            trainings.get(0).getTrainer().getUser().getLastName().equals("Trainer")
    ));
  }
}