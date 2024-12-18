package com.example.gym.utils;

import com.example.gym.model.Trainee;
import com.example.gym.model.Trainer;
import com.example.gym.model.Training;
import com.example.gym.model.User;
import com.example.gym.service.ProfileService;
import com.example.gym.service.TraineeService;
import com.example.gym.service.TrainerService;
import com.example.gym.service.TrainingService;
import com.example.gym.service.UserService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

  public void start() {

    boolean running = true;
    while (running) {
      try {
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
        System.out.println("15. Get all trainees trainers");

        System.out.println("16. Exit");

        System.out.print("Select an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
          case 1:
            createTrainer();
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
            createTraining();
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
            getAllTraineesTreners();
            break;

          case 16:
            System.out.println("Exiting the application.");
            running = false;
            break;

          default:
            System.out.println("Invalid option. Please try again.");
        }

      } catch (IllegalArgumentException e) {
        System.out.println("Wrong input !!! " + e.getMessage());

      }
    }
  }

  private void getAllTraineesTreners() {
    try {
      System.out.println("Enter trainee's username: ");
      String traineeUsername = scanner.nextLine();

      Set<Trainer> allTrainers = trainerService.getAlltrainersByTrainee(traineeUsername);

      System.out.println("All trainers by trainee: " + traineeUsername + " : " + allTrainers);

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  private void getAllTrainersNotAssignedOnTrainee() {
    try {

      System.out.println("Enter trainee's username: ");
      String traineeUsername = scanner.nextLine();
      System.out.println("Enter trainee password: ");
      String traineePassword = scanner.nextLine();
      userService.getUserByPasswordAndUsername(traineePassword, traineeUsername);

      List<Trainer> trainers = trainerService.getAllTrainersNotAssignedToTrainee(traineeUsername);

      System.out.println(
          "The list of trainers not assigned to trainee: " + traineeUsername + " is : " +
              trainers);


    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  private void getTrainersTrainings() {

    try {

      System.out.println("Enter trainer's username: ");
      String trainerUsername = scanner.nextLine();

      System.out.println("Enter trainer's old password: ");
      String trainerOldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(trainerOldPassword, trainerUsername);

      System.out.println("Enter trainee's name (firstName): ");
      String traineeName = scanner.nextLine();

      System.out.println("Enter start date you want to get trainings for trainer: ");
      String startTrainingDate = scanner.nextLine();
      LocalDate startDate = DateTimeFormatter.getCorrectDateFormat(startTrainingDate);

      System.out.println("Enter finish date you want to get trainings for trainer: ");
      String finishTrainingDate = scanner.nextLine();
      LocalDate finishDate = DateTimeFormatter.getCorrectDateFormat(finishTrainingDate);

      List<Training> trainerTrainingsList = trainingService.getTraineesList(trainerUsername,
          traineeName, startDate, finishDate);

      System.out.println("All trainings for trainee with username : "
          + trainerUsername + " is : " + trainerTrainingsList);


    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  public void getTraineeTrainings() {
    try {

      System.out.println("Enter trainee's username : ");
      String traineeUsername = scanner.nextLine();
      System.out.println("Enter trainee old password: ");
      String traineeOldPassword = scanner.nextLine();
      userService.getUserByPasswordAndUsername(traineeOldPassword, traineeUsername);

      System.out.println("Enter trainer's name");
      String trainerName = scanner.nextLine();

      System.out.println("Enter start date you want to get trainings for trainee: ");
      String rawStartTrainingDate = scanner.nextLine();

      LocalDate startOfTraining = DateTimeFormatter.getCorrectDateFormat(rawStartTrainingDate);
      if (startOfTraining == null) {
        return;
      }

      System.out.println("Enter finish date you want to get trainings: ");
      String rawFinishTrainingDate = scanner.nextLine();

      LocalDate finishOfTraining = DateTimeFormatter.getCorrectDateFormat(rawFinishTrainingDate);
      if (finishOfTraining == null) {
        return;
      }

      System.out.println("Enter trainer's specialization: ");
      String trainerSpecialization = scanner.nextLine();

      trainerService.checkSpecializationCorrectness(trainerSpecialization);


      List<Training> traineeTtainingList =
          trainingService.getTrainingsByUserUsername(traineeUsername,
              startOfTraining, finishOfTraining, trainerName, trainerSpecialization);
      System.out.println("All trainings for trainee with username : "
          + traineeUsername + " is : " + traineeTtainingList);

    } catch (NoSuchElementException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  private void createTraining() {
    try {
      System.out.println("Enter trainer's username: ");
      String trainerUsername = scanner.nextLine();

      System.out.println("Enter trainer old password: ");
      String trainerOldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(trainerOldPassword, trainerUsername);

      System.out.println("Enter trainee's username: ");
      String traineeUsername = scanner.nextLine();

      System.out.println("Enter trainee old password: ");
      String traineeOldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(traineeOldPassword, traineeUsername);

      System.out.println("Enter training name: ");
      String trainingName = scanner.nextLine();

      System.out.println("Enter training date: ");
      String rawDate = scanner.nextLine();

      LocalDate dateOfTraining = DateTimeFormatter.getCorrectDateFormat(rawDate);
      if (dateOfTraining == null) {
        return;
      }

      System.out.println("Enter training duration in hours: ");
      int trainingDurationInHours = Integer.parseInt(scanner.nextLine());

      trainingService.createTraining(trainerUsername, traineeUsername, trainingName,
          dateOfTraining,
          trainingDurationInHours);

    } catch (IllegalArgumentException e) {
      log.info(e.getMessage());
    } catch (RuntimeException e) {

      log.info("An unexpected error occurred: " + e.getMessage());
    }
  }

  public void deleteTraineeByUsername() {

    try {
      System.out.println("Enter trainee's username to delete: ");
      String userName = scanner.nextLine();

      System.out.println("Enter old paassword: ");
      String oldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(oldPassword, userName);

      traineeService.deleteTraineeByUsername(userName);

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }

  }

  public void activateUserProfile() {
    try {
      System.out.println("Select existing user's username :");
      String username = scanner.nextLine();


      System.out.println("Enter old password: ");
      String oldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(oldPassword, username);


      User user = userService.getUserByUsername(username);

      System.out.println("Update activity status (false/true):");
      String activityStatus = scanner.nextLine().toLowerCase();

      boolean newActiveStatus = userService.setActivityStatusToUser(activityStatus, user);

      user.setActive(newActiveStatus);
      userService.setNewPasswordToUserFromDtoAndSaveToDb(user);


    } catch (IllegalArgumentException | NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  public void updateTraineeProfile() {
    try {
      System.out.println("Select existing trainee's username :");
      String userName = scanner.nextLine();

      Trainee trainee = traineeService.getTraineeByUsername(userName);

      System.out.println("Enter old paassword: ");
      String oldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(oldPassword, userName);

      System.out.println("Select trainee's new name : ");
      String newName = scanner.nextLine();

      System.out.println("Select trainee's new second name:");
      String newLastName = scanner.nextLine();

      String newUsername = profileService.generateUsername(newName, newLastName);
      System.out.print("Auto-generated new Trainee's Username: " + newUsername);

      String newPassword = profileService.generateRandomPassword();
      System.out.print("Auto-generated new Trainee's Password: " + newPassword);

      System.out.print("Enter new Trainee's Date of Birth (YYYY-MM-DD): ");
      String newDate = scanner.nextLine();


      LocalDate dateOfBirth = DateTimeFormatter.getCorrectDateFormat(newDate);
      if (dateOfBirth == null) {
        return;
      }

      System.out.print("Enter new Trainee's Address: ");
      String address = scanner.nextLine();

      System.out.println("Update activity status (false/true):");
      String activityStatus = scanner.nextLine().toLowerCase();

      boolean newActiveStatus = userService.setActivityStatusToUser(activityStatus,
          trainee.getUser());
      trainee.getUser().setActive(newActiveStatus);

      Trainee trainee1 = traineeService.updateTrainee(trainee, newName, newLastName, newUsername,
          newPassword, newActiveStatus, address, dateOfBirth);

      System.out.println("Trainee updated successfully!");
      System.out.println("This is new Trainer : " + trainee1);


    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  @Transactional
  public void updateTrainerProfile() {

    try {
      System.out.println("Select existing trainer's username :");
      String userName = scanner.nextLine();

      Trainer trainer = trainerService.getTrainerByUsername(userName);

      System.out.println("Enter old paassword: ");
      String oldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(oldPassword, userName);

      System.out.println("Enter trainer's new name : ");
      String newName = scanner.nextLine();

      System.out.println("Enter trainer's new second name:");
      String newLastName = scanner.nextLine();

      String newUsername = profileService.generateUsername(newName, newLastName);
      System.out.print("Auto-generated new Trainer's Username: " + newUsername);

      String newPassword = profileService.generateRandomPassword();
      System.out.print("Auto-generated new Trainer's Password: " + newPassword);

      System.out.println("Enter new Trainer's Specialization (choose existing specialization):");
      String specialization = scanner.nextLine();

      trainerService.checkSpecializationCorrectness(specialization);

      System.out.println("Update activity status (false/true):");
      String activityStatus = scanner.nextLine().toLowerCase();

      boolean activeStatus = userService.setActivityStatusToUser(activityStatus,
          trainer.getUser());

      Trainer trainer1 = trainerService.updateTrainer(trainer, newName, newLastName, newUsername,
          newPassword, activeStatus,
          specialization);
      System.out.println("Trainer updated successfully!");
      System.out.println("This is new Trainer : " + trainer1);

      System.out.println(
          "Trainer from DB: " + trainerService.getTrainerByUsername(trainer1.getUser().getUsername()));
    } catch (IllegalArgumentException | NoSuchElementException e) {
      System.out.println(e.getMessage());

    }
  }

  public void changeUserPassword() {

    System.out.println("Enter user's username : ");
    String username = scanner.nextLine();

    System.out.println("Enter user's old password:");
    String oldPassword = scanner.nextLine();

    try {
      User user = userService.getUserByPasswordAndUsername(oldPassword, username);

      String newPassword = profileService.generateRandomPassword();


      user.setPassword(newPassword);
      System.out.println("New password generated: " + newPassword);

      userService.setNewPasswordToUserFromDtoAndSaveToDb(user);
      log.info("User password: " + user + " is updated");

    } catch (NoSuchElementException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public void selectTraineeByUsername() {
    try {

      System.out.println("Enter Trainee's username");
    String username = scanner.nextLine();

    System.out.println("Enter old password: ");
    String oldPassword = scanner.nextLine();

    userService.getUserByPasswordAndUsername(oldPassword, username);


      Trainee trainee = traineeService.getTraineeByUsername(username);
      System.out.println("You received trainee with username: " + trainee);

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  public void selectTrainerByUsername() {

    try {
    System.out.println("Enter Trainer's username");
    String username = scanner.nextLine();

      System.out.println("Enter old password: ");
      String oldPassword = scanner.nextLine();

      userService.getUserByPasswordAndUsername(oldPassword, username);


      Trainer trainer = trainerService.getTrainerByUsername(username);
      System.out.println("You received trainer with username: " + trainer);

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

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

    System.out.println("Enter trainee's date of birth: ");

    LocalDate dateOfBirth = DateTimeFormatter.getCorrectDateFormat(scanner.nextLine());
    if (dateOfBirth == null) {
      return;
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
