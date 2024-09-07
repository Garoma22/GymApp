package com.example.gymApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "trainees")
@Data
@NoArgsConstructor
public class Trainee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "traineeId", nullable = false)
  private Long traineeId;  // Собственный идентификатор для Trainee


  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "address")
  private String address;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;  // Внешний ключ на User

  public Trainee(

      User user,

      LocalDate dateOfBirth, String address) {

    this.user = user;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }

  @Override
  public String toString() {
    return "Trainee{" +
        ", firstName='" + user.getFirstName() + '\'' +
        ", lastName='" + user.getLastName() + '\'' +
        ", username='" + user.getUsername() + '\'' +
        ", password='" + user.getPassword() + '\'' +
        ", isActive=" + user.isActive() +
        ", dateOfBirth=" + dateOfBirth +
        ", address='" + address + '\'' +
        '}';
  }
}
