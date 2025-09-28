package com.weftecnologia.himitsu_kagi_api.services;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.weftecnologia.himitsu_kagi_api.entities.UserConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Service
public class EncryptionManagerService {

  public static EncryptionResult encryptPassword(String plainPassword, UserConfig userConfig) {
    try {
      // generate random IV
      SecureRandom random = new SecureRandom();
      byte[] iv = new byte[16];
      random.nextBytes(iv);
      String ivBase64 = Base64.getEncoder().encodeToString(iv);

      String algorithm = "PBKDF2WithHmacSHA256";
      SecretKeyFactory factory;
      try {
        factory = SecretKeyFactory.getInstance(algorithm);
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(
            "have a problem with parameter algorithm, the value " + algorithm + " is not valid.");
      }

      PBEKeySpec spec = new PBEKeySpec(
          plainPassword.toCharArray(),
          Base64.getDecoder().decode(userConfig.getEncryptionSalt()),
          userConfig.getIterations(),
          256);

      String keySpec = "AES";
      SecretKey secretKey;
      try {
        secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), keySpec);
      } catch (InvalidKeySpecException e) {
        throw new RuntimeException("have a problem with parameter keySpec, the value " + keySpec + " is not valid.");
      }

      // Encrypt
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
      byte[] encryptedPassword = cipher.doFinal(plainPassword.getBytes(StandardCharsets.UTF_8));
      String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);

      return new EncryptionResult(encryptedPasswordBase64, ivBase64);
    } catch (

    Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  // helper class for encryption result
  @Getter
  @AllArgsConstructor
  public static class EncryptionResult {
    private final String encryptedPassword;
    private final String iv;
  }
}
