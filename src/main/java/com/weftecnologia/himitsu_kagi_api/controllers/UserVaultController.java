package com.weftecnologia.himitsu_kagi_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weftecnologia.himitsu_kagi_api.dtos.api.ResponseApiDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.user_vault.AddPasswordRequest;
import com.weftecnologia.himitsu_kagi_api.security.JwtUtils;
import com.weftecnologia.himitsu_kagi_api.services.UserVaultService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user-vault")
public class UserVaultController {

  private final UserVaultService userVaultService;

  public UserVaultController(UserVaultService userVaultService) {
    this.userVaultService = userVaultService;
  }

  @PostMapping("/add-password")
  public ResponseApiDTO<?> addPasswordToVault(@Valid @RequestBody AddPasswordRequest request,
      @RequestHeader(value = "Authorization", required = false) String authHeader) {
    String userId = JwtUtils.JwtValidationMiddleware(authHeader);
    userVaultService.addPasswordToVault(userId, request.getServiceName(), request.getPlainPassword());
    return new ResponseApiDTO<>(HttpStatus.OK.value(), "password saved successfully");
  }
}
