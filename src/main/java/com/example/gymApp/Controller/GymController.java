package com.example.gymApp.Controller;


import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Model.Trainer;
import com.example.gymApp.Model.Training;
import com.example.gymApp.Service.TraineeService;
import com.example.gymApp.Service.TrainerService;
import com.example.gymApp.Service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GymController {
    @Autowired
    private final TraineeService traineeService;
    @Autowired
    private final TrainerService trainerService;
    @Autowired
    private final TrainingService trainingService;


    public GymController(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;

    }
        @GetMapping("/trainees")
        public List<Trainee> getAllTrainees () {
            return traineeService.getAllTrainees();
        }

        @GetMapping("/trainers")
        public List<Trainer> getAllTrainers () {
            return trainerService.getAllTrainers();
        }

        @GetMapping("/trainings")
        public List<Training> getAllTrainings () {
            return trainingService.getAllTrainings();
        }
    }
