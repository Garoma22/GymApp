package com.example.gymApp.Controller;


import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Model.Trainer;
import com.example.gymApp.Model.Training;
import com.example.gymApp.Service.Facade;
import com.example.gymApp.Service.TraineeService;
import com.example.gymApp.Service.TrainerService;
import com.example.gymApp.Service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/training")
public class TrainingController {


//    private final TrainingService trainingService;
//    private final TrainerService trainerService;
//    private final TraineeService traineeService;

//    @Autowired
//    public TrainingController(TrainingService trainingService, TraineeService traineeService, TrainerService trainerService) {
//        this.trainingService = trainingService;
//        this.trainerService = trainerService;
//        this.traineeService = traineeService;
//    }


    private Facade facade;

//    public TrainingController(Facade facade) {
//        this.facade = facade;
//    }

@Autowired
    public void setFacade(Facade facade){
        this.facade = facade;
    }


//an example of request: http://localhost:8080/training/create?trainerId=5&traineeId=5&trainingId=4
    //do not forget to add the body like that

    /*
{
  "trainingName": "Strength Training",
  "trainingType": "Cardio",
  "trainingDate": "2024-08-25T15:30:00",
  "trainingDuration": 60
}


     */


    @PostMapping("/create")
    public ResponseEntity<String> createTraining(

            @RequestParam("trainerId") Long trainerId,
            @RequestParam("traineeId") Long traineeId,
            @RequestParam("trainingId") Long trainingId,
            @RequestBody Training training) {

        Trainer trainer = facade.getTrainerById(trainerId);
        Trainee trainee = facade.getTraineeById(traineeId);

        if (trainer == null || trainee == null) {

            return new ResponseEntity<>("Trainer or Trainee not found", HttpStatus.NOT_FOUND);
        }
        log.info("Тренер и трейни есть!!!");
        // Устанавливаем найденного тренера и стажера в объект Training
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setId(trainingId);

        // Вызываем сервис для сохранения тренировки
        facade.createTraining(training);

        return new ResponseEntity<>("Training created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/select/all")
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> trainings = facade.getAllTrainings();
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

}
