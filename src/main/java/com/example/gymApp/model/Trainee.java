package com.example.gymApp.model;


import lombok.Data;

import java.time.LocalDate;


@Data
public class Trainee extends User {


  private LocalDate dateOfBirth;


  private String address;


  public Trainee(Long id, String firstName, String lastName, String username, String password,
      boolean isActive, LocalDate dateOfBirth, String address) {
    super(id, firstName, lastName, username, password, isActive);
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }

  public Trainee() {
  }

  @Override
  public String toString() {
    return "Trainee{" +
        "id=" + getId() +
        ", firstName='" + getFirstName() + '\'' +
        ", lastName='" + getLastName() + '\'' +
        ", username='" + getUsername() + '\'' +
        ", password='" + getPassword() + '\'' +
        ", isActive=" + isActive() +
        ", dateOfBirth=" + dateOfBirth +
        ", address='" + address + '\'' +
        '}';
  }
}
