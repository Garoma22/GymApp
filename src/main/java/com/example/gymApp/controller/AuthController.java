package com.example.gymApp.controller;

import com.example.gymApp.actuator.CustomMetrics;
import com.example.gymApp.authentification.AuthenticationRequest;
import com.example.gymApp.authentification.AuthenticationResponse;
import com.example.gymApp.authentification.RegisterRequest;
import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.service.AuthService;

import com.example.gymApp.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final CustomMetrics customMetrics;
  private final TokenBlacklistService tokenBlacklistService;


  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody AuthenticationRequest request
  ){
    return ResponseEntity.ok(authService.authenticate(request));
  }


  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String jwt = authHeader.substring(7);
      tokenBlacklistService.addTokenToBlacklist(jwt);
      return ResponseEntity.ok("Logged out successfully.");
    }
    return ResponseEntity.badRequest().body("Invalid Authorization header.");
  }

  @PostMapping("/register/trainee")
  public ResponseEntity<AuthenticationResponse> registerTrainee(
      @RequestBody TraineeDto request
  ){
    return ResponseEntity.ok(authService.registerTrainee(request));
  }

  @PostMapping("/register/trainer")
  public ResponseEntity<AuthenticationResponse> registerTrainer(
      @RequestBody TrainerDto request
  ){
    return ResponseEntity.ok(authService.registerTrainer(request));
  }
}

