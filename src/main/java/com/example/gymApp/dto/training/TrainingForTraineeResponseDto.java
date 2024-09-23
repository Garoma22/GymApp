package com.example.gymApp.dto.training;


import java.time.LocalDate;
import lombok.Data;

@Data
public class TrainingForTraineeResponseDto {


  private String trainingName;
  private LocalDate trainingDate;
  private String trainingType;
  private Integer trainingDuration;
  private String trainerName;
}
