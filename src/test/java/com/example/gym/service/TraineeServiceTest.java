package com.example.gym.service;

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

import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainee.TraineeWithTrainerListDto;
import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.dto.trainer.TrainerMapper;
import com.example.gym.dto.trainer.TrainerResponseDto;
import com.example.gym.model.Trainee;
import com.example.gym.model.Trainer;
import com.example.gym.model.Training;
import com.example.gym.model.TrainingType;
import com.example.gym.model.User;
import com.example.gym.repository.TraineeRepository;
import com.example.gym.repository.TrainerRepository;
import com.example.gym.repository.TrainingRepository;
import com.example.gym.repository.UserRepository;
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
    Long traineeId = 1L;
    when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());

       IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> traineeService.getTraineeById(traineeId));
    assertEquals("Trainee not found with id: " + traineeId, exception.getMessage());
    verify(traineeRepository).findById(traineeId);
  }


  @Test
  void getTraineeByUsername_shouldReturnTraineeWhenFound() {

    String username = "john_doe";
    Trainee trainee = new Trainee();
    User user = new User();
    user.setUsername(username);
    trainee.setUser(user);

    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));


    Trainee result = traineeService.getTraineeByUsername(username);

    assertNotNull(result);
    assertEquals(username, result.getUser().getUsername());

    verify(traineeRepository).findByUserUsername(username);
  }

  @Test
  void getTraineeByUsername_shouldThrowExceptionWhenNotFound() {

    String username = "unknown_user";

    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.empty());

    // When and Then
    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () -> traineeService.getTraineeByUsername(username));

    assertEquals("No trainee found with the username: " + username, exception.getMessage());

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

    verify(traineeRepository).save(trainee);
  }

  @Test
  void deleteTraineeByUsername_shouldDeleteTraineeAndRelatedEntities() {

    Trainer trainer = createMockTrainer("trainerUsername", "cardio");
    Trainee trainee = createMockTrainee("traineeUsername", "someAddress");

    //mock training1
    Training training1 = new Training();
    training1.setId(1L);  // Ensure you set some values
    training1.setTrainingName("Training 1");
    training1.setTrainee(trainee);
    training1.setTrainer(trainer);

    //mock training2
    Training training2 = new Training();
    training2.setId(2L);  // Ensure you set some values
    training2.setTrainingName("Training 2");
    training2.setTrainee(trainee);
    training2.setTrainer(trainer);

    // Mock repository calls for trainee
    when(userRepository.findByUsername(trainee.getUser().getUsername())).thenReturn(
        Optional.of(trainee.getUser()));
    when(traineeRepository.findByUser(trainee.getUser())).thenReturn(Optional.of(trainee));
    when(trainingRepository.findByTrainee(trainee)).thenReturn(List.of(training1, training2));
    traineeService.deleteTraineeByUsername(trainee.getUsername());

    // Then
    verify(trainingRepository, times(1)).delete(training1);
    verify(trainingRepository, times(1)).delete(training2);


    verify(traineeRepository, times(1)).delete(trainee);

    verify(trainerRepository, times(0)).delete(trainer);

    verify(trainingRepository, times(2)).delete(any(Training.class));
  }


  @Test
  void deleteTraineeByUsername_shouldThrowExceptionIfUserNotFound() {

    String username = "nonexistentUsername";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      traineeService.deleteTraineeByUsername(username);
    });
    assertEquals("No user found with the provided username", exception.getMessage());

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
    when(traineeService.getAllTrainersDtoByTrainee(username)).thenReturn(trainersList);

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
    when(traineeService.getAllTrainersDtoByTrainee(username)).thenReturn(trainersList);

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

    List<TrainerDto> trainersList = List.of(trainerDto1, trainerDto2);

    // Mock repository and service calls
    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
    when(traineeService.getAllTrainersDtoByTrainee(username)).thenReturn(trainersList);
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

  @Test
  void testGetAllTrainees_shouldReturnAllTrainees() {

    List<Trainee> expectedTrainees = getTrainees();

    when(traineeRepository.findAll()).thenReturn(expectedTrainees);

    List<Trainee> actualTrainees = traineeService.getAllTrainees();


    assertNotNull(actualTrainees);
    assertEquals(2, actualTrainees.size());
    assertEquals("userFirstName1", actualTrainees.get(0).getUser().getFirstName());
    assertEquals("userFirstName2", actualTrainees.get(1).getUser().getFirstName());


    verify(traineeRepository, times(1)).findAll();
  }

  private static List<Trainee> getTrainees() {
    User user1 = new User();
    user1.setFirstName("userFirstName1");
    user1.setLastName("userLastName1");
    user1.setUsername("username1");

    Trainee trainee1 = new Trainee();
    trainee1.setId(1L);
    trainee1.setUser(user1);

    User user2 = new User();
    user2.setFirstName("userFirstName2");
    user2.setLastName("userLastName2");
    user2.setUsername("username2");

    Trainee trainee2 = new Trainee();
    trainee2.setId(2L);
    trainee2.setUser(user2);

    List<Trainee> expectedTrainees = Arrays.asList(trainee1, trainee2);
    return expectedTrainees;
  }

  @Test
  void testGetAllTrainees_whenNoTrainees_shouldReturnEmptyList() {

    when(traineeRepository.findAll()).thenReturn(List.of());

    List<Trainee> actualTrainees = traineeService.getAllTrainees();

    assertNotNull(actualTrainees);
    assertTrue(actualTrainees.isEmpty());

    verify(traineeRepository, times(1)).findAll();
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

}









