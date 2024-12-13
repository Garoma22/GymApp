package com.example.gym.dto.training;


import java.time.LocalDate;
import lombok.Data;

@Data
public class TrainingForTrainerResponseDto {

  private String trainingName;
  private LocalDate trainingDate;
  private String trainingType;
  private String traineeName;
}



