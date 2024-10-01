package com.example.gymApp.controller;

import com.example.gymApp.dto.trainee.TraineeDto;
import com.example.gymApp.dto.trainee.TraineeMapper;
import com.example.gymApp.dto.trainer.TrainerDto;
import com.example.gymApp.dto.trainer.TrainerMapper;
import com.example.gymApp.service.ProfileService;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
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
  private final TraineeService traineeService;
  private final ProfileService profileService;
  private final TraineeMapper traineeMapper;
  private final TrainerService trainerService;
  private final TrainerMapper trainerMapper;

  @PostMapping("/trainees")
  public ResponseEntity<?> registerTrainee(@RequestBody TraineeDto traineeDto) {
    Map<String, String> response = profileService.registerTrainee(traineeDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/trainers")
  public ResponseEntity<?> registerTrainer(@RequestBody TrainerDto trainerDto) {
    Map<String, String> response = profileService.registerTrainer(trainerDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
