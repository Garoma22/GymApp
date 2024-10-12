package com.example.gymApp.service;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.dto.user.UserMapper;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private Logger log;

  @InjectMocks
  private UserService userService;

  @Test
  void testGetUserByPasswordAndUsername_UserFound() {
    String username = "testuser";
    String password = "testpassword";

    User mockUser = new User();
    mockUser.setUsername(username);
    mockUser.setPassword(password);

    when(userRepository.findByUsernameAndPassword(username, password))
        .thenReturn(Optional.of(mockUser));
    User result = userService.getUserByPasswordAndUsername(username, password);
    assertNotNull(result);
    assertEquals(username, result.getUsername());
    assertEquals(password, result.getPassword());

    verify(userRepository, times(1)).findByUsernameAndPassword(username, password);
  }

  @Test
  void testGetUserByPasswordAndUsername_UserNotFound() {
    String username = "nonexistentuser";
    String password = "wrongpassword";

    when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      userService.getUserByPasswordAndUsername(username, password);
    });
    assertEquals("No user with such username and password", exception.getMessage());

    verify(userRepository, times(1)).findByUsernameAndPassword(username, password);
  }

  @Test
  void testGetUserDtoByUsername_UserFound() {
    String username = "testuser";

    User mockUser = new User();
    mockUser.setUsername(username);

    UserLoginDto mockUserLoginDto = new UserLoginDto();
    mockUserLoginDto.setUsername(username);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

    when(userMapper.toUserLoginDto(mockUser)).thenReturn(mockUserLoginDto);

    UserLoginDto result = userService.getUserDtoByUsername(username);

    assertNotNull(result);
    assertEquals(username, result.getUsername());

    verify(userRepository, times(1)).findByUsername(username);

    verify(userMapper, times(1)).toUserLoginDto(mockUser);
  }

  @Test
  void testGetUserDtoByUsername_UserNotFound() {
    String username = "nonexistentuser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      userService.getUserDtoByUsername(username);
    });

    assertEquals("No user with such username", exception.getMessage());
    verify(userRepository, times(1)).findByUsername(username);
    verify(userMapper, never()).toUserLoginDto(any(User.class));
  }

  @Test
  void testGetUserByUsername_UserFound() {
    String username = "testuser";
    User mockUser = new User();
    mockUser.setUsername(username);
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
    User result = userService.getUserByUsername(username);
    assertNotNull(result);
    assertEquals(username, result.getUsername());
    verify(userRepository, times(1)).findByUsername(username);
  }

  @Test
  void testGetUserByUsername_UserNotFound() {
    String username = "nonexistentuser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      userService.getUserByUsername(username);
    });
    assertEquals("No user with such username", exception.getMessage());
    verify(userRepository, times(1)).findByUsername(username);
  }

  @Test
  void testSetNewPasswordToUserFromDtoAndSaveToDb_UserFound() {
    UserLoginDto userLoginDto = new UserLoginDto();
    userLoginDto.setUsername("testuser");
    userLoginDto.setPassword("newPassword");

    User mockUser = new User();
    mockUser.setUsername("testuser");
    mockUser.setPassword("oldPassword");

    when(userRepository.findByUsername(userLoginDto.getUsername())).thenReturn(Optional.of(mockUser));

    userService.setNewPasswordToUserFromDtoAndSaveToDb(userLoginDto);

    assertEquals("newPassword", mockUser.getPassword());

    verify(userRepository, times(1)).save(mockUser);
  }

  @Test
  void testSetNewPasswordToUserFromDtoAndSaveToDb_UserNotFound() {
    UserLoginDto userLoginDto = new UserLoginDto();
    userLoginDto.setUsername("nonexistentuser");
    userLoginDto.setPassword("newPassword");

    when(userRepository.findByUsername(userLoginDto.getUsername())).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
      userService.setNewPasswordToUserFromDtoAndSaveToDb(userLoginDto);
    });

    assertEquals("No user with such username", exception.getMessage());
    verify(userRepository, never()).save(any());
  }

  @Test
  void testSetActivityStatusToUser_ValidTrue() {
    User user = new User();
    user.setUsername("testuser");
    String activityStatus = "true";
    boolean result = userService.setActivityStatusToUser(activityStatus, user);
    assertTrue(result);
  }

  @Test
  void testSetActivityStatusToUser_ValidFalse() {
    User user = new User();
    user.setUsername("testuser");
    String activityStatus = "false";
    boolean result = userService.setActivityStatusToUser(activityStatus, user);
    assertFalse(result);
    }

  @Test
  void testSetActivityStatusToUser_InvalidStatus2() {
    User user = new User();
    user.setUsername("testuser");

    String invalidStatus = "invalid";
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      userService.setActivityStatusToUser(invalidStatus, user);
    });
    assertEquals("Invalid input. Please enter 'true' or 'false'.", exception.getMessage());
     }


  @Test
  void testSetActivityStatusToUser_ValidTrue2() {
    String username = "testuser";
    String newActiveStatus = "true";

    User user = new User();
    user.setUsername(username);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    userService.setActivityStatusToUser(username, newActiveStatus);

    assertTrue(user.isActive());
    verify(userRepository, times(1)).save(user); }

  @Test
  void testSetActivityStatusToUser_ValidFalse2() {
    String username = "testuser";
    String newActiveStatus = "false";

    User user = new User();
    user.setUsername(username);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    userService.setActivityStatusToUser(username, newActiveStatus);

    assertFalse(user.isActive());
    verify(userRepository, times(1)).save(user);
   }

  @Test
  void testSetActivityStatusToUser_InvalidStatus() {
    String username = "testuser";
    String invalidStatus = "invalidStatus";

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      userService.setActivityStatusToUser(username, invalidStatus);
    });

    assertEquals("Invalid activity status: invalidStatus. Status must be 'true' or 'false'.", exception.getMessage());
    verify(userRepository, never()).save(any(User.class));
    verify(log, never()).info(anyString());  }


}