package com.example.gymApp.service;


import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {


  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByPasswordAndUsername(String password, String username) {

    Optional<User> user = userRepository.findByPassword(password);
    if (user.isPresent()&& user.get().getUsername().equals(username)) {
      return user.get();
    } else {
      throw new NoSuchElementException("No user with such password");
    }
  }

  public User getUserByUsername(String username){
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()){
      return user.get();
    }else{
      throw new NoSuchElementException("No user with such username");
    }
  }



  public void saveUpdatedUser(User user) {
    userRepository.save(user);
  }

  public boolean setActivityStatusToUser(String activityStatus, User user) {
    boolean newActiveStatus = false;
    boolean actualUserStatus = user.isActive();

    try {

      if (activityStatus.equals("true") || activityStatus.equals("false")) {
        newActiveStatus = Boolean.parseBoolean(activityStatus);
        System.out.println("Success! Activity status set to user : "  + user.getUsername() + " is : "  + newActiveStatus);

        return newActiveStatus; //returns true or false
      } else {
        throw new IllegalArgumentException("Invalid input. Please enter 'true' or 'false'.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }return actualUserStatus;
  }
}
