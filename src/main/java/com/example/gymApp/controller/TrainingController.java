package com.example.gymApp.controller;

import com.example.gymApp.dto.training.TrainingRequestDto;
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
  private final TrainingTypeService trainiingTypeService;

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
  public ResponseEntity<Void> addTraining  //to get back dto!
  (@RequestBody TrainingRequestDto request)  //todo  - addTrainingRequestDto
  {
    Trainee trainee = traineeService.getTraineeByUsername(request.getTraineeUsername());
    Trainer trainer = trainerService.getTrainerByUsername(request.getTrainerUsername());

    Training training = trainingService.createTraining5args(trainer, trainee,
        request.getTrainingName(),
        request.getTrainingDate(), request.getTrainingDuration());

    //answer option 1
//      return ResponseEntity.ok(training);  // useful for checking

    //answer option 2
    return ResponseEntity.status(HttpStatus.CREATED).build();

    //  answer option 3 - required in the task
//      return ResponseEntity.ok().build();
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
  public ResponseEntity<List<TrainingType>> getTrainingTypes() {
    log.info("Received request to get all training types");
    List<TrainingType> list = trainiingTypeService.getTrainingTypeList(); //get back dtos!
    log.info("Successfully retrieved {} training types", list.size());
    return ResponseEntity.ok(list);
  }
}
