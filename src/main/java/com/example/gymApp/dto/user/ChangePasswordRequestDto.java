package com.example.gymApp.dto.user;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
//  private String username;
  private String oldPassword;
  private String newPassword;
}
