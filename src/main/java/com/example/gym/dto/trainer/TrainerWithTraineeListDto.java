package com.example.gym.dto.trainer;
import com.example.gym.model.TrainingType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@JsonPropertyOrder({ "firstName", "lastName",  "trainingType", "active",
"traineeDtoList" })
@Data
public class TrainerWithTraineeListDto {
private String username;
  private String firstName;
  private String lastName;
  private TrainingType trainingType;
  private boolean isActive;
  private List<TraineeDto> traineeDtoList;

  @Data
  public static class TraineeDto {
    private String firstName;
    private String lastName;
    private String username;
  }
}
