package com.example.gymApp.service;

import com.example.gymApp.model.TrainingType;
import com.example.gymApp.repository.TrainingTypeRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TrainingTypeService {

  TrainingTypeRepository trainingTypeRepository;


  public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {
    this.trainingTypeRepository = trainingTypeRepository;
  }



  public List<TrainingType> getTrainingTypeList(){

    log.info("Fetching all training types from repository");

    List<TrainingType>trainingTypeList = trainingTypeRepository.findAll();

    log.info("Fetched {} training types", trainingTypeList.size());
    return trainingTypeList;

  }



}
