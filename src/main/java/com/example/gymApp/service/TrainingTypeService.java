//package com.example.gymApp.service;
//
//import com.example.gymApp.dao.TrainingTypeDAO;
//import com.example.gymApp.model.TrainingType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TrainingTypeService {
//
//  @Autowired
//  private TrainingTypeDAO trainingTypeDao;
//
//  // Получение TrainingType по имени
//  public TrainingType getTrainingTypeByName(String typeName) {
//    return trainingTypeDao.findByTypeName(typeName);
//  }
//
//  // Сохранение нового TrainingType
//  public TrainingType save(TrainingType trainingType) {
//    return trainingTypeDao.save(trainingType);
//  }
//}
