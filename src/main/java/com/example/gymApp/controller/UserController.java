package com.example.gymApp.controller;

import com.example.gymApp.dto.user.ChangePasswordRequestDto;
import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.dto.user.UserStatusUpdateRequest;
import com.example.gymApp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/protected/user-management")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

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
      @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {//old+ new pass

    UserLoginDto userDto = userService.getUserDtoByUsername(username);
    userDto.setPassword(changePasswordRequestDto.getNewPassword());
    userService.setNewPasswordToUserFromDtoAndSaveToDb(userDto);
    return ResponseEntity.ok("Password changed successfully!");
  }

//15+16 - the joint method for trainee and trainer

  //here we are using @RequestBody because it is Patch Mapping
  @PatchMapping("/users/{username}/status")
  public ResponseEntity<String> activateTrainee(@PathVariable String username,
      @RequestBody UserStatusUpdateRequest request) {
      userService.setActivityStatusToUser(username, request);
      return ResponseEntity.ok("User activity status updated successfully!");



  }
}

