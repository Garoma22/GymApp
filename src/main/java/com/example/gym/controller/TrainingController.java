package com.example.gym.controller;

import com.example.gym.dto.training.TrainingInfoResponseDto;
import com.example.gym.dto.training.TrainingRequestDto;
import com.example.gym.dto.training.TrainingResponseDto;
import com.example.gym.repository.TraineeRepository;
import com.example.gym.repository.TrainerRepository;
import com.example.gym.repository.UserRepository;
import com.example.gym.service.TraineeService;
import com.example.gym.service.TrainerService;
import com.example.gym.service.TrainingService;
import com.example.gym.service.TrainingTypeService;
import com.example.gym.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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



  @PostMapping("/trainings")
  public ResponseEntity<TrainingInfoResponseDto> addTraining
      (@RequestBody TrainingRequestDto request) {
    return ResponseEntity.ok(trainingService.getTrainingInfoResponseDto(request));
  }


  @GetMapping("/training-types")
  public ResponseEntity<List<TrainingResponseDto>> getTrainingTypes() {
    log.info("Received request to get all training types");
    List<TrainingResponseDto> list = trainingTypeService.getTrainingTypeList();
    log.info("Successfully retrieved {} training types", list.size());
    return ResponseEntity.ok(list);
  }
}


