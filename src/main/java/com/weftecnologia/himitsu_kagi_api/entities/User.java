package com.weftecnologia.himitsu_kagi_api.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "app_user")
public class User {

  @Id
  private String id;
  private String name;
  private String email;
  private String passwordHash; // PBKDF2
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;

  // mongodb takes care of inserting the id.
  public User(String name, String email, String passwordHash, LocalDateTime createAt, LocalDateTime updatedAt) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.createAt = createAt;
    this.updatedAt = updatedAt;
  }
}
