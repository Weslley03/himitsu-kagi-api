package com.weftecnologia.himitsu_kagi_api.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

import com.weftecnologia.himitsu_kagi_api.configs.EnvVariables;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
public class PasswordManagerService {

  private static final String DEFAULT_KDF = EnvVariables.getDefaultKdf();
  private static final int DEFAULT_ITERATIONS = EnvVariables.getDefaultIterations();
  private static final int SALT_LENGTH = EnvVariables.getSaltLength();
  private static final int KEY_LENGTH = EnvVariables.getKeyLength();

  public static PasswordHashResult hashPassword(String password) {
    return hashPassword(password, DEFAULT_ITERATIONS);
  }

  public static PasswordHashResult hashPassword(String password, int interations) {
    try {
      // generate password random salt
      byte[] passwordSalt = generateSalt();

      // generate encryptionSalt random salt
      byte[] encryptionSalt = generateSalt();

      // generate PBKDF2 hash
      PBEKeySpec spec = new PBEKeySpec(
          password.toCharArray(),
          passwordSalt,
          interations,
          KEY_LENGTH * 8); // bytes

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      byte[] hash = factory.generateSecret(spec).getEncoded();

      // convert to base64
      String passwordSaltBase64 = Base64.getEncoder().encodeToString(passwordSalt);
      String encryptionSaltBase64 = Base64.getEncoder().encodeToString(encryptionSalt);
      String hashBase64 = Base64.getEncoder().encodeToString(hash);

      return new PasswordHashResult(hashBase64, passwordSaltBase64, encryptionSaltBase64, interations, DEFAULT_KDF);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("error generating password hash", e);
    }
  }

  public static boolean verifyPassword(String password, String storedHash, String salt, int iterations) {
    try {
      byte[] saltBytes = Base64.getDecoder().decode(salt);
      byte[] storedHashBytes = Base64.getDecoder().decode(storedHash);

      PBEKeySpec spec = new PBEKeySpec(
          password.toCharArray(),
          saltBytes,
          iterations,
          storedHashBytes.length * 8);

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      byte[] testHash = factory.generateSecret(spec).getEncoded();

      // compare hashes
      return java.security.MessageDigest.isEqual(storedHashBytes, testHash);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("something went wrong while checking the password.", e);
    }
  }

  private static byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  // class to encapsulate all result
  @Getter
  @AllArgsConstructor
  public static class PasswordHashResult {
    private final String hash;
    private final String passwordSalt;
    private final String encryptionSalt;
    private final int iterations;
    private final String kdf;
  }
}
