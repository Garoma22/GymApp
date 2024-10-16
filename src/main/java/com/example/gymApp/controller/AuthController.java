package com.example.gymApp.controller;

import com.example.gymApp.actuator.CustomMetrics;
import com.example.gymApp.authentification.AuthenticationRequest;
import com.example.gymApp.authentification.AuthenticationResponse;
import com.example.gymApp.authentification.RegisterRequest;
import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.service.AuthService;
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


//  @PostMapping("/login")
//  public ResponseEntity<String> login(@RequestBody UserLoginDto loginRequest, HttpServletRequest request) {
//
//    long startTime = System.currentTimeMillis(); //start
//
//    boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword(), request);
//
//    long duration = System.currentTimeMillis() - startTime; //stop
//
//    customMetrics.recordOperationDuration(duration); //recording operation duration
//
//    if (isAuthenticated) {
//      customMetrics.recordSuccess(); // Increasing the counter of successful operations
//      return ResponseEntity.ok("Login successful!");
//    } else {
//      customMetrics.recordFailure(); //decreasing the counter of successful operations
//      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//    }
//  }
//
//
//
//  @PostMapping("/logout")
//  public ResponseEntity<String> logout(HttpServletRequest request) {
//    HttpSession session = request.getSession(false);
//    if (session != null) {
//      session.invalidate();
//    }
//    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logged out successfully.");
//  }


  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ){

    return ResponseEntity.ok(authService.register(request));

  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody AuthenticationRequest request
  ){


    return ResponseEntity.ok(authService.authenticate(request));
  }




}
