package com.example.gymApp.utils;

import com.example.gymApp.service.Facade;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInputHandler {

  private final Facade facade;
  private final Scanner scanner;

  @Autowired
  public ConsoleInputHandler(Facade facade) {
    this.facade = facade;
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
      System.out.println("12: Update Training");
      System.out.println("13: Delete Training");
      System.out.println("14: Get Training by ID");
      System.out.println("15: Get All Trainings");
      System.out.println("0: Exit");

      int choice = scanner.nextInt();
      scanner.nextLine();

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
          updateTraining();
          break;
        case 13:
          deleteTraining();
          break;
        case 14:
          getTrainingById();
          break;
        case 15:
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
    Trainee trainee = new Trainee();


    boolean validId = false;
    while (!validId) {
      try {
        System.out.println("Enter Trainee id");
        trainee.setId(Long.valueOf(scanner.nextLine()));
        validId = true;
      } catch (NumberFormatException e) {
        System.out.println("Invalid ID format. Please enter a valid numeric ID.");
      }
    }

    System.out.println("Enter Trainee First Name:");
    trainee.setFirstName(scanner.nextLine());
    System.out.println("Enter Trainee Last Name:");
    trainee.setLastName(scanner.nextLine());

    boolean validDate = false;
    while (!validDate) {
      try {
        System.out.println("Enter Trainee Date of Birth (YYYY-MM-DD):");
        trainee.setDateOfBirth(LocalDate.parse(scanner.nextLine()));
        validDate = true;
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
      }
    }

    System.out.println("Enter Trainee Address:");
    trainee.setAddress(scanner.nextLine());

    facade.createTrainee(trainee);
  }


  private void updateTrainee() {
    System.out.println("Enter Trainee ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    Trainee trainee = facade.getTraineeById(id);
    if (trainee == null) {
      System.out.println("Trainee not found.");
      return;
    }

    System.out.println("Enter new First Name (current: " + trainee.getFirstName() + "):");
    trainee.setFirstName(scanner.nextLine());
    System.out.println("Enter new Last Name (current: " + trainee.getLastName() + "):");
    trainee.setLastName(scanner.nextLine());
    System.out.println("Enter new Date of Birth (current: " + trainee.getDateOfBirth() + "):");

    //todo separate util method2

    boolean validDate = false;
    while (!validDate) {
      try {
        System.out.println("Enter Trainee Date of Birth (YYYY-MM-DD):");
        trainee.setDateOfBirth(LocalDate.parse(scanner.nextLine()));
        validDate = true;
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
      }
    }

    System.out.println("Enter new Address (current: " + trainee.getAddress() + "):");
    trainee.setAddress(scanner.nextLine());

    facade.updateTrainee(trainee);
  }

  private void deleteTrainee() {
    System.out.println("Enter Trainee ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    facade.deleteTrainee(id);
  }

  private void getTraineeById() {
    System.out.println("Enter Trainee ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    Trainee trainee = facade.getTraineeById(id);
    if (trainee != null) {
      System.out.println(trainee);
    } else {
      System.out.println("Trainee not found.");
    }
  }

  private void getAllTrainees() {
    List<Trainee> trainees = facade.getAllTrainees();
    trainees.forEach(System.out::println);
  }

  private void createTrainer() {
    Trainer trainer = new Trainer();
    System.out.println("Enter Trainer Id: ");
    trainer.setId(Long.valueOf(scanner.nextLine()));
    System.out.println("Enter Trainer First Name : ");
    trainer.setFirstName(scanner.nextLine());
    System.out.println("Enter Trainer Last Name: ");
    trainer.setLastName(scanner.nextLine());
    System.out.println("Enter Trainer Specialization: ");
    trainer.setSpecialization(scanner.nextLine());

    facade.createTrainer(trainer);
  }

  private void updateTrainer() {
    System.out.println("Enter Trainer ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    Trainer trainer = facade.getTrainerById(id);
    if (trainer == null) {
      System.out.println("Trainer not found.");
      return;
    }

    System.out.println("Enter new First Name (current: " + trainer.getFirstName() + "):");
    trainer.setFirstName(scanner.nextLine());
    System.out.println("Enter new Last Name (current: " + trainer.getLastName() + "):");
    trainer.setLastName(scanner.nextLine());
    System.out.println("Enter new Specialization (current: " + trainer.getSpecialization() + "):");
    trainer.setSpecialization(scanner.nextLine());

    facade.updateTrainer(trainer);
  }

  private void deleteTrainer() {
    System.out.println("Enter Trainer ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    facade.deleteTrainer(id);
  }

  private void getTrainerById() {
    System.out.println("Enter Trainer ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    Trainer trainer = facade.getTrainerById(id);
    if (trainer != null) {
      System.out.println(trainer);
    } else {
      System.out.println("Trainer not found.");
    }
  }

  private void getAllTrainers() {
    List<Trainer> trainers = facade.getAllTrainers();
    trainers.forEach(System.out::println);
  }

  private void createTraining() {
    Training training = new Training();
    System.out.println("Enter training id");
    training.setId(Long.valueOf(scanner.nextLine()));
    System.out.println("Enter Training Name:");
    training.setTrainingName(scanner.nextLine());
    System.out.println("Enter Training Type:");
    training.setTrainingType(scanner.nextLine());

    // Assume traineeId and trainerId are known
    System.out.println("Enter Trainee ID:");
    Long traineeId = scanner.nextLong();
    System.out.println("Enter Trainer ID:");
    Long trainerId = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    Trainee trainee = facade.getTraineeById(traineeId);
    Trainer trainer = facade.getTrainerById(trainerId);
    training.setTrainee(trainee);
    training.setTrainer(trainer);

    facade.createTraining(training);
  }

  private void updateTraining() {
    System.out.println("Enter Training ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    Training training = facade.getTrainingById(id);
    if (training == null) {
      System.out.println("Training not found.");
      return;
    }

    System.out.println("Enter new Training Name (current: " + training.getTrainingName() + "):");
    training.setTrainingName(scanner.nextLine());
    System.out.println("Enter new Training Type (current: " + training.getTrainingType() + "):");
    training.setTrainingType(scanner.nextLine());

    facade.updateTraining(training);
  }

  private void deleteTraining() {
    System.out.println("Enter Training ID:");
    Long id = scanner.nextLong();
    scanner.nextLine();

    facade.deleteTraining(id);
  }

  private void getTrainingById() {
    System.out.println("Enter Training ID:");
    Long id = scanner.nextLong();
    scanner.nextLine(); // consume the newline

    Training training = facade.getTrainingById(id);
    if (training != null) {
      System.out.println(training);
    } else {
      System.out.println("Training not found.");
    }
  }

  private void getAllTrainings() {
    List<Training> trainings = facade.getAllTrainings();
    trainings.forEach(System.out::println);
  }
}



