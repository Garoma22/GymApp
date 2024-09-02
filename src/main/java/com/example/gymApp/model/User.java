package com.example.gymApp.model;

import lombok.Data;
@Data
public class User {
  private Long id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private boolean isActive;

  public User(Long id, String firstName, String lastName, String username, String password,
      boolean isActive) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.isActive = isActive;
  }

  public User() {
  }
}




