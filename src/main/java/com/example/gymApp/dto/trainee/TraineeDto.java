package com.example.gymApp.dto.trainee;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@Data
public class TraineeDto {


  @NotNull(message = "First name is required")
  @NotEmpty(message = "First name cannot be empty")
  private String firstName;

  @NotNull(message = "First name is required")
  @NotEmpty(message = "First name cannot be empty")
  private String lastName;

  @NotNull(message = "Date of birth is required")
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
      message = "Date of birth must be in the format yyyy-MM-dd")
  private String dateOfBirth;

  @NotNull(message = "Address is required")
  @NotEmpty(message = "Address cannot be empty")
  private String address;

  @JsonProperty("isActive")
  private boolean isActive;

  private String username;

}


