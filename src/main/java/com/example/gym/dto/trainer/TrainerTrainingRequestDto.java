package com.example.gym.dto.trainer;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TrainerTrainingRequestDto {

  private String username;
  private LocalDate periodFrom;
  private LocalDate periodTo;
  private String traineeFirstName;

}


