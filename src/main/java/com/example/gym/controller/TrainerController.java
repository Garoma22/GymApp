package com.example.gym.controller;

import com.example.gym.dto.trainer.TrainerDto;
import com.example.gym.dto.trainer.TrainerWithTraineeListDto;
import com.example.gym.dto.training.TrainingForTrainerResponseDto;
import com.example.gym.service.TrainerService;
import com.example.gym.service.TrainingService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/protected/trainer-management")
@AllArgsConstructor
public class TrainerController {

  private final TrainerService trainerService;
  private final TrainingService trainingService;



  /*
  8. Get Trainer Profile (GET method)
a. Request
I. Username (required)


b. Response
I. First Name
II. Last Name
III. Specialization (Training type reference)
IV. Is Active
V. Trainees List
   1. Trainee Username
   2. Trainee First Name
   3. Trainee Last Name
   */


  @GetMapping("/trainers/{username}/trainees")
  public ResponseEntity<TrainerWithTraineeListDto> getTrainerProfileWithTraineeList(
      @PathVariable String username) {
    return ResponseEntity.ok(trainerService.getTrainerWithTrainees(username));
  }


  /*
  9. Update Trainer Profile (PUT method)
a. Request
I. Username (required)
II. First Name (required)
III. Last Name (required)
IV. Specialization (read only) (Training type reference)
V. Is Active (required)

b. Response
I. Username
II. First Name
III. Last Name
IV. Specialization (Training type reference)
V. Is Active
VI. Trainees List
1. Trainee Username
2. Trainee First Name
3. Trainee Last Name
   */

//HERE WE UPDATING FIRST AND SECOND NAME + MAKE A CUSTOM RESPONSE


  @PutMapping("trainers/{username}/trainees")
  public ResponseEntity<TrainerWithTraineeListDto> updateTrainerProfile(
      @PathVariable String username,
      @RequestBody TrainerDto trainerDto) {
    var responseDto = trainerService.updateTrainerProfile(username, trainerDto);
    log.info("THIS IS RESPONSE_DTO : " + responseDto);
    return ResponseEntity.ok(responseDto);
  }


    /*
13. Get Trainer Trainings List (GET method)
  a. Request
     I. Username (required)
     II. Period From (optional)
     III. Period To (optional)
     IV. Trainee Name (optional)
  b. Response
     I. Training Name
     II. Training Date
     III. Training Type
     IV. Training Duration
     V. Trainee Name
 */

  /* This method retrieves a trainer's training sessions with optional
   filtering by date range and trainee's first name.

   */


  @GetMapping("/trainers/{username}/trainings")
  public ResponseEntity<List<TrainingForTrainerResponseDto>> getTrainerTrainingsListByDynamicCriteria(
      @PathVariable String username,
      @RequestParam(required = false) String periodFrom,
      @RequestParam(required = false) String periodTo,
      @RequestParam(required = false) String traineeFirstName) {

    List<TrainingForTrainerResponseDto> dtoList = trainingService
        .getTrainerTrainingsByCriteria(username, periodFrom, periodTo, traineeFirstName);

    return ResponseEntity.ok(dtoList);
  }
}