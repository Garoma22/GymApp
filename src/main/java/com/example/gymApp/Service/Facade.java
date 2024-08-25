package com.example.gymApp.Service;


import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Model.Trainer;
import com.example.gymApp.Model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Facade {

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;


    @Autowired
    public Facade(TrainerService trainerService, TraineeService traineeService, TrainingService trainingService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
    }

    public void createTrainee(Trainee trainee) {
        // Дополнительная логика, если требуется
        traineeService.createTrainee(trainee);
    }



    public boolean updateTrainee(Trainee trainee) {
        return traineeService.updateTrainee(trainee);
    }

    public boolean deleteTrainee(Long id) {
        // Логика удаления, если нужно
        return traineeService.deleteTrainee(id);
    }

    public Trainee getTraineeById(Long id) {
        // Можно добавить проверку на null или существование
        return traineeService.getTraineeById(id);
    }

    public List<Trainee> getAllTrainees() {
        // Дополнительная логика обработки списка, если нужно
        return traineeService.getAllTrainees();
    }


    public void createTrainer(Trainer trainer) {
        // Дополнительная логика, если требуется
        trainerService.createTrainer(trainer);
    }

    public boolean updateTrainer(Trainer trainer) {
        // Логика обновления данных тренера
        return  trainerService.updateTrainer(trainer);
    }


    //TRAINER SERVICE
    public boolean deleteTrainer(Long id) {
        // Логика удаления тренера по ID
        return trainerService.deleteTrainer(id);
    }

    public Trainer getTrainerById(Long id) {
        // Можно добавить проверку на null или существование тренера
        return trainerService.getTrainerById(id);
    }

    public List<Trainer> getAllTrainers() {
        // Логика получения списка всех тренеров
        return trainerService.getAllTrainers();
    }



    //TRAINING SERVICE

    public void createTraining(Training training) {
        // Логика для создания тренировки
        trainingService.createTraining(training);
    }

    public void updateTraining(Training training) {
        // Логика для обновления тренировки
        trainingService.updateTraining(training);
    }

    public void deleteTraining(Long id) {
        // Логика для удаления тренировки
        trainingService.deleteTraining(id);
    }

    public Training getTrainingById(Long id) {
        // Логика для получения тренировки по ID
        return trainingService.getTrainingById(id);
    }

    public List<Training> getAllTrainings() {
        // Логика для получения всех тренировок
        return trainingService.getAllTrainings();
    }
}
