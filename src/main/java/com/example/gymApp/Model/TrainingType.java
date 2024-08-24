package com.example.gymApp.Model;


import lombok.Data;


@Data
//@Entity
//@Table(name = "training_types")
public class TrainingType {


    public TrainingType() {
    }

    public TrainingType(String typeName) {
        this.typeName = typeName;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

}

