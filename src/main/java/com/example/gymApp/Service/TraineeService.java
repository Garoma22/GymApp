package com.example.gymApp.Service;

import com.example.gymApp.Dao.TraineeDAO;
import com.example.gymApp.Model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {

    private final TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    public void createTrainee(Trainee trainee) {
        // Дополнительная логика, если требуется
        traineeDAO.createTrainee(trainee);
    }

    public void updateTrainee(Trainee trainee) {

        traineeDAO.updateTrainee(trainee);
    }

    public void deleteTrainee(Long id) {
        // Логика удаления, если нужно
        traineeDAO.deleteTrainee(id);
    }

    public Trainee getTraineeById(Long id) {
        // Можно добавить проверку на null или существование
        return traineeDAO.getTraineeById(id);
    }

    public List<Trainee> getAllTrainees() {
        // Дополнительная логика обработки списка, если нужно
        return traineeDAO.getAllTrainees();
    }
}