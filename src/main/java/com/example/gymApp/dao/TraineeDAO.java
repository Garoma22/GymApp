//
//
//package com.example.gymApp.dao;
//
//import com.example.gymApp.model.Trainee;
//import com.example.gymApp.repository.TraineeRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Slf4j
//@Component
//public class TraineeDAO {
//
//  private final TraineeRepository traineeRepository;
//
//  @Autowired
//  public TraineeDAO(TraineeRepository traineeRepository) {
//    this.traineeRepository = traineeRepository;
//  }
//
//  public boolean createTrainee(Trainee trainee) {
//
//    log.info("Added trainee in the DB: " + trainee.toString());
//    Trainee savedTrainee = traineeRepository.save(trainee); // сохраняем объект через репозиторий
//    return savedTrainee.getId() != null;
//  }
//
//  public boolean updateTrainee(Trainee trainee) {
//    if (traineeRepository.existsById(trainee.getId())) { // проверяем, существует ли стажер
//      traineeRepository.save(trainee); // сохраняем изменения
//      return true;
//    }
//    return false;
//  }
//
//  public boolean deleteTrainee(Long id) {
//    if (traineeRepository.existsById(id)) {
//      traineeRepository.deleteById(id);
//      return true;
//    }
//    return false;
//  }
//
//  public Trainee getTraineeById(Long id) {
//    return traineeRepository.findById(id).orElse(null); // возвращаем стажера, если найден
//  }
//
//  public List<Trainee> getAllTrainees() {
//    return traineeRepository.findAll(); // возвращаем всех стажеров
//  }
//}
