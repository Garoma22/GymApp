package com.example.gymApp.controller;


import com.example.gymApp.dto.training.Training5FieldsRequestDto;
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
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.NoSuchElementException;
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
@RequestMapping("/training")
public class TrainingController {


  private final TrainingService trainingService;
  private final TrainerService trainerService;
  private final TraineeService traineeService;
  private final UserService userService;
  private final TraineeRepository traineeRepository;
  private final TrainerRepository trainerRepository;
  private final UserRepository userRepository;
  private final TrainingTypeService trainiingTypeService;


  public TrainingController(
      TrainingService trainingService, TrainerService trainerService,
      TraineeService traineeService, UserService userService, TraineeRepository traineeRepository,
      TrainerRepository trainerRepository, UserRepository userRepository,
      TrainingTypeService trainiingTypeService) {
    this.trainingService = trainingService;

    this.trainerService = trainerService;
    this.traineeService = traineeService;
    this.userService = userService;
    this.traineeRepository = traineeRepository;
    this.trainerRepository = trainerRepository;
    this.userRepository = userRepository;
    this.trainiingTypeService = trainiingTypeService;
  }

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



  @PostMapping("/addTraining")
  public ResponseEntity<?> addTraining
  (@RequestBody Training5FieldsRequestDto request)

  {

    try {
      Trainee trainee = traineeService.getTraineeByUsername(request.getTraineeUsername());

      Trainer trainer = trainerService.getTrainerByUsername(request.getTrainerUsername());

      Training training = trainingService.createTraining5args(trainer, trainee,
          request.getTrainingName(),
      request.getTrainingDate(), request.getTrainingDuration());

//      return ResponseEntity.ok(training);  // useful for checking

      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Training not found");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred ");
    }
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


  @GetMapping("/getAllTrainingTypes")
  public ResponseEntity<?> getTrainingTypes(){
    log.info("Received request to get all training types");
   List<TrainingType> list  = trainiingTypeService.getTrainingTypeList();
    log.info("Successfully retrieved {} training types", list.size());
   return ResponseEntity.ok(list);



  }


}
