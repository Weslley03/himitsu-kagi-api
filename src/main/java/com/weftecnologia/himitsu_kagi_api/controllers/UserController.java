package com.weftecnologia.himitsu_kagi_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weftecnologia.himitsu_kagi_api.dtos.api.ResponseApiDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.user.RegisterUserDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.user.UserDTO;
import com.weftecnologia.himitsu_kagi_api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseApiDTO<UserDTO> registerUser(@Valid @RequestBody RegisterUserDTO dto) {
    UserDTO userResponse = userService.registerUser(dto);
    return new ResponseApiDTO<UserDTO>(
        HttpStatus.CREATED.value(),
        "user registered successfully.",
        userResponse);
  }
}
