package com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String email) {
    super("user with email " + email + " already exist.");
  }
}
