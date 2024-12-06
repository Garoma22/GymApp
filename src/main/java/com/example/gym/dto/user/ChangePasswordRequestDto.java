package com.example.gym.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePasswordRequestDto {

  @NotEmpty(message = "Old password is required")
  private String oldPassword;

  @NotEmpty(message = "New password is required")
  private String newPassword;
}
