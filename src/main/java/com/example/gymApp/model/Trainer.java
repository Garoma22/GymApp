package com.example.gymApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "trainers")
public class Trainer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "training_type_id", nullable = false)
  private TrainingType specialization; // Это теперь ссылка на TrainingType

  public Trainer() {}

  public Trainer(User user, TrainingType specialization) {
    this.user = user;
    this.specialization = specialization;
  }

  @Override
  public String toString() {
    return "Trainer{" +
        "id=" + id +
        ", firstName=" + user.getFirstName() +
        ", lastName=" + user.getLastName() +
        ", username=" + user.getUsername() +
        ", password=" + user.getPassword() +
        ", active=" + user.isActive() +
        ", specialization=" + specialization.getName() +
        '}';
  }







  public String getUsername() {
    return user.getUsername();
  }


}
