package com.example.gym.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "training_types")
public class TrainingType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  public TrainingType() {}

  public TrainingType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "TrainingType{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
