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

@Entity
@Table(name = "trainers")
@Data
public class Trainer{




  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;  // Собственный ID

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;  // Внешний ключ на User

  @Column(name = "specialization", nullable = false)
  private String specialization;  // Специализация тренера

  public Trainer() {
  }

  public Trainer(User user, String specialization) {
    this.user = user;
    this.specialization = specialization;
  }




  @Override
  public String toString() {
    return "Trainer{" +
        "id=" + getId() +
        ", firstName='" + user.getFirstName() + '\'' +
        ", lastName='" + user.getLastName() + '\'' +
        ", username='" + user.getUsername() + '\'' +
        ", password='" + user.getPassword() + '\'' +
        ", isActive=" + user.isActive() +
        ", specialization='" + specialization + '\'' +
        '}';
  }
}