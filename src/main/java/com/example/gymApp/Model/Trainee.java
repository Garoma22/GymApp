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






    public Trainee(Long id, String firstName, String lastName, String username,
                   String password, boolean isActive,
                   LocalDate dateOfBirth, String address) {
        super(id, firstName, lastName, username, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    // Пустой конструктор
    public Trainee() {
    }

    @Override
    public String toString() {
        return "Trainee{" + " password: " +this.getPassword() + " username: " +this.getUsername() +
                " dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}
