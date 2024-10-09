package com.example.gymApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.example.gymApp")

public class GymApplication {

  public static void main(String[] args) {
    SpringApplication.run(GymApplication.class, args);
  }
}