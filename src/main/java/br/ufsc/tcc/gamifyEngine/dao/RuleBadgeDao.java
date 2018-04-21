package br.ufsc.tcc.gamifyEngine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleBadgeAttribute;

@Transactional
public interface RuleBadgeDao extends CrudRepository<RuleBadge, Integer> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
	
	public RuleBadge findById(int ruleAttributeId);

	@Query("select r from RuleBadge r where r.rule.id = ?1")
	public RuleBadge findRuleBadgeByRuleId(int ruleId);
	
	public List<RuleBadge> findByAttributeId(int attributeId);
}