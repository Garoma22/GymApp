//package com.example.gymApp.model;
//
//
//import lombok.Data;
//
//import java.time.LocalDate;
//
//
//@Data
//public class Trainee extends User {
//
//
//  private LocalDate dateOfBirth;
//
//
//  private String address;
//
//
//  public Trainee(Long id, String firstName, String lastName, String username, String password,
//      boolean isActive, LocalDate dateOfBirth, String address) {
//    super(id, firstName, lastName, username, password, isActive);
//    this.dateOfBirth = dateOfBirth;
//    this.address = address;
//  }
//
//  public Trainee() {
//  }
//
//  @Override
//  public String toString() {
//    return "Trainee{" +
//        "id=" + getId() +
//        ", firstName='" + getFirstName() + '\'' +
//        ", lastName='" + getLastName() + '\'' +
//        ", username='" + getUsername() + '\'' +
//        ", password='" + getPassword() + '\'' +
//        ", isActive=" + isActive() +
//        ", dateOfBirth=" + dateOfBirth +
//        ", address='" + address + '\'' +
//        '}';
//  }
//}



package com.example.gymApp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "trainees")
public class Trainee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;  // Связь с User через внешний ключ

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
