package com.example.gym.controller;

import com.example.gym.actuator.CustomMetrics;
import com.example.gym.authentification.AuthenticationRequest;
import com.example.gym.authentification.AuthenticationResponse;
import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.service.AuthService;

import com.example.gym.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

  private final static String BEARER = "Bearer ";

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody AuthenticationRequest request
  ){
    return ResponseEntity.ok(authService.authenticate(request));
  }


  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


    if (authHeader != null && authHeader.startsWith(BEARER)) {
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

