package com.example.gym.dto.training;

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
