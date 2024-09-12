package com.example.gymApp.service;

import com.example.gymApp.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProfileService {

  private final UserRepository userRepository;

  @Autowired
  public ProfileService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  public String generateUsername(String firstName, String lastName) {
    String baseUsername = firstName + "." + lastName;


    List<String> existingUsernames = userRepository.findAllByUsernameStartingWith(baseUsername + "%");

    if (!existingUsernames.contains(baseUsername)) {
      return baseUsername;
    }


    int maxSuffix = existingUsernames.stream()
        .filter(username -> username.matches(baseUsername + "\\d+"))
        .map(username -> username.substring(baseUsername.length()))
        .mapToInt(suffix -> suffix.isEmpty() ? 0 : Integer.parseInt(suffix))
        .max()
        .orElse(0);


    return baseUsername + (maxSuffix + 1);
  }


  public String generateRandomPassword() {
    int length = 10;
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();

    return IntStream.range(0, length)
        .mapToObj(i -> String.valueOf(characters.charAt(random.nextInt(characters.length()))))
        .collect(Collectors.joining());
  }
}
