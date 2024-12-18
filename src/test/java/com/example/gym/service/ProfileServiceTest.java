package com.example.gym.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainee.TraineeMapper;
import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.dto.trainer.TrainerMapper;
import com.example.gym.model.Trainee;
import com.example.gym.model.Trainer;
import com.example.gym.model.TrainingType;
import com.example.gym.model.User;
import com.example.gym.repository.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

  @InjectMocks
  private ProfileService profileService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TraineeMapper traineeMapper;

  @Mock
  private TraineeService traineeService;

  @Mock
  private TrainerMapper trainerMapper;

  @Mock
  private TrainerService trainerService;

  private User createUser(String firstName, String lastName) {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    return user;
  }

  private Trainee createTrainee(String firstName, String lastName) {
    Trainee trainee = new Trainee();
    trainee.setUser(createUser(firstName, lastName));
    return trainee;
  }

  private Trainer createTrainer(String firstName, String lastName, TrainingType specialization) {
    Trainer trainer = new Trainer();
    trainer.setUser(createUser(firstName, lastName));
    trainer.setSpecialization(specialization);
    return trainer;
  }

  private void mockUserRepositoryForUsernameGeneration(String baseUsername, List<String> existingUsernames) {
    when(userRepository.findAllByUsernameStartingWith(baseUsername + "%"))
        .thenReturn(existingUsernames);
  }

  @Test
  void generateUsername_noExistingUsernames_returnBaseUsername() {

    String firstName = "Ivan";
    String lastName = "Petrov";
    String baseUsername = firstName + "." + lastName;

    mockUserRepositoryForUsernameGeneration(baseUsername, Collections.emptyList());

    String generatedUsername = profileService.generateUsername(firstName, lastName);

    assertEquals(baseUsername, generatedUsername);
  }

  @Test
  void generateUsername_existingUsernames_returnsUsernameWithSuffix() {

    String firstName = "John";
    String lastName = "Doe";
    String baseUsername = firstName + "." + lastName;

    List<String> existingUsernames = Arrays.asList("John.Doe", "John.Doe1", "John.Doe2");
    mockUserRepositoryForUsernameGeneration(baseUsername, existingUsernames);

    String generatedUsername = profileService.generateUsername(firstName, lastName);

    assertEquals(baseUsername + "3", generatedUsername);
  }

  @Test
  void registerTrainee_shouldReturnUsernameAndPassword() {

    TraineeDto traineeDto = new TraineeDto();
    traineeDto.setFirstName("John");
    traineeDto.setLastName("Doe");

    Trainee trainee = createTrainee("John", "Doe");
    String expectedUsername = "John.Doe";

    mockUserRepositoryForUsernameGeneration(expectedUsername, Collections.emptyList());
    when(traineeMapper.toTrainee(traineeDto)).thenReturn(trainee);
    when(traineeService.createTrainee(any(), any(), any(), any(), any(), any()))
        .thenReturn(trainee);

    Map<String, String> response = profileService.registerTrainee(traineeDto);

    assertEquals(expectedUsername, response.get("username"));
    assertNotNull(response.get("password"));
    verify(traineeService).createTrainee(eq("John"), eq("Doe"), eq(expectedUsername), anyString(), any(), any());
  }

  @Test
  void registerTrainer_shouldReturnUsernameAndPassword() {

    TrainerDto trainerDto = new TrainerDto();
    trainerDto.setFirstName("John");
    trainerDto.setLastName("Doe");
    trainerDto.setSpecialization("cardio");

    TrainingType trainingType = new TrainingType("cardio");
    trainerDto.setTrainingType(trainingType);
    trainerDto.setActive(true);

    Trainer trainer = createTrainer("John", "Doe", trainingType);
    String expectedUsername = "John.Doe";

    mockUserRepositoryForUsernameGeneration(expectedUsername, Collections.emptyList());
    when(trainerMapper.toTrainer(trainerDto)).thenReturn(trainer);
    when(trainerService.createTrainer(any(), any(), any(), any(), any())).thenReturn(trainer);
    when(trainerService.checkSpecializationCorrectness(anyString())).thenReturn(trainingType);

    Map<String, String> response = profileService.registerTrainer(trainerDto);

    assertEquals(expectedUsername, response.get("username"));
    assertNotNull(response.get("password"));
    verify(trainerService).createTrainer(eq("John"), eq("Doe"), eq(expectedUsername), anyString(), eq(trainingType.getName()));
  }
}



