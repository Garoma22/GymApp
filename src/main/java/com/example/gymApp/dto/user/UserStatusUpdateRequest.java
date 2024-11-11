package com.example.gymApp.dto.user;

import lombok.Data;

@Data
public class UserStatusUpdateRequest {
  private String username;
  private String newActiveStatus;
}
