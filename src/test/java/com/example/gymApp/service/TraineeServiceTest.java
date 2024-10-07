//package com.example.gymApp.service;
//
//
//import com.example.gymApp.model.Trainee;
//import com.example.gymApp.model.User;
//import com.example.gymApp.repository.TraineeRepository;
//import com.example.gymApp.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.Optional;
//import java.util.Collections;
//import java.util.List;
//import java.util.NoSuchElementException;
//import org.springframework.test.annotation.DirtiesContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//class TraineeServiceTest {
//
//  @Mock
//  private TraineeRepository traineeRepository;
//
//  @Mock
//  private UserRepository userRepository;
//
//  @InjectMocks
//  private TraineeService traineeService;
//
//  @BeforeEach
//  void setUp() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Test
//  void createTrainee_ShouldCreateNewTrainee_WhenUsernameDoesNotExist() {
//
//    String firstName = "John";
//    String lastName = "Doe";
//    String username = "john.doe";
//    String password = "password";
//    LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
//    String address = "123 Street";
//
//    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//
//    Trainee savedTrainee = new Trainee();
//    savedTrainee.setUser(new User(null, firstName, lastName, username, password, true));
//    savedTrainee.setDateOfBirth(dateOfBirth);
//    savedTrainee.setAddress(address);
//
//    when(traineeRepository.save(any(Trainee.class))).thenReturn(savedTrainee);
//
//
//    Trainee createdTrainee = traineeService.createTrainee(firstName, lastName, username, password, dateOfBirth, address);
//
//    assertNotNull(createdTrainee);
//    assertEquals(firstName, createdTrainee.getUser().getFirstName());
//    assertEquals(lastName, createdTrainee.getUser().getLastName());
//    assertEquals(username, createdTrainee.getUser().getUsername());
//    assertEquals(address, createdTrainee.getAddress());
//
//    verify(traineeRepository).save(any(Trainee.class));
//  }
//
//
//  @Test
//  void createTrainee_ShouldThrowException_WhenUsernameExists() {
//
//    String username = "john.doe";
//
//    when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
//
//
//    assertThrows(IllegalArgumentException.class, () -> {
//      traineeService.createTrainee("John", "Doe", username, "password", LocalDate.of(1990, 1, 1), "123 Street");
//    });
//  }
//
//  @Test
//  void getAllTrainees_ShouldReturnListOfTrainees() {
//    Trainee trainee = new Trainee();
//    when(traineeRepository.findAll()).thenReturn(Collections.singletonList(trainee));
//
//
//    List<Trainee> trainees = traineeService.getAllTrainees();
//
//
//    assertFalse(trainees.isEmpty());
//    verify(traineeRepository).findAll();
//  }
//
//
//  @Test
//  void getAllTrainees_ShouldReturnEmptyList_WhenNoTrainees() {
//    when(traineeRepository.findAll()).thenReturn(Collections.emptyList());
//
//
//    List<Trainee> trainees = traineeService.getAllTrainees();
//
//
//    assertTrue(trainees.isEmpty(), "Expected an empty list when no trainees are found");
//    verify(traineeRepository).findAll();
//  }
//
//
//
//  @Test
//  void getTraineeById_ShouldReturnTrainee_WhenTraineeExists() {
//
//    Long traineeId = 1L;
//    Trainee trainee = new Trainee();
//    when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
//
//    Trainee foundTrainee = traineeService.getTraineeById(traineeId);
//
//    assertNotNull(foundTrainee);
//    verify(traineeRepository).findById(traineeId);
//  }
//
//  @Test
//  void getTraineeById_ShouldThrowException_WhenTraineeDoesNotExist() {
//
//    Long traineeId = 1L;
//    when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());
//
//    assertThrows(IllegalArgumentException.class, () -> {
//      traineeService.getTraineeById(traineeId);
//    });
//  }
//
//  @Test
//  void deleteTrainee_ShouldDeleteTrainee_WhenTraineeExists() {
//
//    Long traineeId = 1L;
//    Trainee trainee = new Trainee();
//    when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
//
//
//    traineeService.deleteTrainee(traineeId);
//
//
//    verify(traineeRepository).delete(trainee);
//  }
//
//  @Test
//  void getTraineeByUsername_ShouldReturnTrainee_WhenTraineeExists() {
//
//    String username = "john.doe";
//    Trainee trainee = new Trainee();
//    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
//
//    Trainee foundTrainee = traineeService.getTraineeByUsername(username);
//
//    assertNotNull(foundTrainee);
//    verify(traineeRepository).findByUserUsername(username);
//  }
//
//  @Test
//  void getTraineeByUsername_ShouldThrowException_WhenTraineeDoesNotExist() {
//
//    String username = "john.doe";
//    when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.empty());
//
//    assertThrows(NoSuchElementException.class, () -> {
//      traineeService.getTraineeByUsername(username);
//    });
//  }
//
//  @Test
//  void updateTrainee_ShouldUpdateTraineeDetails() {
//
//    Trainee trainee = new Trainee();
//    trainee.setUser(new User());
//
//    String newName = "Jane";
//    String newLastName = "Smith";
//    String newUsername = "jane.smith";
//    String newPassword = "newPassword";
//    String newAddress = "456 Street";
//    LocalDate newDateOfBirth = LocalDate.of(1985, 5, 15);
//    boolean activeStatus = true;
//
//    traineeService.updateTrainee(trainee, newName, newLastName, newUsername, newPassword, activeStatus, newAddress, newDateOfBirth);
//
//    assertEquals(newName, trainee.getUser().getFirstName());
//    assertEquals(newLastName, trainee.getUser().getLastName());
//    assertEquals(newUsername, trainee.getUser().getUsername());
//    assertEquals(newPassword, trainee.getUser().getPassword());
//    assertEquals(newAddress, trainee.getAddress());
//    assertEquals(newDateOfBirth, trainee.getDateOfBirth());
//    verify(traineeRepository).save(trainee);
//  }
//}
