package com.example.gymApp.model;


import lombok.Data;


@Data
public class TrainingType {


  public TrainingType() {
  }

  public TrainingType(String typeName) {
    this.typeName = typeName;
  }

  private Long id;

  private String typeName;

}

