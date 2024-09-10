//package com.example.gymApp.utils;
//
//
//import com.example.gymApp.model.Trainee;
//import com.example.gymApp.model.Trainer;
//import com.example.gymApp.model.Training;
//import com.example.gymApp.service.TraineeService;
//import com.example.gymApp.service.TrainerService;
//import com.example.gymApp.service.TrainingService;
//import java.time.LocalDateTime;
//import java.util.NoSuchElementException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Scanner;
//
//
//@Slf4j
//@Component
//public class ConsoleInputHandler {
//
//  private final TraineeService traineeService;
//  private final TrainerService trainerService;
//  private final TrainingService trainingService;
//  private final Scanner scanner;
//
//  @Autowired
//  public ConsoleInputHandler(TraineeService traineeService, TrainerService trainerService,
//      TrainingService trainingService) {
//    this.traineeService = traineeService;
//    this.trainerService = trainerService;
//    this.trainingService = trainingService;
//    this.scanner = new Scanner(System.in);
//  }
//
//  public void start() {
//    boolean running = true;
//
//    while (running) {
//      System.out.println("Choose an operation:");
//      System.out.println("1: Create Trainee");
//      System.out.println("2: Update Trainee");
//      System.out.println("3: Delete Trainee");
//      System.out.println("4: Get Trainee by ID");
//      System.out.println("5: Get All Trainees");
//      System.out.println("6: Create Trainer");
//      System.out.println("7: Update Trainer");
//      System.out.println("8: Delete Trainer");
//      System.out.println("9: Get Trainer by ID");
//      System.out.println("10: Get All Trainers");
//      System.out.println("11: Create Training");
//      System.out.println("12: Get Training by ID");
//      System.out.println("13: Get All Trainings");
//      System.out.println("0: Exit");
//
//
//
//      int choice = scanner.nextInt();
//      scanner.nextLine(); // Consume the newline character
//
//      switch (choice) {
//        case 1:
//          createTrainee();
//          break;
//        case 2:
//          updateTrainee();
//          break;
//        case 3:
//          deleteTrainee();
//          break;
//        case 4:
//          getTraineeById();
//          break;
//        case 5:
//          getAllTrainees();
//          break;
//        case 6:
//          createTrainer();
//          break;
//        case 7:
//          updateTrainer();
//          break;
//        case 8:
//          deleteTrainer();
//          break;
//        case 9:
//          getTrainerById();
//          break;
//        case 10:
//          getAllTrainers();
//          break;
//        case 11:
//          createTraining();
//          break;
//        case 12:
//          getTrainingById();
//          break;
//        case 13:
//          getAllTrainings();
//          break;
//        case 0:
//          running = false;
//          break;
//        default:
//          System.out.println("Invalid choice. Please try again.");
//      }
//    }
//  }
//
//
//  private void createTrainee() {
//    System.out.println("Enter Trainee id:");
//    Long id;
//    String firstName;
//    String lastName;
//    LocalDate dateOfBirth;
//    String address;
//
//    try {
//      id = Long.valueOf(scanner.nextLine());
//      System.out.println("Enter Trainee First Name:");
//      firstName = scanner.nextLine();
//      System.out.println("Enter Trainee Last Name:");
//      lastName = scanner.nextLine();
//      System.out.println("Enter Trainee Date of Birth (YYYY-MM-DD):");
//      dateOfBirth = LocalDate.parse(scanner.nextLine());
//      System.out.println("Enter Trainee Address:");
//      address = scanner.nextLine();
//
//      traineeService.createTrainee(id, firstName, lastName, dateOfBirth, address);
//
//      if (traineeService.createTrainee(id, firstName, lastName, dateOfBirth, address)) {
//        System.out.println("Trainee created successfully!");
//      } else {
//        System.out.println("Failed to create Trainee due to validation error.");
//      }
//
//    } catch (IllegalArgumentException e) {
//      System.out.println("Failed to create Trainee: " + e.getMessage());
//    } catch (Exception e) {
//      System.out.println("An unexpected error occurred: " + e.getMessage());
//    }
//  }
//
//
//  private void updateTrainee() {
//    System.out.println("Enter Trainee ID:");
//    Long id = scanner.nextLong();
//    scanner.nextLine(); // Consume the newline character
//
//    System.out.println("Enter new First Name:");
//    String firstName = scanner.nextLine();
//    System.out.println("Enter new Last Name:");
//    String lastName = scanner.nextLine();
//    System.out.println("Enter new Date of Birth (YYYY-MM-DD):");
//    LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
//    System.out.println("Enter new Address:");
//    String address = scanner.nextLine();
//
//    try {
//      traineeService.updateTrainee(id, firstName, lastName, dateOfBirth, address);
//      System.out.println("Trainee updated successfully!");
//    } catch (IllegalArgumentException e) {
//      System.out.println("Failed to update Trainee: " + e.getMessage());
//    }
//  }
//
//  private void deleteTrainee() {
//    System.out.println("Enter Trainee ID:");
//    Long id = scanner.nextLong();
//    scanner.nextLine(); // Consume the newline character
//
//    try {
//      traineeService.deleteTrainee(id);
//      System.out.println("Trainee deleted successfully!");
//    } catch (NoSuchElementException e) {
//      System.out.println("Failed to delete Trainee: " + e.getMessage());
//    }
//  }
//
//  private void getTraineeById() {
//    System.out.println("Enter Trainee ID:");
//    Long id = scanner.nextLong();
//    scanner.nextLine(); // Consume the newline character
//
//    try {
//      Trainee trainee = traineeService.getTraineeById(id);
//      System.out.println(trainee);
//    } catch (NoSuchElementException e) {
//      System.out.println("Trainee not found: " + e.getMessage());
//    }
//  }
//
//  private void getAllTrainees() {
//    List<Trainee> trainees = traineeService.getAllTrainees();
//    trainees.forEach(System.out::println);
//  }
//
//
//  private void createTrainer() {
//    System.out.println("Enter Trainer id:");
//    Long id;
//    String firstName;
//    String lastName;
//    String specialization;
//
//    try {
//      id = Long.valueOf(scanner.nextLine());
//      System.out.println("Enter Trainer First Name:");
//      firstName = scanner.nextLine();
//      System.out.println("Enter Trainer Last Name:");
//      lastName = scanner.nextLine();
//      System.out.println("Enter Trainer Specialization:");
//      specialization = scanner.nextLine();
//
//      if (trainerService.createTrainer(id, firstName, lastName, specialization)) {
//        System.out.println("Trainer created successfully!");
//      } else {
//        System.out.println("Failed to create Trainer due to validation error.");
//      }
//
//    } catch (IllegalArgumentException e) {
//      System.out.println("Failed to create Trainer: " + e.getMessage());
//    } catch (Exception e) {
//      System.out.println("An unexpected error occurred: " + e.getMessage());
//    }
//  }
//
//  private void updateTrainer() {
//    System.out.println("Enter Trainer ID:");
//    Long id = scanner.nextLong();
//    scanner.nextLine(); // Consume the newline character
//
//    System.out.println("Enter new First Name:");
//    String firstName = scanner.nextLine();
//    System.out.println("Enter new Last Name:");
//    String lastName = scanner.nextLine();
//    System.out.println("Enter new Specialization:");
//    String specialization = scanner.nextLine();
//
//    try {
//      trainerService.updateTrainer(id, firstName, lastName, specialization);
//      System.out.println("Trainer updated successfully!");
//    } catch (IllegalArgumentException e) {
//      System.out.println("Failed to update Trainer: " + e.getMessage());
//    }
//  }
//
//  private void deleteTrainer() {
//    System.out.println("Enter Trainer ID:");
//    Long id = scanner.nextLong();
//    scanner.nextLine(); // Consume the newline character
//
//    try {
//      trainerService.deleteTrainer(id);
//      System.out.println("Trainer deleted successfully!");
//    } catch (NoSuchElementException e) {
//      System.out.println("Failed to delete Trainer: " + e.getMessage());
//    }
//  }
//
//  private void getTrainerById() {
//    System.out.println("Enter Trainer ID:");
//    Long id = scanner.nextLong();
//    scanner.nextLine(); // Consume the newline character
//
//    try {
//      Trainer trainer = trainerService.getTrainerById(id);
//      System.out.println(trainer);
//    } catch (NoSuchElementException e) {
//      System.out.println("Trainer not found: " + e.getMessage());
//    }
//  }
//
//  private void getAllTrainers() {
//    List<Trainer> trainers = trainerService.getAllTrainers();
//    trainers.forEach(System.out::println);
//  }
//
//
//  //TRAINING
//
//
//  private void createTraining() {
//    Long id;
//    String trainingName;
//    String trainingType;
//    int duration = 0;
//    LocalDate trainingDate;
//    Long traineeId;
//    Long trainerId;
//
//    try {
//      System.out.println("Enter Training id:");
//      id = Long.valueOf(scanner.nextLine());
//    } catch (NumberFormatException e) {
//      System.out.println("Invalid input for Training ID. Please enter a valid number.");
//      return;
//    }
//
//    System.out.println("Enter Training Name:");
//    trainingName = scanner.nextLine();
//
//    System.out.println("Enter Training Type:");
//    trainingType = scanner.nextLine();
//
//    try {
//      System.out.println("Enter Training Duration in hours:");
//      duration = Integer.parseInt(scanner.nextLine());
//    } catch (NumberFormatException e) {
//      System.out.println("Invalid input for Training Duration. Please enter a valid number.");
//      return;
//    }
//
//    try {
//      System.out.println("Enter Training Date (YYYY-MM-DD):");
//      trainingDate = LocalDate.parse(scanner.nextLine());
//    } catch (Exception e) {
//      System.out.println("Invalid input for Training Date. Please enter a date in the format YYYY-MM-DD.");
//      return;
//    }
//
//    try {
//      System.out.println("Enter Trainee ID:");
//      traineeId = Long.valueOf(scanner.nextLine());
//    } catch (NumberFormatException e) {
//      System.out.println("Invalid input for Trainee ID. Please enter a valid number.");
//      return;
//    }
//
//    try {
//      System.out.println("Enter Trainer ID:");
//      trainerId = Long.valueOf(scanner.nextLine());
//    } catch (NumberFormatException e) {
//      System.out.println("Invalid input for Trainer ID. Please enter a valid number.");
//      return;
//    }
//
//    try {
//      Trainee trainee = traineeService.getTraineeById(traineeId);
//      Trainer trainer = trainerService.getTrainerById(trainerId);
//
//      if (trainingService.createTraining(id, trainee.getId(), trainer.getId(), trainingName,
//          trainingType, trainingDate, duration)) {
//        System.out.println("Training created successfully!");
//      } else {
//        System.out.println("Failed to create Training due to validation error.");
//      }
//
//    } catch (NoSuchElementException e) {
//      System.out.println("Failed to create Training: " + e.getMessage());
//    } catch (Exception e) {
//      System.out.println("An unexpected error occurred: " + e.getMessage());
//    }
//  }
//
//
//  private void getTrainingById() {
//    System.out.println("Enter Training ID:");
//    Long id = scanner.nextLong();
//    scanner.nextLine();
//
//    try {
//      Training training = trainingService.getTrainingById(id);
//      System.out.println(training);
//    } catch (NoSuchElementException e) {
//      System.out.println("Training not found: " + e.getMessage());
//    }
//  }
//
//  private void getAllTrainings() {
//    List<Training> trainings = trainingService.getAllTrainings();
//    trainings.forEach(System.out::println);
//  }
//}
//


