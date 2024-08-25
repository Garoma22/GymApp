package com.example.gymApp.Model;


import lombok.Data;


@Data
//@Entity
//@Table(name = "trainers")
public class Trainer extends User {

//    @Column(name = "specialization")
    private String specialization;




    public Trainer(Long id, String firstName, String lastName, String username, String password, boolean isActive, String specialization) {
        // Вызов конструктора родительского класса
        super(id, firstName, lastName, username, password, isActive);
        this.specialization = specialization;
    }


    public Trainer(){};
}

