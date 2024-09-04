package com.example.gymApp.Dao;



import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Model.Trainer;
import com.example.gymApp.Model.Training;
import com.example.gymApp.Model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;





@Slf4j
@Component
public class InMemoryStorage {

    @Getter
    private Map<Long, Trainee> traineesMap = new HashMap<>();
    @Getter
    private Map<Long, Trainer> trainersMap = new HashMap<>();
    @Getter
    private Map<Long, Training> trainingsMap = new HashMap<>();

    @Value("${data.file}")
    private Resource dataFile;

    @PostConstruct
    public void init() throws IOException {
        loadDataFromFile();
    }

    private void loadDataFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataFile.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Пример парсинга строки данных и загрузки в хранилище
                String[] parts = line.split(",");
                if (parts[0].startsWith("trainee")) {
                    // Парсим строку для Trainee
                    Long id = Long.parseLong(parts[1]);
                    String firstName = parts[2];
                    String lastName = parts[3];
                    LocalDate dateOfBirth = LocalDate.parse(parts[4]);
                    String address = parts[5];

                    Trainee trainee = new Trainee(id, firstName, lastName, firstName + "." + lastName, "password", true, dateOfBirth, address);
                    traineesMap.put(id, trainee);
                    log.info("Parsing line: {}", line);
                    log.info("Current traineesMap: {}", traineesMap);

                } else if (parts[0].startsWith("trainer")) {
                    // Парсим строку для Trainer
                    Long id = Long.parseLong(parts[1]);
                    String firstName = parts[2];
                    String lastName = parts[3];
                    String specialization = parts[4];

                    Trainer trainer = new Trainer(id, firstName, lastName, firstName + "." + lastName, "password", true, specialization);
                    trainersMap.put(id, trainer);
                    log.info("Current trainersMap : {}", trainersMap);

                } else if (parts[0].startsWith("training")) {
                    // Парсим строку для Training
                    Long id = Long.parseLong(parts[1]);
                    Long traineeId = Long.parseLong(parts[2]);
                    Long trainerId = Long.parseLong(parts[3]);
                    String trainingName = parts[4];
                    String trainingType = parts[5];
                    LocalDateTime trainingDate = LocalDateTime.parse(parts[6]);
                    Integer trainingDuration = Integer.parseInt(parts[7]);

                    Trainee trainee = traineesMap.get(traineeId);
                    Trainer trainer = trainersMap.get(trainerId);

                    if (trainee != null && trainer != null) {
                        Training training = new Training(id, trainee, trainer, trainingName, trainingType, trainingDate, trainingDuration);
                        trainingsMap.put(id, training);
                        log.info("Current trainingsMap : {}", trainingsMap);
                    } else {
                        log.warn("Trainee or Trainer not found for training: {}", id);
                    }
                }
            }
        }
    }

}