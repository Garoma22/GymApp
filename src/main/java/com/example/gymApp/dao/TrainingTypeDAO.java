//package com.example.gymApp.dao;
//
//
//import com.example.gymApp.model.TrainingType;
//import com.example.gymApp.repository.TrainingTypeRepository;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TrainingTypeDAO {
//
//  @Autowired
//  private TrainingTypeRepository trainingTypeRepository;
//
//  // Получение TrainingType по имени
//  public TrainingType findByTypeName(String typeName) {
//    return trainingTypeRepository.findByTypeName(typeName);
//  }
//
//  // Сохранение нового TrainingType
//  public TrainingType save(TrainingType trainingType) {
//    return trainingTypeRepository.save(trainingType);
//  }
//
//  // Получение всех типов тренировок (если необходимо)
//  public List<TrainingType> findAll() {
//    return trainingTypeRepository.findAll();
//  }
//}
