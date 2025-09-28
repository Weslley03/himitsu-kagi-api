package com.weftecnologia.himitsu_kagi_api.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.weftecnologia.himitsu_kagi_api.dtos.user.InsertUserDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.user.RegisterUserDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.user.UserDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.userConfig.CreateUserConfigDTO;
import com.weftecnologia.himitsu_kagi_api.entities.User;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.UserAlreadyExistsException;
import com.weftecnologia.himitsu_kagi_api.repositories.UserConfigRepository;
import com.weftecnologia.himitsu_kagi_api.repositories.UserRepository;
import com.weftecnologia.himitsu_kagi_api.repositories.UserVaultRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserConfigRepository userConfigRepository;
  private final UserVaultRepository userVaultRepository;

  public UserService(UserRepository userRepository, UserConfigRepository userConfigRepository,
      UserVaultRepository userVaultRepository) {
    this.userRepository = userRepository;
    this.userConfigRepository = userConfigRepository;
    this.userVaultRepository = userVaultRepository;
  };

  public UserDTO registerUser(RegisterUserDTO dto) {
    User existingUser = userRepository.getUserByEmail(dto.getEmail());

    if (existingUser != null) {
      throw new UserAlreadyExistsException(dto.getEmail());
    }

    /*
     * String hash, String passwordSalt, String encryptoSalt, int iterations,
     * String, kdf
     */
    PasswordManagerService.PasswordHashResult hashResult = PasswordManagerService.hashPassword(dto.getPassword());
    InsertUserDTO insertUserDTO = new InsertUserDTO(
        dto.getName(),
        dto.getEmail(),
        hashResult.getHash(),
        LocalDateTime.now(),
        LocalDateTime.now());

    UserDTO userDto = userRepository.insertUser(insertUserDTO);

    // create user configurations
    CreateUserConfigDTO createUserConfigDTO = new CreateUserConfigDTO(
        userDto.getId(),
        hashResult.getPasswordSalt(),
        hashResult.getEncryptionSalt(),
        hashResult.getIterations(),
        hashResult.getKdf(),
        LocalDateTime.now(),
        LocalDateTime.now());

    userConfigRepository.insertUserConfig(createUserConfigDTO);

    userVaultRepository.createUserVault(userDto.getId());

    return userDto;
  }
}
