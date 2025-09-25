package com.weftecnologia.himitsu_kagi_api.configs;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvVariables {
  private static final Dotenv dotenv = Dotenv.load();

  // -----validations methods----

  private static String validadeStringEnv(String envVar, String caller) {
    if (envVar == null || envVar.trim().isEmpty()) {
      throw new RuntimeException("invalid/empty environment variable: " + caller);
    }
    return envVar;
  }

  private static int validadeNumberEnv(String envVar, String caller) {
    try {
      return Integer.parseInt(envVar);
    } catch (NumberFormatException e) {
      throw new RuntimeException("environment variable is not a number: " + caller, e);
    }
  }

  // ----------------------------

  public static String getJWTSecretKey() {
    String envName = "JWT_SECRET_KEY";
    String env = dotenv.get(envName);
    return validadeStringEnv(env, envName);
  }

  // -----password managers envs----

  public static String getDefaultKdf() {
    String envName = "DEFAULT_KDF";
    String env = dotenv.get(envName);
    return validadeStringEnv(env, envName);
  }

  public static int getDefaultIterations() {
    String envName = "DEFAULT_ITERATIONS";
    String env = dotenv.get(envName);
    return validadeNumberEnv(env, envName);
  }

  public static int getSaltLength() {
    String envName = "SALT_LENGTH";
    String env = dotenv.get(envName);
    return validadeNumberEnv(env, envName);
  }

  public static int getKeyLength() {
    String envName = "KEY_LENGTH";
    String env = dotenv.get(envName);
    return validadeNumberEnv(env, envName);
  }

  // ----------------------------
}
