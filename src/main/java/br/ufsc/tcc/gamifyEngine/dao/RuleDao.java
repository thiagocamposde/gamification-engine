package br.ufsc.tcc.gamifyEngine.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.ufsc.tcc.gamifyEngine.model.Rule;

@Transactional
public interface RuleDao extends CrudRepository<Rule, Integer> {

  /**
   * This method will find an User instance in the database by its email.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
  public Rule findById(int ruleId);

}