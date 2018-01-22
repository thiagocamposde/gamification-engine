package br.ufsc.tcc.gamifyEngine.service;

import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleReward;

public interface RuleService {
	public Rule getRule(long ruleId);
	public Iterable<Rule> findAllRules();
	public RuleReward getRuleReward(long ruleId);
}
