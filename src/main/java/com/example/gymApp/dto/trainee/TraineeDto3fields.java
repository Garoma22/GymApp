package com.example.gymApp.dto.trainee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;


@Component
@Data
public class TraineeDto3fields {

  private String firstName;
  private String lastName;
  private String username;


}
