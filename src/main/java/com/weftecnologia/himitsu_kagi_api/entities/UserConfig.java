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
@Document(collection = "app_user_config")
public class UserConfig {

  @Id
  private String id;
  private String userId;
  private String salt;
  private int iterations;
  private String kdf;
  private LocalDateTime createAt;
  private LocalDateTime updatedAt;

  // mongo takes care of inserting the id
  public UserConfig(String userId, String salt, int iterations,
      String kdf, LocalDateTime createAt, LocalDateTime updatedAt) {
    this.userId = userId;
    this.salt = salt;
    this.iterations = iterations;
    this.kdf = kdf;
    this.createAt = createAt;
    this.updatedAt = updatedAt;
  }
}
