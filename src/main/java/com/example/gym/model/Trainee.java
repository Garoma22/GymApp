package com.example.gym.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "trainees")
public class Trainee{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  private LocalDate dateOfBirth;
  private String address;

  public Trainee(Long id, User user, LocalDate dateOfBirth, String address) {
    this.id = id;
    this.user = user;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }

  public Trainee() {

  }

  @Override
  public String toString() {
    return "Trainee{" +
        "id=" + id +
        ", user=" + user +
        ", dateOfBirth=" + dateOfBirth +
        ", address='" + address + '\'' +
        '}';
  }


  public String getUsername() {
    return user.getUsername();
  }
}
