package com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions;

public class JwtValidationException extends RuntimeException {
  public JwtValidationException(String errorReason) {
    super(errorReason);
  }
}