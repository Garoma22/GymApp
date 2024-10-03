package com.example.gymApp.service;


import com.example.gymApp.model.User;
import com.example.gymApp.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {


  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  public User getUserByPasswordAndUsername(String username, String password) {
    return userRepository.findByUsernameAndPassword(username, password)
        .orElseThrow(() -> new NoSuchElementException("No user with such username and password"));
  }


  public User getUserByPasswordAndCheckUsername(String password, String username) {

    Optional<User> user = userRepository.findByPassword(password);
    if (user.isPresent() && user.get().getUsername().equals(username)) {
      return user.get();
    } else {
      throw new NoSuchElementException("No user with such password");
    }
  }

  public User getUserByUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new NoSuchElementException("No user with such username");
    }
  }


  public void saveUpdatedUser(User user) {
    userRepository.save(user);
  }

  public boolean setActivityStatusToUser(String activityStatus, User user) {
    boolean newActiveStatus = false;
    boolean actualUserStatus = user.isActive();

    if (activityStatus.equals("true") || activityStatus.equals("false")) {
      newActiveStatus = Boolean.parseBoolean(activityStatus);
      log.info("Success! Activity status set to user : " + user.getUsername() + " is : "
          + newActiveStatus);

      return newActiveStatus;
    } else {
      throw new IllegalArgumentException("Invalid input. Please enter 'true' or 'false'.");
    }
  }



}
