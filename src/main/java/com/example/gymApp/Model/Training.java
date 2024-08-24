package com.example.gymApp.Model;


import lombok.Data;

import java.time.LocalDateTime;



@Data
//@Entity
//@Table(name = "trainings")
public class Training {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

//    @ManyToOne
//    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

//    @Column(name = "training_name", nullable = false)
    private String trainingName;

//    @ManyToOne
//    @JoinColumn(name = "training_type_id")
    private String trainingType;

//    @Column(name = "training_date")
    private LocalDateTime trainingDate;

//    @Column(name = "training_duration")
    private Integer trainingDuration;



    public Training() {
    }
    public Training(Long id, Trainee trainee, Trainer trainer, String trainingName, String trainingType, LocalDateTime trainingDate, Integer trainingDuration) {

        this.id = id;
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }
}
