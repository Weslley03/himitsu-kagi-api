package com.weftecnologia.himitsu_kagi_api.dtos.user;

import com.weftecnologia.himitsu_kagi_api.entities.UserConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponseDTO {
  private UserDTO user;
  private UserConfig userConfig;
}
