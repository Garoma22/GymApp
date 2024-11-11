package com.example.gymApp.service;


import com.example.gymApp.dto.user.UserLoginDto;
import com.example.gymApp.dto.user.UserMapper;
import com.example.gymApp.dto.user.UserStatusUpdateRequest;
import com.example.gymApp.model.User;
import com.example.gymApp.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {


  private final UserRepository userRepository;
  private final UserMapper userMapper;


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

  public UserLoginDto getUserDtoByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No user with such username"));

    return userMapper.toUserLoginDto(user);
  }


  public User getUserByUsername(String username) {
  return userRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("No user with such username"));

  }


  public void setNewPasswordToUserFromDtoAndSaveToDb(UserLoginDto userLoginDto) {

    User user = getUserByUsername(userLoginDto.getUsername());
    user.setPassword(userLoginDto.getPassword());

    userRepository.save(user);
  }


  public void setNewPasswordToUserFromDtoAndSaveToDb(User user) {
    userRepository.save(user);
  }


  public boolean setActivityStatusToUser(String activityStatus, User user) {
    if (!activityStatus.equalsIgnoreCase("true") && !activityStatus.equalsIgnoreCase("false")) {
      throw new IllegalArgumentException("Invalid input. Please enter 'true' or 'false'.");
    }

    boolean newActiveStatus = Boolean.parseBoolean(activityStatus);
    log.info("Success! Activity status set to user: " + user.getUsername() + " is: " + newActiveStatus);

    return newActiveStatus;
  }


  public void setActivityStatusToUser(String username, UserStatusUpdateRequest request) {
    if(!username.equals(request.getUsername())){
          throw new IllegalArgumentException("Username from @PathVariable and username from @RequestBody are not equal" );
    }
    if (!"true".equalsIgnoreCase(request.getNewActiveStatus()) && !"false".equalsIgnoreCase(request.getNewActiveStatus())) {
      throw new IllegalArgumentException(
          "Invalid activity status: " + request.getNewActiveStatus() + ". Status must be 'true' or 'false'.");
    }
    User user = getUserByUsername(username);
    user.setActive(Boolean.parseBoolean(request.getNewActiveStatus()));
    log.info("Success! Activity status set to user : " + user.getUsername() + " is : "
        + request.getNewActiveStatus());
    userRepository.saveAndFlush(user);
  }
}
