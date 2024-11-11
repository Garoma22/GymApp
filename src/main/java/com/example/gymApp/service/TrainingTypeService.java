package com.example.gymApp.service;

import com.example.gymApp.dto.training.TrainingResponseDto;
import com.example.gymApp.dto.trainingType.TrainingToTrainingDtoMapper;
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
  TrainingToTrainingDtoMapper trainingToTrainingDtoMapper;

  public List<TrainingResponseDto> getTrainingTypeList() {
    List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
    log.info("Fetched TrainingTypes from database: {}", trainingTypeList);
    return trainingToTrainingDtoMapper.toTrainngDtoList(trainingTypeList);
  }

}
