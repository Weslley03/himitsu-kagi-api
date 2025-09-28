package com.weftecnologia.himitsu_kagi_api.dtos.user_vault;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertVaultItemDTO {
  private String serviceName;
  private String iv;
  private String passwordEncrypted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
