package com.example.gymApp.dto.trainer;

import com.example.gymApp.model.TrainingType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.stereotype.Component;


@JsonPropertyOrder({ "username", "firstName", "lastName", "specialization" })
@Component
@Data
public class TrainerDto {

  private String firstName;
  private String lastName;
  private String specialization;
  private TrainingType trainingType;

  @JsonProperty("isActive")
  private boolean isActive;
  private String username;

  public TrainerDto() {
  }

  public TrainerDto(String firstName, String lastName, String specialization) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialization = specialization;
  }

  public TrainerDto(String username, String firstName, String lastName, String specialization) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialization = specialization;

  }
}
