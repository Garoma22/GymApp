package com.example.gymApp.dto.training;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TrainingInfoResponseDto {
  private String trainingName;
  private LocalDate trainingDate;
  private Integer trainingDuration;
}
