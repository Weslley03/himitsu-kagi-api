package com.weftecnologia.himitsu_kagi_api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weftecnologia.himitsu_kagi_api.dtos.auth.AuthDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.auth.ResponseAuthDTO;
import com.weftecnologia.himitsu_kagi_api.services.AuthUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthUserService authUserService;

  public AuthController(AuthUserService authUserService) {
    this.authUserService = authUserService;
  }

  @PostMapping
  public ResponseAuthDTO registerUser(@Valid @RequestBody AuthDTO dto) {
    return authUserService.authenticateUser(dto);
  }
}
