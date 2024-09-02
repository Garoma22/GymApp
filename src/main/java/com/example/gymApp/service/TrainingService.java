package com.example.gymApp.service;

import com.example.gymApp.dao.TrainingDAO;
import com.example.gymApp.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

  private TrainingDAO trainingDAO;

  @Autowired
  public void setTrainingDAO(TrainingDAO trainingDAO) {
    this.trainingDAO = trainingDAO;
  }


  public void createTraining(Training training) {
    trainingDAO.createTraining(training);
  }

  public void updateTraining(Training training) {
    trainingDAO.updateTraining(training);
  }

  public void deleteTraining(Long id) {
    trainingDAO.deleteTraining(id);
  }

  public Training getTrainingById(Long id) {
    return trainingDAO.getTrainingById(id);
  }

  public List<Training> getAllTrainings() {
    return trainingDAO.getAllTrainings();
  }
}
