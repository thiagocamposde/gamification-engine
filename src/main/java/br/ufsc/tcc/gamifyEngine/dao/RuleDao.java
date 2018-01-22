package br.ufsc.tcc.gamifyEngine.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleReward;

@Transactional
public interface RuleDao extends CrudRepository<Rule, Long> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
  public Rule findById(long ruleId);

  public RuleReward findRuleRewardById(long ruleId);

}