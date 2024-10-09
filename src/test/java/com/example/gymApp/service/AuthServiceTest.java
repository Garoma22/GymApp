package com.example.gymApp.service;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.gymApp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {


  @InjectMocks
  private AuthService authService;

  @Mock
  private UserService userService;

  @Test
  void testAuthenticate_Success() {
    User mockUser = new User();
    when(userService.getUserByPasswordAndUsername("username", "password")).thenReturn(mockUser);

    boolean result = authService.authenticate("username", "password", new MockHttpServletRequest());

    assertTrue(result);
  }

  @Test
  void testAuthenticate_Fail() {
    when(userService.getUserByPasswordAndUsername("username", "password")).thenReturn(null);

    boolean result = authService.authenticate("username", "password", new MockHttpServletRequest());

    assertFalse(result);
  }
}
