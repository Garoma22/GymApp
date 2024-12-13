package com.example.gym.dto.trainee;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TraineeTrainingRequestDto {
  private String username;
  private LocalDate periodFrom;
  private LocalDate periodTo;
  private String trainerName;
  private String specialization;

}
