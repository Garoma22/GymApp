package com.example.gymApp.controller;

import com.example.gymApp.dto.training.TrainingInfoResponseDto;
import com.example.gymApp.dto.training.TrainingRequestDto;
import com.example.gymApp.dto.training.TrainingResponseDto;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.TrainingType;
import com.example.gymApp.repository.TraineeRepository;
import com.example.gymApp.repository.TrainerRepository;
import com.example.gymApp.repository.UserRepository;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import com.example.gymApp.service.TrainingTypeService;
import com.example.gymApp.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/protected/training-management")
@AllArgsConstructor
public class TrainingController {

  private final TrainingService trainingService;
  private final TrainerService trainerService;
  private final TraineeService traineeService;
  private final UserService userService;
  private final TraineeRepository traineeRepository;
  private final TrainerRepository trainerRepository;
  private final UserRepository userRepository;
  private final TrainingTypeService trainingTypeService;
private final TrainingInfoResponseDto trainingInfoResponseDto;

  /*
  14. Add Training (POST method)
a. Request
      I. Trainee username (required)
      II. Trainer username (required)
      III. Training Name (required)
      IV. Training Date (required)
      V. Training Duration (required)
b. Response
      I. 200 OK
   */


  @PostMapping("/trainings")
  public ResponseEntity<TrainingInfoResponseDto> addTraining
      (@RequestBody TrainingRequestDto request) {

    return ResponseEntity.ok(trainingService.getTrainingInfoResponseDto(request));
  }


/*
          17.

Get Training types (GET method)
    a. Request -no data
    b. Response
       I. Training types
          1. Training type
          2. Training type Id
 */

  @GetMapping("/training-types")
  public ResponseEntity<List<TrainingResponseDto>> getTrainingTypes() {
    log.info("Received request to get all training types");
    List<TrainingResponseDto> list = trainingTypeService.getTrainingTypeList(); //getting back dtos!
    log.info("Successfully retrieved {} training types", list.size());
    return ResponseEntity.ok(list);
  }
}


