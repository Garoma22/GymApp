//package com.example.gymApp.utils;
//
//import com.example.gymApp.model.Trainee;
//import com.example.gymApp.model.Trainer;
//import com.example.gymApp.service.ProfileService;
//import jakarta.annotation.PostConstruct;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Data
//@Component
//public class UserInitializationPostProcessor implements BeanPostProcessor {
//
//  @Autowired
//  private DataInitializer dataInitializer;
//
//  private List<TraineeData> traineeData = new ArrayList<>();
//  private List<TrainerData> trainerData = new ArrayList<>();
//
//  @PostConstruct
//  public void init() {
//    log.info("DataInitializer init method called.");
//    traineeData = dataInitializer.getTraineeDataList();
//    trainerData = dataInitializer.getTrainerDataList();
//  }
//
//
//  @Override
//  public Object postProcessBeforeInitialization(Object bean, String beanName)
//      throws BeansException {
//    if (bean instanceof Trainee) {
//      Trainee trainee = (Trainee) bean;
//      for (TraineeData data : traineeData) {
//        if (trainee.getId() == null || trainee.getId().equals(data.getId())) {
//          updateTrainee(trainee, data);
//        }
//      }
//    } else if (bean instanceof Trainer) {
//      Trainer trainer = (Trainer) bean;
//      for (TrainerData data : trainerData) {
//        if (trainer.getId() == null || trainer.getId().equals(data.getId())) {
//          updateTrainer(trainer, data);
//        }
//      }
//    }
//    return bean;
//  }
//
//  private void updateTrainee(Trainee trainee, TraineeData data) {
//    trainee.setId(data.getId());
//    trainee.setFirstName(data.getFirstName());
//    trainee.setLastName(data.getLastName());
//    trainee.setDateOfBirth(LocalDate.parse(data.getDateOfBirth()));
//    trainee.setAddress(data.getAddress());
//    trainee.setUsername(ProfileService.generateUsername(data.getFirstName(), data.getLastName()));
//    trainee.setPassword(ProfileService.generateRandomPassword());
//    trainee.setActive(true);
//  }
//
//  private void updateTrainer(Trainer trainer, TrainerData data) {
//    trainer.setId(data.getId());
//    trainer.setFirstName(data.getFirstName());
//    trainer.setLastName(data.getLastName());
//    trainer.setSpecialization(data.getSpecialization());
//    trainer.setUsername(ProfileService.generateUsername(data.getFirstName(), data.getLastName()));
//    trainer.setPassword(ProfileService.generateRandomPassword());
//    trainer.setActive(true);
//  }
//}
