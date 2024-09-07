//package com.example.gymApp.dao;
//
//import com.example.gymApp.model.Trainee;
//import com.example.gymApp.model.Trainer;
//import com.example.gymApp.model.Training;
//import com.example.gymApp.service.ProfileService;
//import com.example.gymApp.utils.TraineeData;
//import com.example.gymApp.utils.TrainerData;
//import com.example.gymApp.utils.UserInitializationPostProcessor;
//import jakarta.annotation.PostConstruct;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Data
//@Slf4j
//@Component
//public class InMemoryStorage {
//
//  @Autowired
//  private UserInitializationPostProcessor userInitializationPostProcessor;
//
//  private Map<Long, Trainee> traineesMap = new HashMap<>();
//  private Map<Long, Trainer> trainersMap = new HashMap<>();
//  private Map<Long, Training> trainingsMap = new HashMap<>();
//
//  @PostConstruct
//  public void init() {
//    log.info("Initializing InMemoryStorage...");
//
//    if (userInitializationPostProcessor == null) {
//      log.error("UserInitializationPostProcessor is null!");
//      return;
//    }
//
//    traineesMap = userInitializationPostProcessor.getTraineeData()
//        .stream()
//        .map(this::convertToTrainee)
//        .collect(Collectors.toMap(Trainee::getId, Function.identity()));
//
//    trainersMap = userInitializationPostProcessor.getTrainerData()
//        .stream()
//        .map(this::convertToTrainer)
//        .collect(Collectors.toMap(Trainer::getId, Function.identity()));
//
//    log.info("Trainees: {}", traineesMap);
//    log.info("Trainers: {}", trainersMap);
//
//    createAndAddTraining();
//  }
//
//  private Trainee convertToTrainee(TraineeData data) {
//    Trainee trainee = new Trainee();
//    trainee.setId(data.getId());
//    trainee.setFirstName(data.getFirstName());
//    trainee.setLastName(data.getLastName());
//    trainee.setDateOfBirth(LocalDate.parse(data.getDateOfBirth()));
//    trainee.setAddress(data.getAddress());
//    trainee.setUsername(ProfileService.generateUsername(data.getFirstName(), data.getLastName()));
//    trainee.setPassword(ProfileService.generateRandomPassword());
//    trainee.setActive(true);
//    return trainee;
//  }
//
//  private Trainer convertToTrainer(TrainerData data) {
//    Trainer trainer = new Trainer();
//    trainer.setId(data.getId());
//    trainer.setFirstName(data.getFirstName());
//    trainer.setLastName(data.getLastName());
//    trainer.setSpecialization(data.getSpecialization());
//    trainer.setUsername(ProfileService.generateUsername(data.getFirstName(), data.getLastName()));
//    trainer.setPassword(ProfileService.generateRandomPassword());
//    trainer.setActive(true);
//    return trainer;
//  }
//
//  private void createAndAddTraining() {
//    if (!traineesMap.isEmpty() && !trainersMap.isEmpty()) {
//      Long trainingId = 1L;
//      Trainee trainee = traineesMap.values().iterator().next();
//      Trainer trainer = trainersMap.values().iterator().next();
//
//      // Создаем объект Training
//      Training training = new Training();
//      training.setId(trainingId);
//      training.setTrainee(trainee);
//      training.setTrainer(trainer);
//      training.setTrainingName("Simple training");
//      training.setTrainingType("Simple training");
//      training.setTrainingDate(LocalDate.now());
//      training.setTrainingDuration(
//          60);
//
//      trainingsMap.put(trainingId, training);
//      log.info("Trainings: {}", trainingsMap);
//    } else {
//      log.warn("No trainees or trainers available to create a training.");
//    }
//  }
//}
//
//
//