package com.example.gymApp.utils;

import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.model.Training;
import com.example.gymApp.model.User;
import com.example.gymApp.service.ProfileService;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import com.example.gymApp.service.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import net.bytebuddy.asm.Advice.Enter;
import org.hibernate.type.StringNVarcharType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ConsoleInputHandler {

  private final TrainerService trainerService;
  private final TraineeService traineeService;
  private final TrainingService trainingService;
  private final ProfileService profileService;
  private final UserService userService;

  private final Scanner scanner;

  @Autowired
  public ConsoleInputHandler(TrainerService trainerService, TraineeService traineeService,
      TrainingService trainingService,
      ProfileService profileService, UserService userService) {
    this.trainingService = trainingService;
    this.scanner = new Scanner(System.in);
    this.trainerService = trainerService;
    this.traineeService = traineeService;
    this.profileService = profileService;
    this.userService = userService;


  }

  // Основной метод, запускающий процесс создания тренера
  public void start() {

    boolean running = true;

    while (running) {

      System.out.println("\n=== GYM APP MENU ===");
      System.out.println("1. Create Trainer");
      System.out.println("2. Select Trainer by username");
      System.out.println("3. Change Trainer's password");
      System.out.println("4. Update Trainer's profile");
      System.out.println("5. Activate/deactivate User's profile");

      System.out.println("6. Create Trainee");
      System.out.println("7. Select Trainee by username");
      System.out.println("8. Change Trainee's password");
      System.out.println("9. Update Trainee's profile");
      System.out.println("10. Delete Trainee's profile by username");
      System.out.println("11. Create training");
      System.out.println("12. Get trainee trainings list with criteria");
      System.out.println("13. Get trainer trainings list with criteria");
      System.out.println("14. Get all trainers not assigned on specified trainee");

      System.out.println("15. Exit");

      System.out.print("Select an option: ");
      int choice = Integer.parseInt(scanner.nextLine());

      switch (choice) {
        case 1:
          createTrainer(); // Вызов метода создания тренера
          break;
        case 2:
          selectTrainerByUsername();
          break;
        case 3:
          changeUserPassword();
          break;
        case 4:
          updateTrainerProfile();
          break;
        case 5:
          activateUserProfile();
          break;
        case 6:
          createTrainee();
          break;
        case 7:
          selectTraineeByUsername();
          break;
        case 8:
          changeUserPassword();
          break;
        case 9:
          updateTraineeProfile();
          break;
        case 10:
          deleteTraineeByUsername();
          break;
        case 11:
          addTraining();
          break;

        case 12:
          getTraineeTrainings();
          break;
        case 13:
          getTrainersTrainings();
          break;
        case 14:
          getAllTrainersNotAssignedOnTrainee();
          break;

        case 15:
          System.out.println("Exiting the application.");
          running = false; // Завершение работы приложения
          break;


        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  private void getAllTrainersNotAssignedOnTrainee() {
    System.out.println("Enter trainee's username: ");
    String traineeUsername = scanner.nextLine();

    List<Trainer> trainers = trainerService.getAllTrainersNotAssignedToTrainee(traineeUsername);

    System.out.println("The list of trainers not assigned to trainee: " + traineeUsername + " is : " +
        trainers);


  }

  private void getTrainersTrainings() {
    System.out.println("Enter trainer's username: ");
    String trainerUsername = scanner.nextLine();

    System.out.println("Enter trainee's name (firstName): ");
    String traineeName = scanner.nextLine();


    System.out.println("Enter start date you want to get trainings for trainer: ");
    String startTrainingDate = scanner.nextLine();
    LocalDate startDate = DateTimeFormatter.getCorrectDateFormat(startTrainingDate);
    System.out.println("Enter finish date you want to get trainings for trainer: ");
    String finishTrainingDate = scanner.nextLine();
    LocalDate finishDate = DateTimeFormatter.getCorrectDateFormat(finishTrainingDate);


    List<Training> trainerTrainingsList = trainingService.getTraineesList(trainerUsername, traineeName, startDate,finishDate);

    System.out.println("All trainings for trainee with username : "
        + trainerUsername + " is : " + trainerTrainingsList);



  }


  public void getTraineeTrainings() {

    System.out.println("Enter trainee's username : ");

    String traineeUsername = scanner.nextLine();

    System.out.println("Enter trainer's name");
    String trainerName = scanner.nextLine();

    System.out.println("Enter start date you want to get trainings for trainee: ");
    String startTrainingDate = scanner.nextLine();
    LocalDate startDate = DateTimeFormatter.getCorrectDateFormat(startTrainingDate);
    System.out.println("Enter finish date you want to get trainings: ");
    String finishTrainingDate = scanner.nextLine();
    LocalDate finishDate = DateTimeFormatter.getCorrectDateFormat(finishTrainingDate);


    //todo: add specialization check
    System.out.println("Enter trainer's specialization: ");
    String trainerSpecialization = scanner.nextLine();


    //pay attention - we ask not trainer's username but only name!
    List<Training> traineeTtainingList =
        trainingService.getTrainingsByUserUsername(traineeUsername,
        startDate, finishDate, trainerName, trainerSpecialization);
    System.out.println("All trainings for trainee with username : "
        + traineeUsername + " is : " + traineeTtainingList);

  }

  private void addTraining() {
    System.out.println("Enter trainer's username: ");
    String trainerUsername = scanner.nextLine();

    System.out.println("Enter trainee's username: ");
    String traineeUsername = scanner.nextLine();

    System.out.println("Enter training name: ");
    String trainingName = scanner.nextLine();

    LocalDate dateOfTraining = null;
    while (dateOfTraining == null) {
      System.out.print("Enter training date (YYYY-MM-DD): ");
      try {
        dateOfTraining = LocalDate.parse(scanner.nextLine());
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please try again.");
      }
    }

    System.out.println("Enter training duration in hours: ");
    int trainingDurationInHours = Integer.parseInt(scanner.nextLine());

    trainingService.createTraining(trainerUsername, traineeUsername, trainingName, dateOfTraining,
        trainingDurationInHours);


  }


  public void deleteTraineeByUsername() {
    System.out.println("Enter trainee's username to delete: ");
    String username = scanner.nextLine();
    trainerService.deleteTraineeByUsername(username);

  }

  public void activateUserProfile() {
    System.out.println("Select existing user's username :");
    String username = scanner.nextLine();

    User user = userService.getUserByUsername(username);

    System.out.println("Update activity status (false/true):");
    String activityStatus = scanner.nextLine().toLowerCase();
    boolean activeStatus = false;

    try {

      if (activityStatus.equals("true") || activityStatus.equals("false")) {
        activeStatus = Boolean.parseBoolean(activityStatus);
        System.out.println("Activity status set to: " + activeStatus);
      } else {
        throw new IllegalArgumentException("Invalid input. Please enter 'true' or 'false'.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    user.setActive(activeStatus);
    userService.saveUpdatedUser(user);
    System.out.println("User profile updated successfully!");

  }

  public void updateTraineeProfile() {

    System.out.println("Select existing trainee's username :");
    String userName = scanner.nextLine();

    Trainee trainee = traineeService.getTraineeByUsername(userName);

    System.out.println("Select trainee's new name : ");
    String newName = scanner.nextLine();

    System.out.println("Select trainee's new second name:");
    String newLastName = scanner.nextLine();

    String newUsername = profileService.generateUsername(newName, newLastName);
    System.out.print("Auto-generated new Trainee's Username: " + newUsername);

    String newPassword = profileService.generateRandomPassword();
    System.out.print("Auto-generated new Trainee's Password: " + newPassword);

    LocalDate dateOfBirth = null;
    while (dateOfBirth == null) {
      System.out.print("Enter new Trainee's Date of Birth (YYYY-MM-DD): ");
      try {
        dateOfBirth = LocalDate.parse(scanner.nextLine());
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please try again.");
      }
    }

    System.out.print("Enter Trainee's Address: ");
    String address = scanner.nextLine();

    System.out.println("Update activity status (false/true):");
    String activityStatus = scanner.nextLine().toLowerCase();
    boolean activeStatus = false;

    try {

      if (activityStatus.equals("true") || activityStatus.equals("false")) {
        activeStatus = Boolean.parseBoolean(activityStatus);
        System.out.println("Activity status set to: " + activeStatus);
      } else {
        throw new IllegalArgumentException("Invalid input. Please enter 'true' or 'false'.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      Trainee trainee1 = traineeService.updateTrainee(trainee, newName, newLastName, newUsername,
          newPassword, activeStatus, address, dateOfBirth);

      System.out.println("Trainee updated successfully!");
      System.out.println("This is new Trainer : " + trainee1);

//      System.out.println(
//          "Trainer from DB: " + traineeService.getTrainerByUsername(trainee1.getUsername()));

    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }


  @Transactional
  public void updateTrainerProfile() {

    System.out.println("Select existing trainer's username :");
    String userName = scanner.nextLine();

    Trainer trainer = trainerService.getTrainerByUsername(userName);

    System.out.println("Select trainer's new name : ");
    String newName = scanner.nextLine();

    System.out.println("Select trainer's new second name:");
    String newLastName = scanner.nextLine();

    String newUsername = profileService.generateUsername(newName, newLastName);
    System.out.print("Auto-generated new Trainer's Username: " + newUsername);

    String newPassword = profileService.generateRandomPassword();
    System.out.print("Auto-generated new Trainer's Password: " + newPassword);

    //todo :  need to add a specialization check

    System.out.println("Enter new Trainer's Specialization (existing specialization):");
    String specialization = scanner.nextLine();

    System.out.println("Update activity status (false/true):");
    String activityStatus = scanner.nextLine().toLowerCase();
    boolean activeStatus = false;

    try {

      if (activityStatus.equals("true") || activityStatus.equals("false")) {
        activeStatus = Boolean.parseBoolean(activityStatus);
        System.out.println("Activity status set to: " + activeStatus);
      } else {
        throw new IllegalArgumentException("Invalid input. Please enter 'true' or 'false'.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    try {
      Trainer trainer1 = trainerService.updateTrainer(trainer, newName, newLastName, newUsername,
          newPassword, activeStatus,
          specialization);
      System.out.println("Trainer updated successfully!");
      System.out.println("This is new Trainer : " + trainer1);

      System.out.println(
          "Trainer from DB: " + trainerService.getTrainerByUsername(trainer1.getUsername()));

    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }


  public void changeUserPassword() {
    System.out.println("Enter user's old password:");
    String oldPassword = scanner.nextLine();

    try {
      // Найти тренера по старому паролю
      User user = userService.getUserByPassword(oldPassword);

      // Сгенерировать новый пароль
      String newPassword = profileService.generateRandomPassword();

      // Установить новый пароль пользователю тренера
      user.setPassword(newPassword);
      System.out.println("New password generated: " + newPassword);

      // Сохранить обновленного пользователя
      userService.saveUpdatedUser(user);

    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }


  public void selectTraineeByUsername() {
    System.out.println("Enter Trainee's username");
    String username = scanner.nextLine();

    try {
      Trainee trainee = traineeService.getTraineeByUsername(username);
      System.out.println("You received trainee with username: " + trainee);

    } catch (NoSuchElementException e) {
      System.out.println("NO such trainee!!! with name: " + username);
    }
  }


  public Trainer selectTrainerByUsername() {
    System.out.println("Enter Trainer's username");
    String username = scanner.nextLine();

    try {
      Trainer trainer = trainerService.getTrainerByUsername(username);
      System.out.println("You received trainer with username: " + trainer);
      return trainer;
    } catch (NoSuchElementException e) {
      System.out.println("NO such trainer!!! with name: " + username);

    }
    return null;
  }


  // Метод для создания тренера с вводом данных
  public void createTrainer() {
    System.out.println("Enter Trainer's First Name:");
    String firstName = scanner.nextLine();

    System.out.println("Enter Trainer's Last Name:");
    String lastName = scanner.nextLine();

    String username = profileService.generateUsername(firstName, lastName);
    System.out.print("Auto-generated Trainee's Username: " + username);

    String password = profileService.generateRandomPassword();
    System.out.println("Auto-generated Trainee's Password: " + password);

    System.out.println("Enter Trainer's Specialization (existing specialization):");
    String specialization = scanner.nextLine();

    //todo :  need to add a specialization check

    try {
      Trainer trainer = trainerService.createTrainer(firstName, lastName, username, password,
          specialization);
      System.out.println("Trainer created successfully!");
      System.out.println("Trainer details: " + trainer);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public void createTrainee() {
    System.out.println("\n--- Create Trainee ---");

    System.out.print("Enter Trainee's First Name: ");
    String firstName = scanner.nextLine();

    System.out.print("Enter Trainee's Last Name: ");
    String lastName = scanner.nextLine();

    String username = profileService.generateUsername(firstName, lastName);
    System.out.print("Auto-generated Trainee's Username: " + username);

    String password = profileService.generateRandomPassword();
    System.out.print("Auto-generated Trainee's Password: " + password);

    LocalDate dateOfBirth = null;
    while (dateOfBirth == null) {
      System.out.print("Enter Trainee's Date of Birth (YYYY-MM-DD): ");
      try {
        dateOfBirth = LocalDate.parse(scanner.nextLine());
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please try again.");
      }
    }

    System.out.print("Enter Trainee's Address: ");
    String address = scanner.nextLine();

    try {
      Trainee trainee = traineeService.createTrainee(firstName, lastName, username, password,
          dateOfBirth, address);
      System.out.println("Trainee created successfully!");
      System.out.println("Trainee details: " + trainee);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
