package com.weftecnologia.himitsu_kagi_api.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthDTO {
  @Email
  private String email;
  @Size(min = 8, message = "the password field must have at least 8 characters")
  private String password;
}
