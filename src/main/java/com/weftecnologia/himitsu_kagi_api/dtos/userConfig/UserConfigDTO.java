package com.weftecnologia.himitsu_kagi_api.dtos.userConfig;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserConfigDTO {
  private String id;
  private String userId;
  private String passwordSalt;
  private String encryptionSalt;
  private int iterations;
  private String kdf;
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;
}
