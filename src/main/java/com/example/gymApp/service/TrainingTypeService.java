package com.example.gymApp.service;

import com.example.gymApp.model.TrainingType;
import com.example.gymApp.repository.TrainingTypeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class TrainingTypeService {

  TrainingTypeRepository trainingTypeRepository;

  public List<TrainingType> getTrainingTypeList(){
    log.info("Fetching all training types from repository");
    List<TrainingType>trainingTypeList = trainingTypeRepository.findAll();
    log.info("Fetched {} training types", trainingTypeList.size());
    return trainingTypeList;
  }
}
