package com.example.gymApp.config;

public class JwtStorage {
  private static final ThreadLocal<String> jwtThreadLocal = new ThreadLocal<>();

  public static void setJwt(String jwt) {
    jwtThreadLocal.set(jwt);
  }

  public static String getJwt() {
    return jwtThreadLocal.get();
  }

  public static void clear() {
    jwtThreadLocal.remove();
  }
}
