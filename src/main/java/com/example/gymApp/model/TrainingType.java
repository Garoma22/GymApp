package com.example.gymApp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "training_types")
@Data
public class TrainingType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;  // Primary Key

  @Column(name = "type_name", nullable = false)
  private String typeName;  // Required (not null)

  public TrainingType() {
  }

  public TrainingType(String typeName) {
    this.typeName = typeName;
  }
}

