package com.example.gym.utils;


import lombok.Data;

@Data
public class TraineeData {

  private Long id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String address;

  public TraineeData(Long id, String firstName, String lastName, String dateOfBirth,
      String address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }
}