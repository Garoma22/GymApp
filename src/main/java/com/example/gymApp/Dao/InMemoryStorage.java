package com.example.gymApp.Dao;



import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Model.Trainer;
import com.example.gymApp.Model.Training;
import com.example.gymApp.Service.ProfileService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;




@Data
@Slf4j
@Component
public class InMemoryStorage {

    @Getter
    private Map<Long, Trainee> traineesMap = new HashMap<>();
    @Getter
    private Map<Long, Trainer> trainersMap = new HashMap<>();
    @Getter
    private Map<Long, Training> trainingsMap = new HashMap<>();


    @PostConstruct
    public void init() throws IOException {
        Resource resource = new ClassPathResource("data.txt");
        if (!resource.exists()) {
            log.error("File not found!");
            return;
        }

        loadDataFromFile(resource);

    }



    public void loadDataFromFile(Resource resource) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].startsWith("trainee")) {
                    Long id = Long.parseLong(parts[1]);
                    String firstName = parts[2];
                    String lastName = parts[3];
                    LocalDate dateOfBirth = LocalDate.parse(parts[4]);
                    String address = parts[5];

                    Trainee trainee = new Trainee(id, firstName, lastName,
                         ProfileService.generateUsername(firstName,lastName),
                          ProfileService.generateRandomPassword(), true, dateOfBirth, address);
                    traineesMap.put(id, trainee);
//                    System.out.println(getTraineesMap());
                    log.info("Parsing line: {}", line);
                    log.info("Current traineesMap: {}", traineesMap);

                } else if (parts[0].startsWith("trainer")) {
                    Long id = Long.parseLong(parts[1]);
                    String firstName = parts[2];
                    String lastName = parts[3];
                    String specialization = parts[4];

                    Trainer trainer = new Trainer(id, firstName, lastName, ProfileService.generateUsername(firstName,lastName), ProfileService.generateRandomPassword(), true, specialization);
                    trainersMap.put(id, trainer);
                    log.info("Current trainersMap : {}", trainersMap);
                   System.out.println(trainersMap);

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

                       System.out.println(trainingsMap);
                    } else {
                        log.warn("Trainee or Trainer not found for training: {}", id);
                    }
                }
            }
        }
    }
}
