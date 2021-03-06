package br.ufsc.tcc.gamifyEngine.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;

@Transactional
public interface RuleAttributeDao extends CrudRepository<RuleAttribute, Integer> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
	
	public RuleAttribute findById(int ruleAttributeId);
	
	@Query("select r from RuleAttribute r where r.rule.id = ?1")
	public List<RuleAttribute> findRuleAttributeByRuleId(int ruleId);
}