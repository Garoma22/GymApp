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
  public ResponseEntity<String> changePassword(
      @PathVariable String username,
      @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {

    if (changePasswordRequestDto.getOldPassword() == null || changePasswordRequestDto.getOldPassword().isEmpty() ||
        changePasswordRequestDto.getNewPassword() == null || changePasswordRequestDto.getNewPassword().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Old password and new password are required");
    }

    User user = userService.getUserByUsername(username); //user DTO!

    // todo: i also was trying to use here - User user = userService.getUserByPasswordAndCheckUsername
    //  and/or userService.getUserByPasswordAndUsername
    //  but it doesn't work properly for some reason

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    user.setPassword(changePasswordRequestDto.getNewPassword());
    userService.saveUpdatedUser(user);


    //! Required response in the task
    return ResponseEntity.ok("Password changed successfully!");
  }





  //15+16 - the joint method for trainee and trainer
  @PatchMapping("/users/{username}/status")
  public ResponseEntity<?> activateTrainee(@PathVariable String username,
      @RequestParam String isActiveStatus) {

    User user = userService.getUserByUsername(username);

//    boolean activityStatus = userService.setActivityStatusToUser(isActiveStatus, user);

    user.setActive(Boolean.parseBoolean(isActiveStatus));

    userService.saveUpdatedUser(user);

    return ResponseEntity.ok(user); //get back dto!

//      return ResponseEntity.ok().build();
  }
}

