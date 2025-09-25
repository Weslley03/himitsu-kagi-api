package com.weftecnologia.himitsu_kagi_api.dtos.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertUserDTO {
  private String name;
  private String email;
  private String passwordHash;
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;
}
