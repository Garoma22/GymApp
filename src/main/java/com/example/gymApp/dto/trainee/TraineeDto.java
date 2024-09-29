package com.example.gymApp.dto.trainee;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@Data
public class TraineeDto {

  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String address;

  @JsonProperty("isActive")
  private boolean isActive;
  private String username;

}


