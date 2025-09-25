package com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions;

public class CheckingPasswordException extends RuntimeException {
  public CheckingPasswordException(String errorMessage) {
    super(errorMessage);
  }
}
