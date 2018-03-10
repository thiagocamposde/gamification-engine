package br.ufsc.tcc.gamifyEngine.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.RuleBadge;

@Transactional
public interface RuleBadgeDao extends CrudRepository<RuleBadge, Long> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
	
	public RuleBadge findById(int ruleAttributeId);

	@Query("select r from RuleBadge r where r.rule.id = ?1")
	public RuleBadge findRuleBadgeByRuleId(int ruleId);
}