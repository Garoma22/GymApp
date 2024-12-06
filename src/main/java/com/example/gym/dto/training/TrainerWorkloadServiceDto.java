package com.example.gym.dto.training;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TrainerWorkloadServiceDto {

  private String trainerUsername;
  private String trainerFirstName;
  private String trainerLastName;
  private boolean isActive;
  private LocalDate trainingDate;
  private int trainingDuration;
  private String actionType;
}