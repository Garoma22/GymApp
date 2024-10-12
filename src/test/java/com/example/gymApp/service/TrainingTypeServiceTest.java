package com.example.gymApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.example.gymApp.model.TrainingType;
import com.example.gymApp.repository.TrainingTypeRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceTest {

  @Mock
  private TrainingTypeRepository trainingTypeRepository;

  @InjectMocks
  private TrainingTypeService trainingTypeService;

  @Test
  void testGetTrainingTypeList() {

    List<TrainingType> mockTrainingTypes = List.of(
        new TrainingType("Cardio"),
        new TrainingType("Strength")
    );

    when(trainingTypeRepository.findAll()).thenReturn(mockTrainingTypes);

    List<TrainingType> result = trainingTypeService.getTrainingTypeList();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Cardio", result.get(0).getName());
    assertEquals("Strength", result.get(1).getName());

    verify(trainingTypeRepository, times(1)).findAll();
  }
}
