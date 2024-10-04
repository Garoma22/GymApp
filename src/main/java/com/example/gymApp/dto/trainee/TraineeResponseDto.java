package com.example.gymApp.dto.trainee;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TraineeResponseDto {
  private String firstName;
  private String lastName;
  private String username;


}
