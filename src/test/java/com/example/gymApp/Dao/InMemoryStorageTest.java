package com.example.gymApp.Dao;

import com.example.gymApp.Model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InMemoryStorageTest {

    private InMemoryStorage inMemoryStorage;

    @Mock
    private Resource mockResource;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        inMemoryStorage = new InMemoryStorage();

        // Мокируем Resource, чтобы он возвращал true при вызове метода exists()
        when(mockResource.exists()).thenReturn(true);
    }

    @Test
    public void testLoadDataFromFile() throws Exception {
        String testData = "trainee,1,John,Doe,1990-01-01,1234 Elm Street\n";
        InputStream inputStream = new ByteArrayInputStream(testData.getBytes());

        // Заменяем реальный InputStream на мок
        when(mockResource.getInputStream()).thenReturn(inputStream);

        inMemoryStorage.loadDataFromFile(mockResource);

        // Проверка, что данные загружены корректно
        assertEquals(1, inMemoryStorage.getTraineesMap().size());
        Trainee trainee = inMemoryStorage.getTraineesMap().get(1L);
        assertNotNull(trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Doe", trainee.getLastName());
        assertEquals("1990-01-01", trainee.getDateOfBirth().toString());
        assertEquals("1234 Elm Street", trainee.getAddress());
    }
}
