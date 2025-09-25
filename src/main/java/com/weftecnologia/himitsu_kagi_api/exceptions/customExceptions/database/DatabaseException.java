package com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.database;

public class DatabaseException extends RuntimeException {
  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }
}
