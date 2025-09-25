package com.weftecnologia.himitsu_kagi_api.entities;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VaultItem {

  private String serviceName;
  private String iv;
  private String passwordEncrypted;
  private LocalDate createdAt;
  private LocalDate updatedAt;
}
