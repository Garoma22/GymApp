package com.example.gymApp.utils;

import jakarta.annotation.PostConstruct;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Data
@Slf4j
@Component
public class DataInitializer {


  private List<TraineeData> traineeDataList = new ArrayList<>();
  private List<TrainerData> trainerDataList = new ArrayList<>();

  @PostConstruct
  public void init() throws IOException {
    Resource resource = new ClassPathResource("data.txt");
    if (!resource.exists()) {
      throw new FileNotFoundException("Data file not found!");
    }

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(resource.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts[0].equals("trainee")) {
          TraineeData traineeData = new TraineeData(
              Long.parseLong(parts[1]),
              parts[2],
              parts[3],
              parts[4],
              parts[5]
          );
          traineeDataList.add(traineeData);
          log.info("Created traineeDataList : " + traineeDataList);
        } else if (parts[0].equals("trainer")) {
          TrainerData trainerData = new TrainerData(
              Long.parseLong(parts[1]),
              parts[2],
              parts[3],
              parts[4]
          );
          trainerDataList.add(trainerData);
        }
      }
    }
  }
}

