package com.example.gym.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {

  private static final int MAX_ATTEMPT = 3; //limit of attempts
  private static final long BLOCK_TIME_DURATION = 5; // block in minutes

 //attempt storage
  private final Map<String, Integer> attemptsCache = new HashMap<>();

  // time storage
  private final Map<String, LocalDateTime> blockTimeCache = new HashMap<>();

  // fail attempts counter
  public void loginFailed(String username) {
    int attempts = attemptsCache.getOrDefault(username, 0);
    attempts++;
    attemptsCache.put(username, attempts);

    if (attempts >= MAX_ATTEMPT) {
      blockTimeCache.put(username, LocalDateTime.now().plusMinutes(BLOCK_TIME_DURATION));
    }
  }

  // reset unsuccessful attempts
  public void loginSucceeded(String username) {
    attemptsCache.remove(username);
    blockTimeCache.remove(username);
  }

  // check of user block
  public boolean isBlocked(String username) {
    LocalDateTime blockTime = blockTimeCache.get(username);
    if (blockTime != null && LocalDateTime.now().isBefore(blockTime)) {
      return true;
    }
    return false;
  }

  // get time to unblock
  public long getRemainingBlockTime(String username) {
    LocalDateTime blockTime = blockTimeCache.get(username);
    if (blockTime != null && LocalDateTime.now().isBefore(blockTime)) {
      return java.time.Duration.between(LocalDateTime.now(), blockTime).toMinutes();
    }
    return 0;
  }
}

