package com.weftecnologia.himitsu_kagi_api.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "app_user_vault")
public class UserVault {

  @Id
  private String id;
  private String userId;

  // String serviceName, String iv, String passwordEncrypted,
  // LocalDate createdAt, LocalDate updatedAt
  private List<VaultItem> vaultItem;
}
