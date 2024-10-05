package com.example.gymApp.service;

import com.example.gymApp.model.User;
import com.example.gymApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUserByPasswordAndUsername_ShouldReturnUser_WhenPasswordAndUsernameMatch() {
    String username = "john.doe";
    String password = "password";
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);

    when(userRepository.findByPassword(password)).thenReturn(Optional.of(user));

    User result = userService.getUserByPasswordAndUsername(password, username);

    assertNotNull(result);
    assertEquals(username, result.getUsername());
    verify(userRepository, times(1)).findByPassword(password);
  }

  @Test
  void getUserByPasswordAndUsername_ShouldThrowException_WhenPasswordDoesNotMatch() {
    String username = "john.doe";
    String password = "password";
    User user = new User();
    user.setUsername("wrong.username");
    user.setPassword(password);

    when(userRepository.findByPassword(password)).thenReturn(Optional.of(user));

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () -> userService.getUserByPasswordAndUsername(password, username));

    assertEquals("No user with such password", exception.getMessage());
    verify(userRepository, times(1)).findByPassword(password);
  }

  @Test
  void getUserByUsername_ShouldReturnUser_Dto_WhenUsernameExists() {
    String username = "john.doe";
    User user = new User();
    user.setUsername(username);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    User result = userService.getUserDtoByUsername(username);

    assertNotNull(result);
    assertEquals(username, result.getUsername());
    verify(userRepository, times(1)).findByUsername(username);
  }

  @Test
  void getUserDtoByUsername_ShouldThrowException_WhenUsernameDoesNotExist() {
    String username = "john.doe";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () -> userService.getUserDtoByUsername(username));

    assertEquals("No user with such username", exception.getMessage());
    verify(userRepository, times(1)).findByUsername(username);
  }

  @Test
  void saveUpdatedUser_ShouldSaveUserFromDtoAndSaveToDb() {
    User user = new User();
    user.setUsername("john.doe");

    userService.setNewPasswordToUserFromDtoAndSaveToDb(user);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  void setActivityStatusToUser_ShouldReturnNewStatus_WhenValidInput() {
    User user = new User();
    user.setUsername("john.doe");
    user.setActive(false);

    boolean result = userService.setActivityStatusToUser("true", user);

    assertTrue(result);
    verify(userRepository, never()).save(user);
  }

  @Test
  void setActivityStatusToUser_ShouldReturnOldStatus_WhenInvalidInput() {
    User user = new User();
    user.setUsername("john.doe");
    user.setActive(false);

    boolean result = userService.setActivityStatusToUser("invalid", user);

    assertFalse(result);
    verify(userRepository, never()).save(user);
  }
}
