package com.weftecnologia.himitsu_kagi_api.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.weftecnologia.himitsu_kagi_api.dtos.user_vault.InsertVaultItemDTO;
import com.weftecnologia.himitsu_kagi_api.entities.UserConfig;
import com.weftecnologia.himitsu_kagi_api.repositories.UserConfigRepository;
import com.weftecnologia.himitsu_kagi_api.repositories.UserVaultRepository;

@Service
public class UserVaultService {
  private final UserVaultRepository userVaultRepository;
  private final UserConfigRepository userConfigRepository;

  public UserVaultService(UserVaultRepository userVaultRepository, UserConfigRepository userConfigRepository) {
    this.userVaultRepository = userVaultRepository;
    this.userConfigRepository = userConfigRepository;
  }

  public void addPasswordToVault(String userId, String serviceName, String plainPassword) {
    UserConfig userConfig = userConfigRepository.findByUserId(userId);

    if (userConfig == null) {
      throw new RuntimeException("user-configuration not found for user: " + userId);
    }

    // encrypt the password
    EncryptionManagerService.EncryptionResult encryptionResult = EncryptionManagerService.encryptPassword(plainPassword,
        userConfig);

    InsertVaultItemDTO insertVaultItemDTO = new InsertVaultItemDTO(
        serviceName,
        encryptionResult.getIv(),
        encryptionResult.getEncryptedPassword(),
        LocalDateTime.now(),
        LocalDateTime.now());

    userVaultRepository.addVaultItem(userId, insertVaultItemDTO);
  }
}
