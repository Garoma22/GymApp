package com.example.gymApp.utils;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class TraineeData {


  private Long id;

  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String address;

  public TraineeData(long id, String firstName, String lastName, String dateOfBirth,
      String address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }


}