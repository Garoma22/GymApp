package com.example.gymApp.controller;

import com.example.gymApp.model.User;
import com.example.gymApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;

  }
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
    // Check credentials
    User user = userService.getUserByPasswordAndUsername(username, password);

    if (user != null) {
      // Create session and store user info
      HttpSession session = request.getSession();
      session.setAttribute("user", user);

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
    return ResponseEntity.ok("Logged out successfully.");
  }
}
