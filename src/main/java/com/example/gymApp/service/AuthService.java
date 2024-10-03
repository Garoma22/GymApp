package com.example.gymApp.service;

import com.example.gymApp.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserService userService;

  public boolean authenticate(String username, String password, HttpServletRequest request) {
    User user = userService.getUserByPasswordAndUsername(username, password);
    if (user != null) {
      HttpSession session = request.getSession();
      session.setAttribute("user", user);
      return true;
    }
    return false;
  }
}
