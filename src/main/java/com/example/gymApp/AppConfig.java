package com.example.gymApp;
////
//////
//
////
//import com.example.gymApp.dao.InMemoryStorage;
//import com.example.gymApp.dao.TraineeDAO;
//import com.example.gymApp.dao.TrainerDAO;
//import com.example.gymApp.dao.TrainingDAO;
import com.example.gymApp.model.Trainee;
import com.example.gymApp.model.Trainer;
//import com.example.gymApp.service.Facade;
//import com.example.gymApp.service.TraineeService;
//import com.example.gymApp.service.TrainerService;
//import com.example.gymApp.service.TrainingService;
//import com.example.gymApp.utils.DataInitializer;
//import com.example.gymApp.utils.UserInitializationPostProcessor;
import com.example.gymApp.utils.DataInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;





@Configuration
@ComponentScan(basePackages = "com.example.gymApp")
public class AppConfig {


  @Bean
  public DataInitializer dataInitializer() {
    return new DataInitializer();
  }
}


