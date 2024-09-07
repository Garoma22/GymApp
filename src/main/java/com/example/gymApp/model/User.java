//package com.example.gymApp.model;
//
//import lombok.Data;
//@Data
//public class User {
//  private Long id;
//  private String firstName;
//  private String lastName;
//  private String username;
//  private String password;
//  private boolean isActive;
//
//  public User(Long id, String firstName, String lastName, String username, String password,
//      boolean isActive) {
//    this.id = id;
//    this.firstName = firstName;
//    this.lastName = lastName;
//    this.username = username;
//    this.password = password;
//    this.isActive = isActive;
//  }
//
//  public User() {
//  }
//}
//
//
//
//



package com.example.gymApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity  // Аннотация для обозначения класса как сущности базы данных
@Table(name = "users")  // Указание на таблицу в базе данных
@Data
public class User {

  @Id  // Указывает, что это первичный ключ
  @GeneratedValue(strategy = GenerationType.IDENTITY)  // Генерация значения ключа (например, автоинкремент)
  @Column(name = "id")  // Соответствие столбцу id в таблице
  private Long id;

  @Column(name = "first_name", nullable = false)  // Столбец для имени пользователя
  private String firstName;

  @Column(name = "last_name",nullable = false)  // Столбец для фамилии пользователя
  private String lastName;

  @Column(name = "username",nullable = false)  // Столбец для логина, должен быть уникальным
  private String username;

  @Column(name = "password", nullable = false)  // Столбец для пароля
  private String password;

  @Column(name = "is_active", nullable = false)  // Столбец для хранения активности пользователя (true/false)
  private boolean isActive;

  public User(Long id, String firstName, String lastName, String username, String password,
      boolean isActive) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.isActive = isActive;
  }

  public User() {
  }
}
