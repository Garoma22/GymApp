package com.example.gymApp.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
public class User implements UserDetails { // UserDetails is needed for Spring Security. It provides an info about user status

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name",nullable = false)
  private String lastName;

  @Column(name = "username",nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "is_active", nullable = false)
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

  @Enumerated(EnumType.STRING)
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
