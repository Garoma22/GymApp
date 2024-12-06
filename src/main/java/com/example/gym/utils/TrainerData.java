package com.example.gym.utils;


import lombok.Data;

@Data

public class TrainerData {

  private Long id;
  private String firstName;
  private String lastName;
  private String specialization;

  public TrainerData(Long id, String firstName, String lastName, String specialization) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialization = specialization;
  }
}
