package com.example.gym.controller;

import com.example.gym.dto.user.ChangePasswordRequestDto;
import com.example.gym.dto.user.UserLoginDto;
import com.example.gym.dto.user.UserStatusUpdateRequest;
import com.example.gym.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/protected/user-management")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PutMapping("/users/{username}/password")
  public ResponseEntity<String> changePassword(
      @PathVariable String username,
      @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {

    UserLoginDto userDto = userService.getUserDtoByUsername(username);
    userDto.setPassword(changePasswordRequestDto.getNewPassword());
    userService.setNewPasswordToUserFromDtoAndSaveToDb(userDto);
    return ResponseEntity.ok("Password changed successfully!");
  }

  @PatchMapping("/users/{username}/status")
  public ResponseEntity<String> activateTrainee(@PathVariable String username,
      @RequestBody UserStatusUpdateRequest request) {
      userService.setActivityStatusToUser(username, request);
      return ResponseEntity.ok("User activity status updated successfully!");
  }
}

