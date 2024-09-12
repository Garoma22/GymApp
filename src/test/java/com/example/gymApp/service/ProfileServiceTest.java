package com.example.gymApp.service;


import com.example.gymApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.test.annotation.DirtiesContext;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProfileServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ProfileService profileService;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.openMocks(this);
  }


  @Test
  void generateUsername_ShouldReturnBaseUsername_WhenNoSimilarUsernamesExist() {

    String firstName = "John";
    String lastName = "Doe";
    String baseUsername = "John.Doe";

    when(userRepository.findAllByUsernameStartingWith(baseUsername + "%"))
        .thenReturn(Collections.emptyList());


    String generatedUsername = profileService.generateUsername(firstName, lastName);


    assertEquals(baseUsername, generatedUsername);
    verify(userRepository).findAllByUsernameStartingWith(baseUsername + "%");
  }

  @Test
  void generateUsername_ShouldReturnBaseUsernameWithIncrementedSuffix_WhenSimilarUsernamesExist() {

    String firstName = "John";
    String lastName = "Doe";
    String baseUsername = "John.Doe";

    List<String> existingUsernames = Arrays.asList("John.Doe", "John.Doe1", "John.Doe2");
    when(userRepository.findAllByUsernameStartingWith(baseUsername + "%"))
        .thenReturn(existingUsernames);


    String generatedUsername = profileService.generateUsername(firstName, lastName);

    assertEquals("John.Doe3", generatedUsername);
    verify(userRepository).findAllByUsernameStartingWith(baseUsername + "%");
  }

  @Test
  void generateRandomPassword_ShouldReturnPasswordOfCorrectLengthAndCharacters() {

    String password = profileService.generateRandomPassword();

    assertNotNull(password);
    assertEquals(10, password.length());
    assertTrue(password.matches("[A-Za-z0-9]+"));  // Проверяем, что пароль состоит только из разрешенных символов
  }
}
