//package com.example.gymApp.model;
//
//
//import lombok.Data;
//
//
//@Data
//public class Trainer extends User {
//
//
//  private String specialization;
//
//
//  public Trainer(Long id, String firstName, String lastName, String username, String password,
//      boolean isActive, String specialization) {
//    super(id, firstName, lastName, username, password, isActive);
//    this.specialization = specialization;
//  }
//
//
//  public Trainer() {
//  }
//
//
//  @Override
//  public String toString() {
//    return "Trainer{" +
//        "id=" + getId() +
//        ", firstName='" + getFirstName() + '\'' +
//        ", lastName='" + getLastName() + '\'' +
//        ", username='" + getUsername() + '\'' +
//        ", password='" + getPassword() + '\'' +
//        ", isActive=" + isActive() +
//        ", specialization='" + specialization + '\'' +
//        '}';
//  }
//}



//package com.example.gymApp.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "trainers")
//public class Trainer {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @OneToOne
//  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//  private User user;  // Связь с User через внешний ключ
//
//  private String specialization;
//
//  public Trainer(Long id, User user, String specialization) {
//    this.id = id;
//    this.user = user;
//    this.specialization = specialization;
//  }
//
//  public Trainer() {
//  }
//
//  @Override
//  public String toString() {
//    return "Trainer{" +
//        "id=" + id +
//        ", user=" + user +
//        ", specialization='" + specialization + '\'' +
//        '}';
//  }
//
//  public String getUsername() {
//    return user.getUsername();
//  }
//}


package com.example.gymApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "trainers")
public class Trainer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "training_type_id", nullable = false)
  private TrainingType specialization; // Это теперь ссылка на TrainingType

  public Trainer() {}

  public Trainer(User user, TrainingType specialization) {
    this.user = user;
    this.specialization = specialization;
  }

  @Override
  public String toString() {
    return "Trainer{" +
        "id=" + id +
        ", user=" + user.getUsername() +
        ", specialization=" + specialization.getName() +
        '}';
  }

  public String getUsername() {
    return user.getUsername();
  }
}
