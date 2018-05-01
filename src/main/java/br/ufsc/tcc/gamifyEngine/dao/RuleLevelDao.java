package br.ufsc.tcc.gamifyEngine.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;

@Transactional
public interface RuleLevelDao extends CrudRepository<RuleLevel, Integer> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
	
	public RuleLevel findById(int ruleLevelId);
	
	@Query("select r from RuleLevel r where r.startLevel <= ?1 and r.endLevel >= ?1")
	public RuleLevel findByLevelRange(int currentUserLevel);

	@Query(value="select coalesce(max(r.end_level),0) as maxLevel from rule_level r", nativeQuery=true)
	public int findHighestLevelRange();
}