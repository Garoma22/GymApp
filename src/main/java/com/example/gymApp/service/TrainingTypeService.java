package com.example.gymApp.service;

import com.example.gymApp.model.TrainingType;
import com.example.gymApp.repository.TrainingTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class TrainingTypeService {

  TrainingTypeRepository trainingTypeRepository;


  public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {
    this.trainingTypeRepository = trainingTypeRepository;
  }



  public List<TrainingType> getTrainingTypeList(){
    return trainingTypeRepository.findAll();
  }



}
