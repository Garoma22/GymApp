package com.example.gymApp.utils;


import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


@Slf4j
@Component
public class ConsoleInputHandler {

  private final TraineeService traineeService;
  private final TrainerService trainerService;
  private final TrainingService trainingService;
  private final Scanner scanner;

  @Autowired
  public ConsoleInputHandler(TraineeService traineeService, TrainerService trainerService,
      TrainingService trainingService) {
    this.traineeService = traineeService;
    this.trainerService = trainerService;
    this.trainingService = trainingService;
    this.scanner = new Scanner(System.in);
  }

  public void start() {
    boolean running = true;

    while (running) {
      System.out.println("Choose an operation:");
      System.out.println("1: Create Trainee");
      System.out.println("2: Update Trainee");
      System.out.println("3: Delete Trainee");
      System.out.println("4: Get Trainee by ID");
      System.out.println("5: Get All Trainees");
      System.out.println("6: Create Trainer");
      System.out.println("7: Update Trainer");
      System.out.println("8: Delete Trainer");
      System.out.println("9: Get Trainer by ID");
      System.out.println("10: Get All Trainers");
      System.out.println("11: Create Training");
      System.out.println("12: Get Training by ID");
      System.out.println("13: Get All Trainings");
      System.out.println("0: Exit");



      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume the newline character

      switch (choice) {
        case 1:
          createTrainee();
          break;
        case 2:
          updateTrainee();
          break;
        case 3:
          deleteTrainee();
          break;
        case 4:
          getTraineeById();
          break;
        case 5:
          getAllTrainees();
          break;
        case 6:
          createTrainer();
          break;
        case 7:
          updateTrainer();
          break;
        case 8:
          deleteTrainer();
          break;
        case 9:
          getTrainerById();
          break;
        case 10:
          getAllTrainers();
          break;
        case 11:
          createTraining();
          break;
        case 12:
          getTrainingById();
          break;
        case 13:
          getAllTrainings();
          break;
        case 0:
          running = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }


  private void createTrainee() {
    System.out.println("Enter Trainee id:");
    Long id;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String address;

    try {
      id = Long.valueOf(scanner.nextLine());
      System.out.println("Enter Trainee First Name:");
      firstName = scanner.nextLine();
      System.out.println("Enter Trainee Last Name:");
      lastName = scanner.nextLine();
      System.out.println("Enter Trainee Date of Birth (YYYY-MM-DD):");
      dateOfBirth = LocalDate.parse(scanner.nextLine());
      System.out.println("Enter Trainee Address:");
      address = scanner.nextLine();

      traineeService.createTrainee(id, firstName, lastName, dateOfBirth, address);

      if (traineeService.createTrainee(id, firstName, lastName, dateOfBirth, address)) {
        System.out.println("Trainee created successfully!");
      } else {
        System.out.println("Failed to create Trainee due to validation error.");
      }

    } catch (IllegalArgumentException e) {
      System.out.println("Failed to create Trainee: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("An unexpected error occurred: " + e.getMessage());
    }
  }


  private void updateTrainee() {
    System.out.println("Enter Trainee ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // Consume the newline character

    System.out.println("Enter new First Name:");
    String firstName = scanner.nextLine();
    System.out.println("Enter new Last Name:");
    String lastName = scanner.nextLine();
    System.out.println("Enter new Date of Birth (YYYY-MM-DD):");
    LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
    System.out.println("Enter new Address:");
    String address = scanner.nextLine();

    try {
      traineeService.updateTrainee(id, firstName, lastName, dateOfBirth, address);
      System.out.println("Trainee updated successfully!");
    } catch (IllegalArgumentException e) {
      System.out.println("Failed to update Trainee: " + e.getMessage());
    }
  }

  private void deleteTrainee() {
    System.out.println("Enter Trainee ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // Consume the newline character

    try {
      traineeService.deleteTrainee(id);
      System.out.println("Trainee deleted successfully!");
    } catch (NoSuchElementException e) {
      System.out.println("Failed to delete Trainee: " + e.getMessage());
    }
  }

  private void getTraineeById() {
    System.out.println("Enter Trainee ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // Consume the newline character

    try {
      Trainee trainee = traineeService.getTraineeById(id);
      System.out.println(trainee);
    } catch (NoSuchElementException e) {
      System.out.println("Trainee not found: " + e.getMessage());
    }
  }

  private void getAllTrainees() {
    List<Trainee> trainees = traineeService.getAllTrainees();
    trainees.forEach(System.out::println);
  }


  private void createTrainer() {
    System.out.println("Enter Trainer id:");
    Long id;
    String firstName;
    String lastName;
    String specialization;

    try {
      id = Long.valueOf(scanner.nextLine());
      System.out.println("Enter Trainer First Name:");
      firstName = scanner.nextLine();
      System.out.println("Enter Trainer Last Name:");
      lastName = scanner.nextLine();
      System.out.println("Enter Trainer Specialization:");
      specialization = scanner.nextLine();

      if (trainerService.createTrainer(id, firstName, lastName, specialization)) {
        System.out.println("Trainer created successfully!");
      } else {
        System.out.println("Failed to create Trainer due to validation error.");
      }

    } catch (IllegalArgumentException e) {
      System.out.println("Failed to create Trainer: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("An unexpected error occurred: " + e.getMessage());
    }
  }

  private void updateTrainer() {
    System.out.println("Enter Trainer ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // Consume the newline character

    System.out.println("Enter new First Name:");
    String firstName = scanner.nextLine();
    System.out.println("Enter new Last Name:");
    String lastName = scanner.nextLine();
    System.out.println("Enter new Specialization:");
    String specialization = scanner.nextLine();

    try {
      trainerService.updateTrainer(id, firstName, lastName, specialization);
      System.out.println("Trainer updated successfully!");
    } catch (IllegalArgumentException e) {
      System.out.println("Failed to update Trainer: " + e.getMessage());
    }
  }

  private void deleteTrainer() {
    System.out.println("Enter Trainer ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // Consume the newline character

    try {
      trainerService.deleteTrainer(id);
      System.out.println("Trainer deleted successfully!");
    } catch (NoSuchElementException e) {
      System.out.println("Failed to delete Trainer: " + e.getMessage());
    }
  }

  private void getTrainerById() {
    System.out.println("Enter Trainer ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // Consume the newline character

    try {
      Trainer trainer = trainerService.getTrainerById(id);
      System.out.println(trainer);
    } catch (NoSuchElementException e) {
      System.out.println("Trainer not found: " + e.getMessage());
    }
  }

  private void getAllTrainers() {
    List<Trainer> trainers = trainerService.getAllTrainers();
    trainers.forEach(System.out::println);
  }


  //TRAINING


  private void createTraining() {
    Long id;
    String trainingName;
    String trainingType;
    int duration = 0;
    LocalDate trainingDate;
    Long traineeId;
    Long trainerId;

    try {
      System.out.println("Enter Training id:");
      id = Long.valueOf(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input for Training ID. Please enter a valid number.");
      return;
    }

    System.out.println("Enter Training Name:");
    trainingName = scanner.nextLine();

    System.out.println("Enter Training Type:");
    trainingType = scanner.nextLine();

    try {
      System.out.println("Enter Training Duration in hours:");
      duration = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input for Training Duration. Please enter a valid number.");
      return;
    }

    try {
      System.out.println("Enter Training Date (YYYY-MM-DD):");
      trainingDate = LocalDate.parse(scanner.nextLine());
    } catch (Exception e) {
      System.out.println("Invalid input for Training Date. Please enter a date in the format YYYY-MM-DD.");
      return;
    }

    try {
      System.out.println("Enter Trainee ID:");
      traineeId = Long.valueOf(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input for Trainee ID. Please enter a valid number.");
      return;
    }

    try {
      System.out.println("Enter Trainer ID:");
      trainerId = Long.valueOf(scanner.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input for Trainer ID. Please enter a valid number.");
      return;
    }

    try {
      Trainee trainee = traineeService.getTraineeById(traineeId);
      Trainer trainer = trainerService.getTrainerById(trainerId);

      if (trainingService.createTraining(id, trainee.getId(), trainer.getId(), trainingName,
          trainingType, trainingDate, duration)) {
        System.out.println("Training created successfully!");
      } else {
        System.out.println("Failed to create Training due to validation error.");
      }

    } catch (NoSuchElementException e) {
      System.out.println("Failed to create Training: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("An unexpected error occurred: " + e.getMessage());
    }
  }


  private void getTrainingById() {
    System.out.println("Enter Training ID:");
    Long id = scanner.nextLong();
    scanner.nextLine();

    try {
      Training training = trainingService.getTrainingById(id);
      System.out.println(training);
    } catch (NoSuchElementException e) {
      System.out.println("Training not found: " + e.getMessage());
    }
  }

  private void getAllTrainings() {
    List<Training> trainings = trainingService.getAllTrainings();
    trainings.forEach(System.out::println);
  }
}

