package com.example.gym.dto.user;

import lombok.Data;

@Data
public class UserStatusUpdateRequest {
  private String username;
  private String newActiveStatus;
}
