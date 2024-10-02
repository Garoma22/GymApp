package com.example.gymApp.dto.trainer;

import com.example.gymApp.model.TrainingType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;


@JsonPropertyOrder({ "username", "firstName", "lastName", "specialization" })
@Component
@Data
public class TrainerDto {


  @NotNull(message = "First name is required")
  @NotEmpty(message = "First name cannot be empty")
  private String firstName;

  @NotNull(message = "Last name is required")
  @NotEmpty(message = "Last name cannot be empty")
  private String lastName;

  @NotNull(message = "Specialization is required")
  private String specialization;

  private TrainingType trainingType;  // need to be here!

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
