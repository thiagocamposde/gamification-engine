package br.ufsc.tcc.gamifyEngine.service;

import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;

public interface RuleService {
	public Rule getRule(int ruleId);
	public Iterable<Rule> findAllRules();
	public RuleAttribute getRuleAttribute(int ruleAttributeId);
	public RuleBadge getRuleBadge(int ruleBadgeId);
	public RuleAttribute getRuleAttributeByRule(Rule rule);
	public RuleLevel getRuleLevel(int ruleLevelId);
}

