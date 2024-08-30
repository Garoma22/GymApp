package com.example.gymApp.Dao;

import com.example.gymApp.Model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeDAOTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TraineeDAO traineeDAO;

    private Map<Long, Trainee> traineesMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        traineesMap = spy(new HashMap<>()); // Используем spy, чтобы можно было проверять вызовы методов
        when(storage.getTraineesMap()).thenReturn(traineesMap);
    }

    @Test
    void testCreateTraineeWithCustomUsernameAndPass() {

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("John");
        trainee.setLastName("Smith");


        traineeDAO.createTrainee(trainee);


        assertNotNull(trainee.getUsername(), "Username should not be null");
        assertTrue(trainee.getUsername().startsWith("John.Smith"), "Username should start with 'John.Smith'");
        assertNotNull(trainee.getPassword(), "Password should not be null");
        assertEquals(10, trainee.getPassword().length(), "Password should be 10 characters long");


        verify(traineesMap).put(trainee.getId(), trainee); // Проверяем, что trainee был добавлен в traineesMap
    }

    @Test
    void testCreateTrainee() {

        Trainee trainee = new Trainee(1L, "John", "Doe", "johndoe", "password", true, LocalDate.of(1990, 1, 1), "1234 Elm Street");

        traineeDAO.createTrainee(trainee);


        assertEquals(1, traineesMap.size());
        assertEquals(trainee, traineesMap.get(1L));
    }

    @Test
    void testUpdateTrainee_Success() {

        Trainee trainee = new Trainee(1L, "John", "Doe", "johndoe", "password", true, LocalDate.of(1990, 1, 1), "1234 Elm Street");
        traineesMap.put(1L, trainee);

        Trainee updatedTrainee = new Trainee(1L, "John", "Smith", "johnsmith", "newpassword", true, LocalDate.of(1990, 1, 1), "5678 Oak Street");


        boolean updated = traineeDAO.updateTrainee(updatedTrainee);


        assertTrue(updated);
        assertEquals("Smith", traineesMap.get(1L).getLastName());
        assertEquals("5678 Oak Street", traineesMap.get(1L).getAddress());
    }

    @Test
    void testUpdateTrainee_Failure() {

        Trainee trainee = new Trainee(1L, "John", "Doe", "johndoe", "password", true, LocalDate.of(1990, 1, 1), "1234 Elm Street");
        traineesMap.put(1L, trainee);

        Trainee nonExistentTrainee = new Trainee(2L, "Jane", "Doe", "janedoe", "password", true, LocalDate.of(1992, 2, 2), "5678 Oak Street");


        boolean updated = traineeDAO.updateTrainee(nonExistentTrainee);


        assertFalse(updated);
    }

    @Test
    void testDeleteTrainee_Success() {

        Trainee trainee = new Trainee(1L, "John", "Doe", "johndoe", "password", true, LocalDate.of(1990, 1, 1), "1234 Elm Street");
        traineesMap.put(1L, trainee);


        boolean deleted = traineeDAO.deleteTrainee(1L);


        assertTrue(deleted);
        assertEquals(0, traineesMap.size());
    }

    @Test
    void testDeleteTrainee_Failure() {

        Trainee trainee = new Trainee(1L, "John", "Doe", "johndoe", "password", true, LocalDate.of(1990, 1, 1), "1234 Elm Street");
        traineesMap.put(1L, trainee);

        boolean deleted = traineeDAO.deleteTrainee(2L);


        assertFalse(deleted);
        assertEquals(1, traineesMap.size());
    }

    @Test
    void testGetTraineeById() {

        Trainee trainee = new Trainee(1L, "John", "Doe", "johndoe", "password", true, LocalDate.of(1990, 1, 1), "1234 Elm Street");
        traineesMap.put(1L, trainee);


        Trainee foundTrainee = traineeDAO.getTraineeById(1L);


        assertEquals(trainee, foundTrainee);
    }

    @Test
    void testGetAllTrainees() {

        Trainee trainee1 = new Trainee(1L, "John", "Doe", "johndoe", "password", true, LocalDate.of(1990, 1, 1), "1234 Elm Street");
        Trainee trainee2 = new Trainee(2L, "Jane", "Doe", "janedoe", "password", true, LocalDate.of(1992, 2, 2), "5678 Oak Street");

        traineesMap.put(1L, trainee1);
        traineesMap.put(2L, trainee2);


        List<Trainee> trainees = traineeDAO.getAllTrainees();


        assertEquals(2, trainees.size());
        assertTrue(trainees.contains(trainee1));
        assertTrue(trainees.contains(trainee2));
    }
}

