package com.weftecnologia.himitsu_kagi_api.dtos.user_vault;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPasswordRequest {
  @NotBlank
  private String serviceName;
  @Size(min = 8, message = "the password field must have at least 8 characters")
  @NotBlank
  private String plainPassword;
}
