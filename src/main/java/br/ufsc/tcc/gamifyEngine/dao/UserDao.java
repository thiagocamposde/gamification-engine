package br.ufsc.tcc.gamifyEngine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.LevelReward;
import br.ufsc.tcc.gamifyEngine.model.User;

@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
  public User findById(int userId);

  @Modifying
  @Query(value = "DELETE FROM user_badges where user_id = ?1 and badge_id = ?2", nativeQuery = true)	
  public void deleteUserBadge(int userId, int badgeId);

}