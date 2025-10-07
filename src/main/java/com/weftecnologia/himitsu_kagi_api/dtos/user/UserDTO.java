package com.weftecnologia.himitsu_kagi_api.dtos.user;

import java.time.LocalDateTime;

import com.weftecnologia.himitsu_kagi_api.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
  private String id;
  private String name;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public UserDTO(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.createdAt = user.getCreateAt();
    this.updatedAt = user.getUpdatedAt();
  }
}
