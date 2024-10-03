package com.example.gymApp.controller;

import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.service.AuthService;
import com.example.gymApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;


  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserLoginDto loginRequest, HttpServletRequest request) {
    boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword(), request);

    if (isAuthenticated) {
      return ResponseEntity.ok("Login successful!");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logged out successfully.");
  }
}
