package com.example.gymApp.Model;


import lombok.Data;





@Data
//@Entity
//@Table(name = "users")
//@Inheritance(strategy = InheritanceType.JOINED) // So that other entities can inherit User
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "first_name")
    private String firstName;

//    @Column(name = "last_name")
    private String lastName;

//    @Column(name = "username", unique = true)
    private String username;

//    @Column(name = "password")
    private String password;

//    @Column(name = "is_active")
    private boolean isActive;

    public User(Long id, String firstName, String lastName, String username, String password, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    // Пустой конструктор
    public User() {}
}




