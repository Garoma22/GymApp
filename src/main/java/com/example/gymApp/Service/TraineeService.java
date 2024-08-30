package com.example.gymApp.Service;

import com.example.gymApp.Dao.TraineeDAO;
import com.example.gymApp.Model.Trainee;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class TraineeService {

private  TraineeDAO traineeDAO;

    @Autowired
    public void setTraineeDAO(TraineeDAO traineeDAO){
        this.traineeDAO = traineeDAO;
    }

    public void createTrainee(Trainee trainee) {
        traineeDAO.createTrainee(trainee);
    }


    public boolean updateTrainee(Trainee trainee) {
        return traineeDAO.updateTrainee(trainee);
    }

    public boolean deleteTrainee(Long id) {
        return traineeDAO.deleteTrainee(id);
    }

    public Trainee getTraineeById(Long id) {
        return traineeDAO.getTraineeById(id);
    }

    public List<Trainee> getAllTrainees() {
        return  traineeDAO.getAllTrainees();
    }
}