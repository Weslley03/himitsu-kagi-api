package com.weftecnologia.himitsu_kagi_api.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.weftecnologia.himitsu_kagi_api.dtos.user.InsertUserDTO;
import com.weftecnologia.himitsu_kagi_api.dtos.user.UserDTO;
import com.weftecnologia.himitsu_kagi_api.entities.User;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.database.DatabaseException;

@Repository
public class UserRepository {

  private final MongoTemplate mongoTemplate;

  public UserRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public UserDTO insertUser(InsertUserDTO dto) {
    try {
      User entity = new User(
          dto.getName(),
          dto.getEmail(),
          dto.getPasswordHash(),
          dto.getCreateAt(),
          dto.getUpdatedAt());

      User user = mongoTemplate.insert(entity, "app_user");
      return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getCreateAt(), user.getUpdatedAt());
    } catch (DataAccessException e) {
      throw new DatabaseException(
          "connection error with the database when trying to insert a user. try again later.", e);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("general error when trying to insert user, try again later.", e);
    }
  }

  public User getUserByEmail(String email) {
    try {
      Query findByEmailQuery = Query.query(Criteria.where("email").is(email));
      return mongoTemplate.findOne(findByEmailQuery, User.class);
    } catch (DataAccessException e) {
      throw new DatabaseException("could not find the user, try again later.", e);
    }
  }
}