package com.example.gymApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages = "com.example.gymApp")
@EnableFeignClients
public class GymApplication {

  public static void main(String[] args) {
    SpringApplication.run(GymApplication.class, args);
  }

}
