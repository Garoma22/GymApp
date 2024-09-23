package com.example.gymApp.controller;

import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.User;
import com.example.gymApp.service.UserService;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public ResponseEntity<String> login(@RequestParam String username,
      @RequestParam String password) {
    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Username or password cannot be empty");
    }
    try {
      User isAuthenticated = userService.getUserByPasswordAndUsername(password, username);
      return ResponseEntity.ok("Login successful!");
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
  }


  @PutMapping("/change-password")
  public ResponseEntity<String> changePassword(@RequestParam String username,
      @RequestParam String oldPassword,
      @RequestParam String newPassword) {

    if (username == null || username.isEmpty() ||
        oldPassword == null || oldPassword.isEmpty() ||
        newPassword == null || newPassword.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Username, old password, and new password are required");
    }

    try {
      User user = userService.getUserByPasswordAndUsername(oldPassword, username);

      user.setPassword(newPassword);
      userService.saveUpdatedUser(user);

      return ResponseEntity.ok("Password changed successfully!");
    } catch (NoSuchElementException e) {

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Invalid username or old password");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while changing the password");
    }
  }

  //15+16 - the joint method for trainee and trainer
  @PatchMapping("/activateUser")
  public ResponseEntity<?> activateTrainee(@RequestParam String username, String isActiveStatus) {
    try {
      User user = userService.getUserByUsername(username);

      boolean activityStatus = userService.setActivityStatusToUser(isActiveStatus,user);

      user.setActive(activityStatus);

      userService.saveUpdatedUser(user);

      return ResponseEntity.ok(user);

//      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainee not found");

    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wring boolean var!!!");
    }

    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
  }
}

