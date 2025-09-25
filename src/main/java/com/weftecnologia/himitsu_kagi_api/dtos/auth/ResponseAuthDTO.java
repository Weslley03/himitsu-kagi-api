package com.weftecnologia.himitsu_kagi_api.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseAuthDTO {
  private int status;
  private String message;
  private String token;

  public ResponseAuthDTO(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
