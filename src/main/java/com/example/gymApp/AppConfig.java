package com.example.gymApp;


import com.example.gymApp.Controller.GymController;
import com.example.gymApp.Controller.HomeController;
import com.example.gymApp.dao.InMemoryStorage;
import com.example.gymApp.dao.TraineeDAO;
import com.example.gymApp.dao.TrainerDAO;
import com.example.gymApp.dao.TrainingDAO;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
import com.example.gymApp.service.Facade;
import com.example.gymApp.service.TraineeService;
import com.example.gymApp.service.TrainerService;
import com.example.gymApp.service.TrainingService;
import com.example.gymApp.utils.DataInitializer;
import com.example.gymApp.utils.UserInitializationPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
@ComponentScan(basePackages = "com.example.gymApp")
public class AppConfig {

  @Bean
  public static UserInitializationPostProcessor userInitializationPostProcessor() {
    return new UserInitializationPostProcessor();
  }


  @Bean
  public DataInitializer dataInitializer() {
    return new DataInitializer();
  }


  @Bean
  @Scope("prototype")
  public Trainee traineePrototype() {
    return new Trainee();
  }

  @Bean
  @Scope("prototype")
  public Trainer trainerPrototype() {
    return new Trainer();
  }


  @Bean
  public InMemoryStorage inMemoryStorage() {
    return new InMemoryStorage();

  }

  @Bean
  public TrainingService trainingService() {
    return new TrainingService();
  }

  @Bean
  public TraineeService traineeService() {
    return new TraineeService();
  }

  @Bean
  public TrainerService trainerService() {
    return new TrainerService();
  }

  @Bean
  public Facade facade() {
    return new Facade(trainerService(), traineeService(), trainingService());
  }

  @Bean
  public TraineeDAO traineeDAO() {
    return new TraineeDAO(inMemoryStorage());
  }

  @Bean
  public TrainerDAO trainerDAO() {
    return new TrainerDAO(inMemoryStorage());
  }

  @Bean
  public TrainingDAO trainingDAO() {
    return new TrainingDAO(inMemoryStorage());
  }

  @Bean
  public HomeController homeController() {
    return new HomeController();
  }

  @Bean
  public GymController gymController() {
    return new GymController(facade());
  }

}






