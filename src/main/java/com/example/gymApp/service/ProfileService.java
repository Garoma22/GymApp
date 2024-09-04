package com.example.gymApp.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class ProfileService {

  public static String generateUsername(String firstName, String lastName) {
    return firstName + "." + lastName;

  }

  public static String generateRandomPassword() {
    int length = 10;
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();

    return IntStream.range(0, length)
        .mapToObj(i -> String.valueOf(characters.charAt(random.nextInt(characters.length()))))
        .collect(Collectors.joining());
  }
}

