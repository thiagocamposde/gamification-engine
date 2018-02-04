package br.ufsc.tcc.gamifyEngine.service;

import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;

public interface RuleService {
	public Rule getRule(int ruleId);
	public Iterable<Rule> findAllRules();
	public RuleAttribute getRuleAttribute(int ruleAttributeId);
}
