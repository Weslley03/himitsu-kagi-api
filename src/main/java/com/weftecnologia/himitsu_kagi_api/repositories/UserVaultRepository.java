package com.weftecnologia.himitsu_kagi_api.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.weftecnologia.himitsu_kagi_api.dtos.user_vault.InsertVaultItemDTO;
import com.weftecnologia.himitsu_kagi_api.entities.UserVault;
import com.weftecnologia.himitsu_kagi_api.entities.VaultItem;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.database.DatabaseException;

@Repository
public class UserVaultRepository {
  private final MongoTemplate mongoTemplate;

  public UserVaultRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public UserVault createUserVault(String userId) {
    try {
      List<VaultItem> vaultItems = new ArrayList<>();
      UserVault entity = new UserVault(userId, vaultItems);
      return mongoTemplate.insert(entity, "app_user_vault");
    } catch (DataAccessException e) {
      throw new DatabaseException(
          "connection error with the database when trying to insert a user-vault. try again later.", e);
    }
  }

  public UserVault addVaultItem(String userVaultId, InsertVaultItemDTO dto) {
    try {
      Query query = new Query(Criteria.where("userId").is(userVaultId));
      Update update = new Update().push("vaultItem", dto);
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
      UserVault userVault = mongoTemplate.findAndModify(query, update, options, UserVault.class);

      if (userVault == null) {
        throw new RuntimeException("fail to insert a user-vault-item. user-vault with.");
      }

      return userVault;
    } catch (DataAccessException e) {
      throw new DatabaseException(
          "connection error with the database when trying to insert a user-vault-item. try again later.", e);
    }
  }

  public UserVault findByUserId(String userVaultId) {
    try {
      Query query = new Query(Criteria.where("_id").is(userVaultId));
      return mongoTemplate.findOne(query, UserVault.class);
    } catch (DataAccessException e) {
      throw new DatabaseException(
          "connection error with the database when trying to find a user-vault. try again later.", e);
    }
  }
}
