package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;
import br.ufsc.tcc.gamifyEngine.model.User;

public interface RuleService {
	public Rule getRule(int ruleId);
	public Iterable<Rule> findAllRules();
	public RuleAttribute getRuleAttribute(int ruleAttributeId);
	public RuleBadge getRuleBadge(int ruleBadgeId);
	public List<RuleAttribute> getRuleAttributeByRule(int rule);
	public RuleLevel getRuleLevel(int ruleLevelId);
	
	/**
	 * Esta função verifica recursivamente, se após um usuário receber uma recompensa, 
	 * outras regras não poderão ser desencadeadas, gerando novas recompensas ou
	 * ou simplesmente fazendo atribuições necessárias.
	 * @param type tipo da recompensa conquistada (xp, level, attributo ou badge)
	 * @param user Usuário que recebeu a recompensa
	 * 
	 **/
	public void evaluate(String type, User user);
	public RuleLevel findAdequatedRuleLevel(User user);
}

