package com.example.gymApp.dto.training;

import java.time.LocalDate;
import lombok.Data;


@Data
public class Training5FieldsRequestDto {
  private String traineeUsername;
  private String trainerUsername;
  private String trainingName;
  private LocalDate trainingDate;
  private Integer trainingDuration;

}


