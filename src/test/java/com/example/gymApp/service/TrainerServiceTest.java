//package com.example.gymApp.service;
//
//import com.example.gymApp.dao.TrainerDAO;
//import com.example.gymApp.model.Trainer;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.NoSuchElementException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//  public class TrainerServiceTest {
//
//    @Mock
//    private TrainerDAO trainerDAO;
//
//    @InjectMocks
//    private TrainerService trainerService;
//
//    @BeforeEach
//    void setUp() {
//      MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateTrainerSuccess() {
//      when(trainerDAO.createTrainer(any(Trainer.class))).thenReturn(true);
//
//      boolean result = trainerService.createTrainer(1L, "John", "Doe", "Strength Training");
//
//      assertTrue(result);
//
//    }
//
//    @Test
//    public void testCreateTrainerValidationError() {
//      boolean emptyName = trainerService.createTrainer(1L, "", "Doe", "Strength Training");
//
//      boolean emptyLastName = trainerService.createTrainer(1L, "John", "", "Strength Training");
//
//      boolean emptySpecialization = trainerService.createTrainer(1L, "John", "Doe", "");
//
//      assertFalse(emptyName);
//      assertFalse(emptyLastName);
//      assertFalse(emptySpecialization);
//
//      verify(trainerDAO, never()).createTrainer(any(Trainer.class));
//    }
//
//    @Test
//    public void testUpdateTrainerSuccess() {
//      Trainer existingTrainer = new Trainer();
//      existingTrainer.setId(1L);
//      existingTrainer.setFirstName("John");
//      existingTrainer.setLastName("Doe");
//      existingTrainer.setSpecialization("Strength Training");
//
//
//
//      when(trainerDAO.getTrainerById(1L)).thenReturn(existingTrainer);
//      when(trainerDAO.updateTrainer(any(Trainer.class))).thenReturn(true);
//
//      boolean result = trainerService.updateTrainer(1L, "John", "Petrov", "Cardio Training");
//
//      assertTrue(result);
//    }
//
//    @Test
//    public void testUpdateTrainerNotFound() {
//      when(trainerDAO.getTrainerById(1L)).thenReturn(null);
//
//      assertThrows(NoSuchElementException.class, () -> {
//        trainerService.updateTrainer(1L, "John", "Smith", "Cardio Training");
//      });
//
//      verify(trainerDAO, never()).updateTrainer(any(Trainer.class));
//    }
//
//    @Test
//    public void testDeleteTrainerSuccess() {
//      when(trainerDAO.deleteTrainer(1L)).thenReturn(true);
//
//      boolean result = trainerService.deleteTrainer(1L);
//
//      assertTrue(result);
//
//    }
//
//    @Test
//    public void testDeleteTrainerNotFound() {
//      when(trainerDAO.deleteTrainer(1L)).thenReturn(false);
//
//      boolean result = trainerService.deleteTrainer(1L);
//
//      assertFalse(result);
//    }
//
//    @Test
//    public void testGetTrainerByIdSuccess() {
//      Trainer trainer = new Trainer();
//      trainer.setId(1L);
//
//      when(trainerDAO.getTrainerById(1L)).thenReturn(trainer);
//
//      Trainer result = trainerService.getTrainerById(1L);
//
//      assertNotNull(result);
//      assertEquals(1L, result.getId());
//
//    }
//
//    @Test
//    public void testGetTrainerByIdNotFound() {
//      when(trainerDAO.getTrainerById(1L)).thenReturn(null);
//
//      assertThrows(NoSuchElementException.class, () -> {
//        trainerService.getTrainerById(1L);
//      });
//
//    }
//
//    @Test
//    public void testGetAllTrainersSuccess() {
//      List<Trainer> trainers = List.of(new Trainer(), new Trainer());
//
//      when(trainerDAO.getAllTrainers()).thenReturn(trainers);
//
//      List<Trainer> result = trainerService.getAllTrainers();
//
//      assertNotNull(result);
//      assertEquals(2, result.size());
//
//    }
//  }
//
//
//
