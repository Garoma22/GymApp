package com.example.gymApp;


import com.example.gymApp.Controller.GymController;
import com.example.gymApp.Controller.HomeController;
import com.example.gymApp.Dao.InMemoryStorage;
import com.example.gymApp.Dao.TraineeDAO;
import com.example.gymApp.Dao.TrainerDAO;
import com.example.gymApp.Dao.TrainingDAO;
import com.example.gymApp.Service.Facade;
import com.example.gymApp.Service.TraineeService;
import com.example.gymApp.Service.TrainerService;
import com.example.gymApp.Service.TrainingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
    public class AppConfig   {

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
        public HomeController homeController(){
            return new HomeController();
        }

        @Bean
        public GymController gymController(){
            return new GymController(facade());
       }

    }






