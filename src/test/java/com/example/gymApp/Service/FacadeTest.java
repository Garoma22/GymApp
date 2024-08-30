package com.example.gymApp.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.*;

public class FacadeTest {

    @InjectMocks
    private Facade facade;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrainees() {
        facade.getAllTrainees();
        verify(traineeService, times(1)).getAllTrainees();
    }

    @Test
    void testGetAllTrainers() {
        facade.getAllTrainers();
        verify(trainerService, times(1)).getAllTrainers();
    }

    @Test
    void testGetAllTrainings() {
        facade.getAllTrainings();
        verify(trainingService, times(1)).getAllTrainings();
    }


}
