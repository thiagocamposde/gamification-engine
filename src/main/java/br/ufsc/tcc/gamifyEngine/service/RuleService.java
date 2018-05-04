package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;
import java.util.Map;

import br.ufsc.tcc.gamifyEngine.model.Attribute;
import br.ufsc.tcc.gamifyEngine.model.LevelReward;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleBadgeAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;
import br.ufsc.tcc.gamifyEngine.model.User;

public interface RuleService {
	public Rule getRule(int ruleId);
	public Iterable<Rule> findAllRules();
	public RuleAttribute getRuleAttribute(int ruleAttributeId);
	public RuleBadge getRuleBadge(int ruleBadgeId);
	public List<RuleAttribute> getRuleAttributeByRule(int rule);
	public RuleLevel getRuleLevel(int ruleLevelId);
	public RuleLevel findAdequatedRuleLevel(User user);
	public RuleBadge getRuleBadgesByRule(int ruleId);
	void deleteRuleLevel(int ruleLevelId);
	void deleteRuleBadge(int ruleBadgeId);
	void deleteRuleAttribute(int ruleAttributeId);
	void deleteRule(int ruleId);
	public Rule saveRule(Rule currentRule);
	public RuleBadge saveRuleBadge(RuleBadge ruleBadge);
	public RuleLevel saveRuleLevel(RuleLevel ruleLevel);
	public RuleAttribute saveRuleAttribute(RuleAttribute ruleAttribute);
	public LevelReward saveLevelReward(LevelReward levelReward);
//	public LevelReward getLevelReward(int levelRewardId);
	public RuleBadgeAttribute saveRuleBadgeAttribute(RuleBadgeAttribute ruleBadgeAttribute);
	public Iterable<RuleAttribute> findAllAttributeRules();
	public Iterable<RuleBadge> findAllBadgeRules();
	public Iterable<RuleLevel> findAllLevelRules();
	
	/**
	 * Esta função verifica recursivamente, se após um usuário receber uma recompensa, 
	 * outras regras não poderão ser desencadeadas, gerando novas recompensas ou
	 * ou simplesmente fazendo atribuições necessárias.
	 * @param type tipo da recompensa conquistada (xp, level, attributo ou badge)
	 * @param user Usuário que recebeu a recompensa
	 * @param attribute. Pode ser null. Refere-se ao attributo que foi modificado
	 * 
	 **/
	public void evaluate(String type, User user, Attribute attribute);
	
	public Map<String, Object> processRule(int userId, int ruleId);
	public Iterable<LevelReward> getLevelRewardsByRuleLevel(int idRuleLevel);
	public List<Rule> getRuleByType(String type);
	
	
	
}

