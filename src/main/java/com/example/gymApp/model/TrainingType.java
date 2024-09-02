package com.example.gymApp.model;


import lombok.Data;


@Data
public class TrainingType {
  private String typeName;

  public TrainingType() {
  }

  public TrainingType(String typeName) {
    this.typeName = typeName;
  }




}

