package com.example.gymApp.Controller;

import com.example.gymApp.Model.Trainee;
import com.example.gymApp.Service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainees")
public class TraineeController {

    private final TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping
    public void createTrainee(@RequestBody Trainee trainee) {
        traineeService.createTrainee(trainee);
    }

    @PutMapping("/update/{id}")
    public void updateTrainee(@PathVariable ("id") Long id, @RequestBody Trainee trainee) {
        trainee.setId(id);
        traineeService.updateTrainee(trainee);
    }



    @DeleteMapping("/delete/{id}")
    public void deleteTrainee(@PathVariable ("id") Long id) {
        traineeService.deleteTrainee(id);
    }

    @GetMapping("/{id}")
    public Trainee getTraineeById(@PathVariable Long id) {
        return traineeService.getTraineeById(id);
    }

    @GetMapping
    public List<Trainee> getAllTrainees() {
        return traineeService.getAllTrainees();
    }
}