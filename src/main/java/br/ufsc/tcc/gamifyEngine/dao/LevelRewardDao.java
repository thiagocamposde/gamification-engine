package br.ufsc.tcc.gamifyEngine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufsc.tcc.gamifyEngine.model.LevelReward;

public interface LevelRewardDao  extends CrudRepository<LevelReward, Integer> {
	
	@Query("select r from LevelReward r where r.ruleLevel.id = ?2")
	public List<LevelReward> findCurrentLevelReward(int levelRewardId);
}
