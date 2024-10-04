package com.example.gymApp.controller;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.service.ProfileService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterUserFreeController {

  private final ProfileService profileService;

  @PostMapping("/trainees")
  public ResponseEntity<Map<String, String>> registerTrainee(
      @Valid @RequestBody TraineeDto traineeDto) {
    Map<String, String> response = profileService.registerTrainee(traineeDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
  @PostMapping("/trainers")
  public ResponseEntity<Map<String, String>> registerTrainer(@Valid @RequestBody TrainerDto trainerDto) {
    Map<String, String> response = profileService.registerTrainer(trainerDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
