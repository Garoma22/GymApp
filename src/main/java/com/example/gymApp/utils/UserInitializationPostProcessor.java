//package com.example.gymApp.utils;
//
//import com.example.gymApp.model.User;
//import com.example.gymApp.model.Trainee;
//import com.example.gymApp.model.Trainer;
//import com.example.gymApp.repository.TraineeRepository;
//import com.example.gymApp.repository.TrainerRepository;
//import com.example.gymApp.repository.UserRepository;
//import com.example.gymApp.service.ProfileService;
//import jakarta.annotation.PostConstruct;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Slf4j
//@Data
//@Component
//public class UserInitializationPostProcessor {
//
//  @Autowired
//  private DataInitializer dataInitializer;
//
//  @Autowired
//  private TraineeRepository traineeRepository;
//
//  @Autowired
//  private TrainerRepository trainerRepository;
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @PostConstruct
//  public void init() {
//    log.info("DataInitializer init method called.");
//
//    // Получаем списки данных стажеров и тренеров
//    List<TraineeData> traineeDataList = dataInitializer.getTraineeDataList();
//    List<TrainerData> trainerDataList = dataInitializer.getTrainerDataList();
//
//    // Обрабатываем и сохраняем стажеров
//    for (TraineeData data : traineeDataList) {
//      Trainee trainee = convertToTrainee(data);
//      log.info("Saving trainee: " + trainee);
//      traineeRepository.save(trainee);
//    }
//
////     Обрабатываем и сохраняем тренеров
//     for (TrainerData data : trainerDataList) {
//    Trainer trainer = convertToTrainer(data);
//      log.info("Saving trainer: " + trainer);
//      trainerRepository.save(trainer);
//    }
//  }
//
//  private Trainee convertToTrainee(TraineeData data) {
//    // Создаем объект User
//    User user = new User();
//    user.setFirstName(data.getFirstName());
//    user.setLastName(data.getLastName());
//    user.setUsername(ProfileService.generateUsername(data.getFirstName(), data.getLastName()));
//    user.setPassword(ProfileService.generateRandomPassword());
//    user.setActive(true);
//
//    // Сохраняем User в базу данных
//    user = userRepository.save(user);
//    log.info("Saved User: " + user);
//
//    // Создаем объект Trainee и связываем его с User
//    Trainee trainee = new Trainee();
//
//    trainee.setUser(user); // Связываем Trainee с User
//    trainee.setDateOfBirth(LocalDate.parse(data.getDateOfBirth()));
//    trainee.setAddress(data.getAddress());
//    log.info("Created Trainee: " + trainee);
//    traineeRepository.save(trainee);
//
//    return trainee;
//  }
//
//  private Trainer convertToTrainer(TrainerData data) {
//    // Создаем объект User
//    User user = new User();
//    user.setFirstName(data.getFirstName());
//    user.setLastName(data.getLastName());
//    user.setUsername(ProfileService.generateUsername(data.getFirstName(), data.getLastName()));
//    user.setPassword(ProfileService.generateRandomPassword());
//    user.setActive(true);
//
//    // Создаем объект Trainer и связываем его с User
//    Trainer trainer = new Trainer();
//    trainer.setUser(user);
//    trainer.setSpecialization(data.getSpecialization());
//
//    log.info("Created Trainer: " + trainer);
//    return trainer;
//  }
//
//
//}
