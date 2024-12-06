package com.example.gym.service;

import com.example.gym.dto.training.TrainingResponseDto;
import com.example.gym.dto.trainingtype.TrainingToTrainingDtoMapper;
import com.example.gym.model.TrainingType;
import com.example.gym.repository.TrainingTypeRepository;
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
