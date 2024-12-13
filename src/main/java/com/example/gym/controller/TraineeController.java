package com.example.gym.controller;

import com.example.gym.dto.trainee.TraineeDto;
import com.example.gym.dto.trainee.TraineeMapper;
import com.example.gym.dto.trainee.TraineeTrainingRequestDto;
import com.example.gym.dto.trainee.TraineeWithTrainerListDto;


import com.example.gym.dto.trainer.TrainerResponseDto;
import com.example.gym.dto.training.TrainingForTraineeResponseDto;

import com.example.gym.service.TraineeService;
import com.example.gym.service.TrainerService;
import com.example.gym.service.TrainingService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/protected/trainee-management")

public class TraineeController {

  private final TraineeService traineeService;
  private final TraineeMapper traineeMapper;
  private final TrainerService trainerService;
  private final TrainingService trainingService;

//


  /*

  5. Get Trainee Profile (GET method)
a. Request
I. Username (required)
b. Response
I. First Name
II. Last Name
III. Date of Birth
IV. Address
V. Is Active
VI. Trainers List
1. Trainer Username
2. Trainer First Name
3. Trainer Last Name
4. Trainer Specialization (Training type reference)
   */

  @GetMapping("/trainees/{username}/trainers")
  public ResponseEntity<TraineeWithTrainerListDto> getTraineeProfileWithTrainersList(
      @PathVariable String username) {
    TraineeWithTrainerListDto responseDTO = traineeService.getTraineeProfileWithTrainersList(
        username);
    return ResponseEntity.ok(responseDTO);
  }


/*
6. Update Trainee Profile (PUT method)
a. Request
I. Username (required)
II. First Name (required)
III. Last Name (required)
IV. Date of Birth (optional)
V. Address (optional)
VI. Is Active (required)


b. Response
I. Username
II. First Name
III. Last Name
IV. Date of Birth
V. Address
VI. Is Active
VII. Trainers List
1. Trainer Username
2. Trainer First Name
3. Trainer Last Name
4. Trainer Specialization (Training type reference)

 */


  @PutMapping("/trainees/{username}")
  public ResponseEntity<TraineeWithTrainerListDto> updateTraineeProfile(
      @RequestBody TraineeDto traineeDto,
      @PathVariable String username) {
    return ResponseEntity.ok(traineeService.updateTraineeProfile(traineeDto, username));
  }



/*
7. Delete Trainee Profile (DELETE method)
a. Request
I. Username (required)
b. Response
I. 200 OK0
 */


  @DeleteMapping("/trainees/{username}")
  public ResponseEntity<String> deleteTraineeProfile(@PathVariable String username) {
    traineeService.deleteTraineeByUsername(username);
    return ResponseEntity.noContent().build();
  }





  /*
10. Get not assigned on trainee active trainers. (GET method)
a. Request
I. Username (required) trainee
b. Response
I. Trainer Username
II. Trainer First Name
III. Trainer Last Name
IV. Trainer Specialization (Training type reference)
   */

//  @GetMapping("/not-assigned-active-trainers-on-trainee")


  @GetMapping("/trainees/{traineeUsername}/trainers/not-assigned")
  public ResponseEntity<List<TrainerResponseDto>> getNotAssignedActiveTrainersOnTrainee(
      @PathVariable String traineeUsername) {

    List<TrainerResponseDto> trainers = trainerService.getAllActiveTrainersNotAssignedToTrainee(
        traineeUsername);
    return ResponseEntity.ok(trainers);
  }


  /*
  11. Update Trainee's Trainer List (PUT method)
a. Request
I. Trainee Username
II. Trainers List (required)
1. Trainer Username (required)
b. Response
I. Trainers List
1. Trainer Username
2. Trainer First Name
3. Trainer Last Name
4. Trainer Specialization (Training type reference)
   */


  @PutMapping("/trainees/{traineeUsername}/trainers")
  public ResponseEntity<List<TrainerResponseDto>> updateTraineeTrainersList(
      @PathVariable String traineeUsername, @RequestBody List<String> newTrainersUsernames) {
    List<TrainerResponseDto> updatedTrainersList = traineeService.updateTraineeTrainers(
        traineeUsername, newTrainersUsernames);
    return ResponseEntity.ok(updatedTrainersList);
  }


  /*
  12. Get Trainee Trainings List (GET method)
a. Request
   I. Username (required)
   II. Period From (optional)
   III. Period To (optional)
   IV. Trainer Name (optional)
   V. Training Type (optional)
b. Response
   I. Training Name
   II. Training Date
   III. Training Type
   IV. Training Duration
   V. Trainer Name
   */
//


/*
The method filters trainings based on the following criteria:

Date range: If periodFrom and periodTo are provided, it filters trainings within the specified range.
Trainer's first name: If trainerFirstName is provided, it filters trainings where the trainer's first name matches.
Trainer's specialization: If specializationName is provided, it filters trainings by the trainer's specialization.
 */


  @GetMapping("/trainees/{username}/trainings")
  public ResponseEntity<List<TrainingForTraineeResponseDto>> getTraineeTrainingsListByDynamicCriteria(
      @PathVariable String username,
      @RequestParam(required = false) String periodFrom,
      @RequestParam(required = false) String periodTo,
      @RequestParam(required = false) String trainerFirstName,
      @RequestParam(required = false) String specializationName
  ) {
    TraineeTrainingRequestDto traineeTrainingRequestDto = traineeMapper.toDto(
        username, periodFrom, periodTo, trainerFirstName, specializationName
    );
    List<TrainingForTraineeResponseDto> responseDtos = trainingService
        .findTraineeTrainingsWithFilters(traineeTrainingRequestDto);

    return ResponseEntity.ok(responseDtos);
  }
}






