package com.weftecnologia.himitsu_kagi_api.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.weftecnologia.himitsu_kagi_api.dtos.userConfig.CreateUserConfigDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.userConfig.UserConfigDTO;
import com.weftecnologia.himitsu_kagi_api.entities.UserConfig;

@Repository
public class UserConfigRepository {

  private final MongoTemplate mongoTemplate;

  public UserConfigRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public UserConfigDTO insertUserConfig(CreateUserConfigDTO dto) {
    try {
      UserConfig entity = new UserConfig(
          dto.getUserId(),
          dto.getPasswordSalt(),
          dto.getEncryptionSalt(),
          dto.getIterations(),
          dto.getKdf(),
          dto.getCreateAt(),
          dto.getUpdatedAt());

      UserConfig userConfig = mongoTemplate.insert(entity, "app_user_config");
      return new UserConfigDTO(
          userConfig.getId(),
          userConfig.getUserId(),
          userConfig.getPasswordSalt(),
          userConfig.getEncryptionSalt(),
          userConfig.getIterations(),
          userConfig.getKdf(),
          userConfig.getCreateAt(),
          userConfig.getUpdatedAt());
    } catch (DataAccessException e) {
      throw new RuntimeException(
          "connection error with the database when trying to insert a user-config. try again later.", e);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("general error when trying to insert user-config, try again later.", e);
    }
  }

  public UserConfig findByUserId(String userId) {
    try {
      Query findByUserIdQuery = Query.query(Criteria.where("userId").is(userId));
      return mongoTemplate.findOne(findByUserIdQuery, UserConfig.class);
    } catch (DataAccessException e) {
      throw new RuntimeException("could not find the user, try again later.");
    }
  }
}
