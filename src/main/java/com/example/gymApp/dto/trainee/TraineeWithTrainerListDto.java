package com.example.gymApp.dto.trainee;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.model.TrainingType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "firstName", "lastName",  "dateOfBirth",
    "address", "active", "trainers" })
@Data

public class TraineeWithTrainerListDto {

 private String username;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private String address;
  private boolean isActive;
  private List<TrainerDto> trainers;

  public TraineeWithTrainerListDto(String firstName, List<TrainerDto> trainers, String lastName,
      LocalDate dateOfBirth, String address, boolean isActive) {
    this.firstName = firstName;
    this.trainers = trainers;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.isActive = isActive;
  }


  public TraineeWithTrainerListDto(String username,
      String firstName, String lastName, LocalDate dateOfBirth,
      String address, boolean active, List<TrainerDto> trainersList) {

    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.isActive = active;
    this.trainers = trainersList;

  }


}


