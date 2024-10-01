package com.example.gymApp.controller;

import com.example.gymApp.dto.user.ChangePasswordRequestDto;
import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.User;
import com.example.gymApp.service.UserService;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/protected/user-management")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  // 3 - this method moved to AuthController

//  @GetMapping("/login")
//  public ResponseEntity<String> login(@RequestParam String username,
//      @RequestParam String password) {
//    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//          .body("Username or password cannot be empty");
//    }
//      userService.getUserByPasswordAndUsername(password, username);
//      return ResponseEntity.ok("Login successful!");
//  }


  /* 4
  Change Login (PUT method)
a. Request
I. Username (required)
II. Old Password (required)
III. New Password (required)
b. Response
I. 200 OK
   */


  @PutMapping("/users/{username}/password")

//  @PutMapping("/change-password")
  public ResponseEntity<String> changePassword( @PathVariable String username,
      @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {

    if (username == null || username.isEmpty() ||
        changePasswordRequestDto.getOldPassword() == null || changePasswordRequestDto.getOldPassword().isEmpty() ||
        changePasswordRequestDto.getNewPassword() == null || changePasswordRequestDto.getNewPassword().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Username, old password, and new password are required");
    }

//    User user = userService.getUserByPasswordAndCheckUsername(
    User user = userService.getUserByPasswordAndUsername(
        changePasswordRequestDto.getOldPassword(),
        username
    );

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    user.setPassword(changePasswordRequestDto.getNewPassword());
    userService.saveUpdatedUser(user);

    return ResponseEntity.ok("Password changed successfully!");
  }





  //15+16 - the joint method for trainee and trainer
  @PatchMapping("/users/{username}/status")
  public ResponseEntity<?> activateTrainee(@PathVariable String username, String isActiveStatus) {

    User user = userService.getUserByUsername(username);

    boolean activityStatus = userService.setActivityStatusToUser(isActiveStatus, user);

    user.setActive(activityStatus);

    userService.saveUpdatedUser(user);

    return ResponseEntity.ok(user);

//      return ResponseEntity.ok().build();
  }
}

