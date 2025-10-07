package com.weftecnologia.himitsu_kagi_api.dtos.auth;

import com.weftecnologia.himitsu_kagi_api.dtos.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseAuthDTO {
  private int status;
  private String message;
  private UserDTO user;
  private String token;

  public ResponseAuthDTO(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
