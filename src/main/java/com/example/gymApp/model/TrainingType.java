//package com.example.gymApp.model;
//
//
//import lombok.Data;
//
//
//@Data
//public class TrainingType {
//  private String typeName;
//
//  public TrainingType() {
//  }
//
//  public TrainingType(String typeName) {
//    this.typeName = typeName;
//  }
//
//
//
//
//}
//


package com.example.gymApp.model;

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
