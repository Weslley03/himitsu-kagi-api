package com.weftecnologia.himitsu_kagi_api.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUserDTO {
  @NotBlank
  private String name;
  @Email
  private String email;
  @Size(min = 8, message = "the password field must have at least 8 characters")
  private String password;
}
