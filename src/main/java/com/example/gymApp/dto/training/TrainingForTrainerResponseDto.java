package com.example.gymApp.dto.training;


import com.example.gymApp.model.TrainingType;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TrainingForTrainerResponseDto {

  private String trainingName;
  private LocalDate trainingDate;
  private String trainingType;
  private String traineeName;
}



