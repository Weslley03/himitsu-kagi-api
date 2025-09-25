package com.weftecnologia.himitsu_kagi_api.dtos.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseApiDTO<T> {
  private int status;
  private String message;
  private T response;

  public ResponseApiDTO(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
