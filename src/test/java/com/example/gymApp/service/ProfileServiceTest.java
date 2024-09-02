package com.example.gymApp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {

  @Test
  void testGenerateUsername_success() {
    // Arrange
    String firstName = "John";
    String lastName = "Doe";

    // Act
    String result = ProfileService.generateUsername(firstName, lastName);

    // Assert
    assertEquals("John.Doe", result);
  }

  @Test
  void testGenerateRandomPassword_success() {
    // Act
    String result = ProfileService.generateRandomPassword();

    // Assert
    assertNotNull(result);
    assertEquals(10, result.length());
    assertTrue(result.matches("[A-Za-z0-9]{10}"));
  }
}
