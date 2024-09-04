package com.example.gymApp.model;


import lombok.Data;


@Data
public class Trainer extends User {


  private String specialization;


  public Trainer(Long id, String firstName, String lastName, String username, String password,
      boolean isActive, String specialization) {
    super(id, firstName, lastName, username, password, isActive);
    this.specialization = specialization;
  }


  public Trainer() {
  }


  @Override
  public String toString() {
    return "Trainer{" +
        "id=" + getId() +
        ", firstName='" + getFirstName() + '\'' +
        ", lastName='" + getLastName() + '\'' +
        ", username='" + getUsername() + '\'' +
        ", password='" + getPassword() + '\'' +
        ", isActive=" + isActive() +
        ", specialization='" + specialization + '\'' +
        '}';
  }
}