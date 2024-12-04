package com.example.gymApp.utills_two;


import com.example.gymApp.model.TrainingType;
import com.example.gymApp.repository.TrainingTypeRepository;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DataInitializerTraining {
  private final TrainingTypeRepository trainingTypeRepository;

  @Autowired
  public DataInitializerTraining(TrainingTypeRepository trainingTypeRepository) {
    this.trainingTypeRepository = trainingTypeRepository;
  }
  @PostConstruct
  public void init() {
    loadTrainingTypes();
  }

  private void loadTrainingTypes() {
    try {
      ClassPathResource resource = new ClassPathResource("training_types.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && !trainingTypeRepository.existsByName(line)) {
          TrainingType trainingType = new TrainingType(line);
          trainingTypeRepository.save(trainingType);
        }
      }
      reader.close();
    } catch (Exception e) {
      System.err.println("Failed to load training types: " + e.getMessage());
    }
  }
}

