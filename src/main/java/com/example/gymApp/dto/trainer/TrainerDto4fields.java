package com.example.gymApp.dto.trainer;

import com.example.gymApp.model.TrainingType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.stereotype.Component;



  @JsonPropertyOrder({ "username", "firstName", "lastName", "specialization" })
  @Component
  @Data
  public class TrainerDto4fields {


    private String username;
    private String firstName;
    private String lastName;
    private TrainingType trainingType;
}
