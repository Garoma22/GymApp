package com.example.gym.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainee.TraineeMapper;
import com.example.gym.dto.trainer.TrainerMapper;

import com.example.gym.dto.trainer.TrainerResponseDto;
import com.example.gym.dto.trainer.TrainerWithTraineeListDto;
import com.example.gym.model.*;
import com.example.gym.repository.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

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
  private TraineeService traineeService;


@Mock
  private TrainerService trainerService;


  @BeforeEach
  void setUp() {
    trainerService = new TrainerService(trainerRepository, userRepository, trainingTypeRepository,
        traineeRepository, trainingRepository, traineeMapper,
        trainerMapper);
  }


  @Test
  void testSaveTrainer() {

    Trainer trainer = createMockTrainer("Roman.Galkin", "cardio");

    when(trainerRepository.saveAndFlush(any(Trainer.class))).thenReturn(trainer);

    Trainer result = trainerService.saveTrainer(trainer);

    assertNotNull(result);
    assertEquals(trainer.getId(), result.getId());
    assertEquals("Roman.Galkin", result.getUser().getUsername());

  }

  @Test
  void testUpdateTrainer() {
    Trainer trainer = createMockTrainer("Roman.Galkin", "cardio");

    TrainingType trainingType1 = new TrainingType();
    trainingType1.setName("yoga");

    when(trainingTypeRepository.findByName(anyString())).thenReturn(
        Optional.of(trainingType1));


    Trainer updatedTrainer = createMockTrainer("Ivan.Petrov", "yoga");
    updatedTrainer.getUser().setFirstName("newName");
    updatedTrainer.getUser().setLastName("newLastName");
    updatedTrainer.getUser().setPassword("newPass");
    updatedTrainer.getUser().setActive(true);

    when(trainerRepository.save(updatedTrainer)).thenReturn(updatedTrainer);

    Trainer result = trainerService.updateTrainer(trainer, updatedTrainer.getUser().getFirstName(),
        updatedTrainer.getUser().getLastName(),
        updatedTrainer.getUser().getUsername(), updatedTrainer.getUser().getPassword(),
        updatedTrainer.getUser().isActive(),
        updatedTrainer.getSpecialization().getName());

    assertNotNull(result);
    assertEquals("newName", result.getUser().getFirstName());
    assertEquals("newLastName", result.getUser().getLastName());
    assertEquals("Ivan.Petrov", result.getUser().getUsername());
    assertEquals("newPass", result.getUser().getPassword());
    assertTrue(result.getUser().isActive());
    assertEquals("yoga", result.getSpecialization().getName());

  }

  @Test
  void testCreateTrainer_success() {

    String firstName = "John";
    String lastName = "Doe";
    String username = "john.doe";
    String password = "password123";
    String specialization = "yoga";

    TrainingType trainingType = new TrainingType();
    trainingType.setName(specialization);



    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());


    when(trainingTypeRepository.findByName(specialization)).thenReturn(Optional.of(trainingType));


    Trainer result = trainerService.createTrainer(firstName, lastName, username, password, specialization);


    assertNotNull(result);
    assertEquals(firstName, result.getUser().getFirstName());
    assertEquals(lastName, result.getUser().getLastName());
    assertEquals(username, result.getUser().getUsername());
    assertEquals(password, result.getUser().getPassword());
    assertEquals(trainingType, result.getSpecialization());
    assertTrue(result.getUser().isActive());


    verify(trainerRepository, times(1)).save(any(Trainer.class));

  }

  @Test
  void testCreateTrainer_usernameAlreadyExists() {
    String username = "john.doe";
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      trainerService.createTrainer("John", "Doe", username, "password123", "yoga");
    });
    assertEquals("Username of trainer already exists", exception.getMessage());
    verify(trainerRepository, never()).save(any(Trainer.class));
  }

  @Test
  void testCreateTrainer_specializationNotFound() {
    String username = "john.doe";
    String specialization = "unknown";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    when(trainingTypeRepository.findByName(specialization)).thenReturn(Optional.empty());
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      trainerService.createTrainer("John", "Doe", username, "password123", specialization);
    });

    assertEquals("Specialization not found in the database", exception.getMessage());
    verify(trainerRepository, never()).save(any(Trainer.class));
  }

  @Test
  void testGetTrainerByUsername_success() {
    Trainer trainer = createMockTrainer("Roman.Galkin", "cardio");
    when(trainerRepository.findByUserUsername(trainer.getUser().getUsername())).thenReturn(Optional.of(trainer));
    Trainer result = trainerService.getTrainerByUsername(trainer.getUser().getUsername());
    assertNotNull(result);
    assertEquals("Roman.Galkin", result.getUser().getUsername());
    verify(trainerRepository, times(1)).findByUserUsername(result.getUser().getUsername());
  }

  @Test
  void testGetTrainerByUsername_trainerNotFound() {
    String username = "nonexistentTrainer";
    when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.empty());
    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      trainerService.getTrainerByUsername(username);
    });

    assertEquals("No trainer found with the username: " + username, exception.getMessage());
    verify(trainerRepository, times(1)).findByUserUsername(username);
  }


  @Test
  void testGetAllTrainersNotAssignedToTrainee_success() {

    String traineeUsername = "trainee1";
    Trainer trainer1 = createMockTrainer("Roman.Galkin", "cardio");
    Trainer trainer2 = createMockTrainer("Ivan.Petrov", "yoga");
    List<Trainer> trainers = List.of(trainer1, trainer2);

    when(trainerRepository.getAllTrainersNotAssignedToTrainee(traineeUsername)).thenReturn(trainers);
    List<Trainer> result = trainerService.getAllTrainersNotAssignedToTrainee(traineeUsername);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Roman.Galkin", result.get(0).getUser().getUsername());
    assertEquals("Ivan.Petrov", result.get(1).getUser().getUsername());

    verify(trainerRepository, times(1)).getAllTrainersNotAssignedToTrainee(traineeUsername);
  }

  @Test
  void testGetAllTrainersNotAssignedToTrainee_emptyList() {
    String traineeUsername = "trainee1";
    when(trainerRepository.getAllTrainersNotAssignedToTrainee(traineeUsername)).thenReturn(Collections.emptyList());

    List<Trainer> result = trainerService.getAllTrainersNotAssignedToTrainee(traineeUsername);

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(trainerRepository, times(1)).getAllTrainersNotAssignedToTrainee(traineeUsername);
  }


  @Test
  void testGetAllActiveTrainersNotAssignedToTrainee(){
    Trainee trainee = createMockTrainee("Roman.Galkin", "Street Rustaveli");
    when(traineeRepository.findByUserUsername(trainee.getUser().getUsername())).thenReturn(Optional.of(trainee));
    Trainer trainer1 = createMockTrainer("Ivan.Petrov", "cardio" );
    Trainer trainer2 = createMockTrainer("Nikolay.Svetlov", "yoga" );


    var trainers = List.of(trainer1,trainer2);

    TrainerResponseDto trainerResponseDto1  = createTrainerResponseDto(trainer1);
    TrainerResponseDto trainerResponseDto2  = createTrainerResponseDto(trainer2);

    List<TrainerResponseDto> trainerResponseDtos = List.of(trainerResponseDto1, trainerResponseDto2);

    when(trainingRepository.findAllActiveTrainersNotAssignedToTrainee(trainee.getUsername())).thenReturn(trainers);
    when(trainerMapper.toTrainerResponseDto(trainers)).thenReturn(trainerResponseDtos);


    List<TrainerResponseDto> result = trainerService.getAllActiveTrainersNotAssignedToTrainee(trainee.getUser().getUsername());

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Ivan.Petrov", result.get(0).getUsername());
    assertEquals("Nikolay.Svetlov", result.get(1).getUsername());

    verify(traineeRepository, times(1)).findByUserUsername(trainee.getUser().getUsername());
    verify(trainingRepository, times(1)).findAllActiveTrainersNotAssignedToTrainee(trainee.getUsername());
    verify(trainerMapper, times(1)).toTrainerResponseDto(trainers);
  }



  @Test
  void testfindByUsernameIn() {

    List<String> newTrainersUserNames = List.of("Ivan.Ivanov", "Stepan.Stepanov", "Ilia.Iliev");

    Trainer trainer1 = createMockTrainer("Ivan.Ivanov", "cardio");
    Trainer trainer2 = createMockTrainer("Stepan.Stepanov", "yoga");
    Trainer trainer3 = createMockTrainer("Ilia.Iliev", "cardio");

    List<Trainer> trainers = List.of(trainer1, trainer2, trainer3);

    when(trainerRepository.findByUserUsernameIn(newTrainersUserNames)).thenReturn(trainers);
    List<Trainer> result = trainerService.findByUsernameIn(newTrainersUserNames);

    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("Ivan.Ivanov", result.get(0).getUser().getUsername());
    assertEquals("Stepan.Stepanov", result.get(1).getUser().getUsername());
    assertEquals("Ilia.Iliev", result.get(2).getUser().getUsername());

    verify(trainerRepository, times(1)).findByUserUsernameIn(newTrainersUserNames);
  }


  @Test
  void testGetTrainerWithTrainees_success() {
    String username = "trainerUsername";
    Trainer trainer = createMockTrainer(username, "cardio");
    Trainee trainee1 = createMockTrainee("traineeUsername1", "someAddress");
    Trainee trainee2 = createMockTrainee("traineeUsername2", "someAddress");

    List<TraineeDto> traineeDtoList = getTraineeDtoList(trainee1, trainee2);

    List<TrainerWithTraineeListDto.TraineeDto> simpleTraineeDtoList = traineeDtoList.stream()
        .map(this::toSimpleTraineeDto)
        .toList();

    TrainerWithTraineeListDto result = new TrainerWithTraineeListDto();
    result.setUsername(trainer.getUser().getUsername());
    result.setFirstName(trainer.getUser().getFirstName());
    result.setTrainingType(trainer.getSpecialization());
    result.setActive(trainer.getUser().isActive());
    result.setTraineeDtoList(simpleTraineeDtoList);

    assertNotNull(result);
    assertEquals(trainer.getUser().getUsername(), result.getUsername());
    assertEquals(trainer.getUser().getFirstName(), result.getFirstName());
    assertEquals(trainer.getSpecialization(), result.getTrainingType());
    assertEquals(trainer.getUser().isActive(), result.isActive());
    assertEquals(2, result.getTraineeDtoList().size());
    assertEquals("traineeUsername1", result.getTraineeDtoList().get(0).getUsername());
    assertEquals("traineeUsername2", result.getTraineeDtoList().get(1).getUsername());

  }


  private static List<TraineeDto> getTraineeDtoList(Trainee trainee1, Trainee trainee2) {
    TraineeDto traineeDto1 = new TraineeDto();
    traineeDto1.setFirstName(trainee1.getUser().getFirstName());
    traineeDto1.setLastName(trainee1.getUser().getLastName());
    traineeDto1.setDateOfBirth("2222-22-22");
    traineeDto1.setAddress(traineeDto1.getAddress());
    traineeDto1.setActive(trainee1.getUser().isActive());
    traineeDto1.setUsername(trainee1.getUsername());

    TraineeDto traineeDto2 = new TraineeDto();
    traineeDto2.setFirstName(trainee2.getUser().getFirstName());
    traineeDto2.setLastName(trainee2.getUser().getLastName());
    traineeDto2.setDateOfBirth("2222-22-22");
    traineeDto2.setAddress(traineeDto2.getAddress());
    traineeDto2.setActive(trainee2.getUser().isActive());
    traineeDto2.setUsername(trainee2.getUsername());

    List<TraineeDto> traineeDtoList = List.of(traineeDto1, traineeDto2);
    return traineeDtoList;
  }


  private TrainerWithTraineeListDto.TraineeDto toSimpleTraineeDto(TraineeDto fullTraineeDto) {
    TrainerWithTraineeListDto.TraineeDto simpleTraineeDto = new TrainerWithTraineeListDto.TraineeDto();
    simpleTraineeDto.setFirstName(fullTraineeDto.getFirstName());
    simpleTraineeDto.setLastName(fullTraineeDto.getLastName());
    simpleTraineeDto.setUsername(fullTraineeDto.getUsername());
    return simpleTraineeDto;
  }


  private TrainerResponseDto createTrainerResponseDto(Trainer trainer) {
    TrainerResponseDto dto = new TrainerResponseDto();
    dto.setUsername(trainer.getUser().getUsername());
    dto.setFirstName(trainer.getUser().getFirstName());
    dto.setLastName(trainer.getUser().getLastName());
    dto.setTrainingType(trainer.getSpecialization());
    return dto;
  }

  private Trainee createMockTrainee(String username, String address){
    User user = new User();
    user.setUsername(username);
    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setAddress(address);
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

    return trainer;
  }
}
