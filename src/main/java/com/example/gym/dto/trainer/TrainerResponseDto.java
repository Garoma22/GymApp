package com.example.gym.dto.trainer;

import com.example.gym.model.TrainingType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.stereotype.Component;

  @JsonPropertyOrder({ "username", "firstName", "lastName", "specialization" })
  @Component
  @Data
  public class TrainerResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingType trainingType;
}
