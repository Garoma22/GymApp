package com.example.gymApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeWithTrainerListDto;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.dto.trainer.TrainerResponseDto;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.TrainingRepository;
import com.example.gymApp.repository.UserRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

  @Mock
  private TraineeRepository traineeRepository;

  @Mock
  private TrainerRepository trainerRepository;


  @Mock
  private TrainingRepository trainingRepository;

  @Mock
  private UserRepository userRepository;


  @Mock
  private TrainerService trainerService;

  @Mock
  private TrainingService trainingService;

  @Mock
  private TrainerMapper trainerMapper;

  @InjectMocks
  private TraineeService traineeService;

  @Test
  void createTrainee_shouldThrowExceptionWhenUsernameExists() {

    String username = "existingUser";

    //if we already have user with such username in db
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

    //we throw an exception
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      traineeService.createTrainee("John", "Doe", username, "password123", LocalDate.now(),
          "Address");
    });

    assertEquals("Username already exists.", exception.getMessage());

    verify(traineeRepository, never()).save(any(Trainee.class));
  }

  @Test
  void createTrainee_shouldCreateTraineeWhenUsernameDoesNotExist() {
    String username = "newUser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    //joint together trainee and user
    Trainee trainee = new Trainee();
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setUsername(username); // set username is important here!
    trainee.setUser(user);  //set user to trainee

    when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

    Trainee result = traineeService.createTrainee("John", "Doe", "newUser", "password123",
        LocalDate.now(), "Address");

    assertNotNull(result);
    assertEquals("John", result.getUser().getFirstName());
    assertEquals("Doe", result.getUser().getLastName());
    assertEquals(username, result.getUser().getUsername());

    // Verify that trainee was saved
    verify(traineeRepository).save(any(Trainee.class));
    verify(userRepository, never()).save(any(User.class)); // Ensures user was not saved separately!
  }


  @Test
  void getTraineeById_shouldReturnTraineeWhenExists() {

    Long traineeId = 1L;
    Trainee trainee = new Trainee();
    trainee.setId(traineeId);

    // Mocking repository to return the trainee
    when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));

    // When
    Trainee result = traineeService.getTraineeById(traineeId);

    // Then
    assertNotNull(result);
    assertEquals(traineeId, result.getId());

    // Verify that repository's findById was called
    verify(traineeRepository).findById(traineeId);
  }

  @Test
  void getTraineeById_shouldThrowExceptionWhenNotExists() {
    // Given
    Long traineeId = 1L;

    // Mocking repository to return empty (trainee not found)
    when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());

    // traineeService.getTraineeById(traineeId) will trow an exception if trainee with such id will not be found
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> traineeService.getTraineeById(traineeId));

    assertEquals("Trainee not found with id: " + traineeId, exception.getMessage());

    // Verify that repository's findById was called
    verify(traineeRepository).findById(traineeId);
  }


  @Test
  void getTraineeByUsername_shouldReturnTraineeWhenFound() {

    String username = "john_doe";
    Trainee trainee = new Trainee();
    User user = new User();
    user.setUsername(username);
    trainee.setUser(user);

    // Stub traineeRepository to return the trainee when the username is found
    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));

    // When
    Trainee result = traineeService.getTraineeByUsername(username);

    // Then
    assertNotNull(result);
    assertEquals(username, result.getUser().getUsername());

    // Verify that repository method was called
    verify(traineeRepository).findByUserUsername(username);
  }

  @Test
  void getTraineeByUsername_shouldThrowExceptionWhenNotFound() {
    // Given
    String username = "unknown_user";

    // Stub traineeRepository to return an empty Optional when username is not found
    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.empty());

    // When and Then
    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () -> traineeService.getTraineeByUsername(username));

    assertEquals("No trainee found with the username: " + username, exception.getMessage());

    // Verify that repository method was called
    verify(traineeRepository).findByUserUsername(username);
  }


  @Test
  void updateTrainee_shouldUpdateTraineeFieldsCorrectly() {

    Trainee trainee = new Trainee();
    User user = new User();
    trainee.setUser(user);

    String newName = "UpdatedName";
    String newLastName = "UpdatedLastName";
    String newUsername = "updatedUsername";
    String newPassword = "updatedPassword123";
    boolean activeStatus = true;
    String address = "Updated Address";
    LocalDate dateOfBirth = LocalDate.of(1995, 5, 15);

    // When
    Trainee updatedTrainee = traineeService.updateTrainee(
        trainee,
        newName,
        newLastName,
        newUsername,
        newPassword,
        activeStatus,
        address,
        dateOfBirth
    );

    // Then
    assertEquals(newName, updatedTrainee.getUser().getFirstName());
    assertEquals(newLastName, updatedTrainee.getUser().getLastName());
    assertEquals(newUsername, updatedTrainee.getUser().getUsername());
    assertEquals(newPassword, updatedTrainee.getUser().getPassword());
    assertEquals(activeStatus, updatedTrainee.getUser().isActive());
    assertEquals(address, updatedTrainee.getAddress());
    assertEquals(dateOfBirth, updatedTrainee.getDateOfBirth());

    // Verify that traineeRepository.save() was called
    verify(traineeRepository).save(trainee);
  }

  @Test
  void deleteTraineeByUsername_shouldDeleteTraineeAndRelatedEntities() {

    //mock Trainee
    String traineeUsername = "traineeUsername";
    User traineeUser = new User();
    traineeUser.setUsername(traineeUsername);
    Trainee trainee = new Trainee();
    trainee.setUser(traineeUser);

    //mock Trainer
    String trainerUsername = "trainerUsername";
    User trainerUser = new User();
    trainerUser.setUsername(trainerUsername);
    Trainer trainer = new Trainer();
    trainer.setUser(trainerUser);

    //mock training1
    Training training1 = new Training();
    training1.setId(1L);  // Ensure you set some values
    training1.setTrainingName("Training 1");
    training1.setTrainee(trainee);
    training1.setTrainer(trainer);

//
    //mock training2
    Training training2 = new Training();
    training2.setId(2L);  // Ensure you set some values
    training2.setTrainingName("Training 2");
    training2.setTrainee(trainee);
    training2.setTrainer(trainer);

    // Mock repository calls for trainee
    when(userRepository.findByUsername(traineeUser.getUsername())).thenReturn(
        Optional.of(traineeUser));
    when(traineeRepository.findByUser(traineeUser)).thenReturn(Optional.of(trainee));
    when(trainingRepository.findByTrainee(trainee)).thenReturn(Arrays.asList(training1, training2));
    traineeService.deleteTraineeByUsername(traineeUsername);

    // Then
    // Verify that both trainings were deleted
    verify(trainingRepository, times(1)).delete(training1);
    verify(trainingRepository, times(1)).delete(training2);

    // Verify that the trainee was deleted
    verify(traineeRepository, times(1)).delete(trainee);

    // Verify that the trainer WAS NOT deleted!
    verify(trainerRepository, times(0)).delete(trainer);

    // Verify the total number of delete invocations on trainingRepository
    verify(trainingRepository, times(2)).delete(any(Training.class));
  }


  @Test
  void deleteTraineeByUsername_shouldThrowExceptionIfUserNotFound() {
    // Given
    String username = "nonexistentUsername";

    // Mocking the repository call to return empty when user is not found
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    // When & Then
    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      traineeService.deleteTraineeByUsername(username);
    });

    assertEquals("No user found with the provided username", exception.getMessage());

    // Verify that the trainee and trainings are never touched
    verify(traineeRepository, never()).delete(any());
    verify(trainingRepository, never()).delete(any());
  }

  @Test
  void deleteTraineeByUsername_shouldThrowExceptionIfTraineeNotFound() {

    String username = "traineeUsername";
    User user = new User();
    user.setUsername(username);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    when(traineeRepository.findByUser(user)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      traineeService.deleteTraineeByUsername(username);
    });

    assertEquals("No trainee found for the provided user", exception.getMessage());
    verify(traineeRepository, never()).delete(any());
    verify(trainingRepository, never()).delete(any());
  }


  @Test
  void getTraineeProfileWithTrainersList_shouldReturnTraineeProfileWithTrainersList() {

    String username = "traineeUsername";

    User user = new User();
    user.setUsername(username);
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setActive(true);

    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setDateOfBirth(LocalDate.of(1990, 5, 15));
    trainee.setAddress("123 Main St");

    TrainerDto trainerDto1 = new TrainerDto("trainer1", "Trainer", "One");
    TrainerDto trainerDto2 = new TrainerDto("trainer2", "Trainer", "Two");
    List<TrainerDto> trainersList = Arrays.asList(trainerDto1, trainerDto2);

    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
    when(trainerService.getAllTrainersDtoByTrainee(username)).thenReturn(trainersList);

    TraineeWithTrainerListDto result = traineeService.getTraineeProfileWithTrainersList(username);

    assertNotNull(result);
    assertEquals(username, result.getUsername());
    assertEquals("John", result.getFirstName());
    assertEquals("Doe", result.getLastName());
    assertEquals(LocalDate.of(1990, 5, 15), result.getDateOfBirth());
    assertEquals("123 Main St", result.getAddress());
    assertTrue(result.isActive());
    assertEquals(2, result.getTrainers().size());
    assertEquals(trainerDto1, result.getTrainers().get(0));
    assertEquals(trainerDto2, result.getTrainers().get(1));

    verify(traineeRepository, times(1)).findByUserUsername(username);
    verify(trainerService, times(1)).getAllTrainersDtoByTrainee(username);
  }


  @Test
  void updateTraineeProfile_shouldUpdateProfileAndReturnTraineeWithTrainers() {

    String username = "traineeUsername";

    User existingUser = new User();
    existingUser.setUsername(username);
    existingUser.setFirstName("OldFirstName");
    existingUser.setLastName("OldLastName");
    existingUser.setActive(false);

    Trainee existingTrainee = new Trainee();
    existingTrainee.setUser(existingUser);
    existingTrainee.setDateOfBirth(LocalDate.of(1990, 5, 15));
    existingTrainee.setAddress("Old Address");

    TraineeDto traineeDto = new TraineeDto();
    traineeDto.setFirstName("NewFirstName");
    traineeDto.setLastName("NewLastName");
    traineeDto.setActive(true);
    traineeDto.setDateOfBirth("1995-03-20");
    traineeDto.setAddress("New Address");

    TrainerDto trainerDto1 = new TrainerDto("trainer1", "TrainerFirstName1", "TrainerLastName1");
    TrainerDto trainerDto2 = new TrainerDto("trainer2", "TrainerFirstName2", "TrainerLastName2");
    List<TrainerDto> trainersList = Arrays.asList(trainerDto1, trainerDto2);

    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(existingTrainee));
    when(trainerService.getAllTrainersDtoByTrainee(username)).thenReturn(trainersList);

    // When
    TraineeWithTrainerListDto result = traineeService.updateTraineeProfile(traineeDto, username);

    // Then
    assertEquals("NewFirstName", existingTrainee.getUser().getFirstName());
    assertEquals("NewLastName", existingTrainee.getUser().getLastName());
    assertTrue(existingTrainee.getUser().isActive());
    assertEquals(LocalDate.of(1995, 3, 20), existingTrainee.getDateOfBirth());
    assertEquals("New Address", existingTrainee.getAddress());

    //trainersList
    assertNotNull(result);
    assertEquals(username, result.getUsername());
    assertEquals("NewFirstName", result.getFirstName());
    assertEquals("NewLastName", result.getLastName());
    assertEquals(LocalDate.of(1995, 3, 20), result.getDateOfBirth());
    assertEquals("New Address", result.getAddress());
    assertTrue(result.isActive());
    assertEquals(2, result.getTrainers().size());
    assertEquals(trainerDto1, result.getTrainers().get(0));
    assertEquals(trainerDto2, result.getTrainers().get(1));

    verify(traineeRepository, times(1)).save(existingTrainee);
    verify(trainerService, times(1)).getAllTrainersDtoByTrainee(username);
  }


  @Test
  void updateTraineeProfile_shouldUpdateProfileAndReturnUpdatedTraineeWithTrainers() {

    String username = "traineeUsername";

    TraineeDto traineeDto = new TraineeDto();
    traineeDto.setFirstName("NewFirstName");
    traineeDto.setLastName("NewLastName");
    traineeDto.setActive(true);
    traineeDto.setDateOfBirth("1995-03-20");
    traineeDto.setAddress("New Address");

    User user = new User();
    user.setUsername(username);
    user.setFirstName("OldFirstName");
    user.setLastName("OldLastName");
    user.setActive(false);

    Trainee trainee = new Trainee();
    trainee.setUser(user);
    trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
    trainee.setAddress("Old Address");

    TrainerDto trainerDto1 = new TrainerDto("Iavan.Petrov", "Ivan", "Petrov", "cardio");
    TrainerDto trainerDto2 = new TrainerDto("Maria.Sidorova", "Maria", "Sidorova", "yoga");

    List<TrainerDto> trainersList = Arrays.asList(trainerDto1, trainerDto2);

    // Mock repository and service calls
    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
    when(trainerService.getAllTrainersDtoByTrainee(username)).thenReturn(trainersList);
    when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

    // When
    TraineeWithTrainerListDto result = traineeService.updateTraineeProfile(traineeDto, username);

    // Then
    assertNotNull(result);
    assertEquals(username, result.getUsername());
    assertEquals("NewFirstName", result.getFirstName());
    assertEquals("NewLastName", result.getLastName());
    assertEquals(LocalDate.of(1995, 3, 20), result.getDateOfBirth());
    assertEquals("New Address", result.getAddress());
    assertTrue(result.isActive());

    assertEquals(2, result.getTrainers().size());

    TrainerDto resultTrainer1 = result.getTrainers().get(0);
    assertEquals("Iavan.Petrov", resultTrainer1.getUsername());
    assertEquals("Ivan", resultTrainer1.getFirstName());
    assertEquals("Petrov", resultTrainer1.getLastName());
    assertEquals("cardio", resultTrainer1.getSpecialization());

    TrainerDto resultTrainer2 = result.getTrainers().get(1);
    assertEquals("Maria.Sidorova", resultTrainer2.getUsername());
    assertEquals("Maria", resultTrainer2.getFirstName());
    assertEquals("Sidorova", resultTrainer2.getLastName());
    assertEquals("yoga", resultTrainer2.getSpecialization());

    verify(traineeRepository, times(1)).save(trainee);
  }


  @Test
  void updateTraineeTrainers_shouldUpdateTrainersAndReturnResponseDtos() {

    // Mock trainee and user details
    User userTrainee = new User();
    userTrainee.setUsername("traineeUsername");

    Trainee trainee = new Trainee();
    trainee.setUser(userTrainee);

    // Mock trainers with specializations + helper helps us to add mock TrainingType specialization
    Trainer trainer1 = createMockTrainer("trainerUsername1", "Cardio");
    Trainer trainer2 = createMockTrainer("trainerUsername2", "Strength");

    List<Trainer> foundTrainers = Arrays.asList(trainer1, trainer2);
    List<String> newTrainersUsernames = Arrays.asList("trainerUsername1", "trainerUsername2");

    // Mock DTOs for trainers
    TrainerResponseDto trainerResponseDto1 = new TrainerResponseDto();
    trainerResponseDto1.setUsername("trainerUsername1");

    TrainerResponseDto trainerResponseDto2 = new TrainerResponseDto();
    trainerResponseDto2.setUsername("trainerUsername2");

    List<TrainerResponseDto> trainerResponseDtos = Arrays.asList(trainerResponseDto1, trainerResponseDto2);

    // Mock behavior
    when(trainerService.findByUsernameIn(newTrainersUsernames)).thenReturn(foundTrainers);
    when(traineeRepository.findByUserUsername(trainee.getUser().getUsername())).thenReturn(Optional.of(trainee));
    doNothing().when(trainingService).createTraining(foundTrainers, trainee);
    when(trainingService.getAllTrainersByTraineeUsername(trainee.getUser().getUsername())).thenReturn(foundTrainers);
    when(trainerMapper.toTrainerResponseDto(foundTrainers)).thenReturn(trainerResponseDtos);

    // When
    List<TrainerResponseDto> result = traineeService.updateTraineeTrainers(trainee.getUser().getUsername(), newTrainersUsernames);

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("trainerUsername1", result.get(0).getUsername());
    assertEquals("trainerUsername2", result.get(1).getUsername());

    // Verify interactions
    verify(trainerService, times(1)).findByUsernameIn(newTrainersUsernames);
    verify(trainingService, times(1)).createTraining(foundTrainers, trainee);
    verify(trainingService, times(1)).getAllTrainersByTraineeUsername(trainee.getUser().getUsername());
    verify(trainerMapper, times(1)).toTrainerResponseDto(foundTrainers);
  }

  // Helper to create a mock trainer with specialization
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









