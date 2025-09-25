package com.weftecnologia.himitsu_kagi_api.dtos.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
  private String id;
  private String name;
  private String email;
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;
}
