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

  public User getUserByPassword(String password) {

    Optional<User> user = userRepository.findByPassword(password);
    if (user.isPresent()) {
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
}
