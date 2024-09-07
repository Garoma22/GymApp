//package com.example.gymApp.service;
//
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ProfileServiceTest {
//
//  @Test
//  public void testGenerateUsername() {
//    String firstName = "John";
//    String lastName = "Doe";
//
//    String expectedUsername = "John.Doe";
//    String actualUsername = ProfileService.generateUsername(firstName, lastName);
//
//    assertEquals(expectedUsername, actualUsername, "Username should be generated correctly");
//  }
//
//  @Test
//  public void testGenerateRandomPassword() {
//    String password = ProfileService.generateRandomPassword();
//
//    assertNotNull(password, "Password should not be null");
//    assertEquals(10, password.length(), "Password should be 10 characters long");
//
//    boolean hasLetter = password.chars().anyMatch(Character::isLetter);
//    boolean hasDigit = password.chars().anyMatch(Character::isDigit);
//
//    assertTrue(hasLetter, "Password should contain at least one letter");
//    assertTrue(hasDigit, "Password should contain at least one digit");
//  }
//}
//
