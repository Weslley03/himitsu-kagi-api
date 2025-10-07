package com.weftecnologia.himitsu_kagi_api.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.weftecnologia.himitsu_kagi_api.dtos.auth.AuthDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.auth.ResponseAuthDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.user.UserDTO;
import com.weftecnologia.himitsu_kagi_api.entities.User;
import com.weftecnologia.himitsu_kagi_api.entities.UserConfig;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.CheckingPasswordException;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.GenericNotFoundException;
import com.weftecnologia.himitsu_kagi_api.repositories.UserConfigRepository;
import com.weftecnologia.himitsu_kagi_api.repositories.UserRepository;
import com.weftecnologia.himitsu_kagi_api.security.JwtUtils;

@Service
public class AuthUserService {
  private final UserRepository userRepository;
  private final UserConfigRepository userConfigRepository;

  public AuthUserService(UserRepository userRepository, UserConfigRepository userConfigRepository) {
    this.userRepository = userRepository;
    this.userConfigRepository = userConfigRepository;
  }

  public ResponseAuthDTO authenticateUser(AuthDTO dto) {
    User user = userRepository.getUserByEmail(dto.getEmail());

    if (user == null) {
      throw new GenericNotFoundException("user with email " + dto.getEmail() + " not found.");
    }

    UserConfig userConfig = userConfigRepository.findByUserId(user.getId());

    if (userConfig == null) {
      throw new GenericNotFoundException("user-config with id " + user.getId() + " not found.");
    }

    String storedPasswordHash = user.getPasswordHash();

    boolean correctPassword = PasswordManagerService.verifyPassword(
        dto.getPassword(),
        storedPasswordHash,
        userConfig.getPasswordSalt(),
        userConfig.getIterations());

    if (correctPassword == false) {
      throw new CheckingPasswordException("invalid password.");
    }

    String token = JwtUtils.generateToken(user.getId(), user.getEmail());
    UserDTO userDTO = new UserDTO(user);
    return new ResponseAuthDTO(HttpStatus.OK.value(), "user authenticated!", userDTO, token);
  }
}
