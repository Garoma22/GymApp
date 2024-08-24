package com.example.gymApp.Model;






import lombok.Data;

import java.time.LocalDate;


@Data
//@Entity
//@Table(name = "trainees")
public class Trainee extends User {

//    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

//    @Column(name = "address")
    private String address;


    // Конструктор для Trainee, включая поля родителя
    public Trainee(Long id, String firstName, String lastName, String username, String password, boolean isActive,
                   LocalDate dateOfBirth, String address) {
        super(id, firstName, lastName, username, password, isActive); // Вызов конструктора родителя
        this.dateOfBirth = dateOfBirth; // Поля Trainee
        this.address = address;
    }

    // Пустой конструктор
    public Trainee() {}




}
