package com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions;

public class GenericNotFoundException extends RuntimeException {
  public GenericNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
