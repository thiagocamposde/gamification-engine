package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.RuleDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleLevelDao;
import br.ufsc.tcc.gamifyEngine.dao.UserDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleAttributeDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleBadgeDao;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;
import br.ufsc.tcc.gamifyEngine.model.User;

@Service
public class RuleServiceImpl implements RuleService{
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RuleAttributeDao ruleAttributeDao;
	
	@Autowired
	private RuleBadgeDao ruleBadgeDao;

	@Autowired
	private RuleLevelDao ruleLevelDao;
	
	public RuleServiceImpl() {
			
	}
	
	@Override
	public Rule getRule(int ruleId) {
		return ruleDao.findById(ruleId);
	}

	@Override
	public Iterable<Rule> findAllRules() {
		return ruleDao.findAll();
	}

	@Override
	public RuleAttribute getRuleAttribute(int ruleAttributeId) {
		return ruleAttributeDao.findById(ruleAttributeId);
	}
	
	@Override
	public RuleBadge getRuleBadge(int ruleBadgeId) {
		return ruleBadgeDao.findById(ruleBadgeId);
	}

	@Override
	public List<RuleAttribute> getRuleAttributeByRule(int ruleId) {
		return ruleAttributeDao.findRuleAttributeByRuleId(ruleId);
	}
	

	@Override
	public RuleBadge getRuleBadgesByRule(int ruleId) {
		return this.ruleBadgeDao.findRuleBadgeByRuleId(ruleId);
		
	}

	@Override
	public RuleLevel getRuleLevel(int ruleLevelId) {
		return ruleLevelDao.findById(ruleLevelId);
	}

	@Override
	public void evaluate(String type, User user) { //TODO mudar pra enum
		
		switch (type) {
			case "xp":
				
				RuleLevel ruleLevel = this.findAdequatedRuleLevel(user);
				
				int currentUserXp = user.getCurrentXp();
				
				if(ruleLevel != null) {
					while(currentUserXp >= ruleLevel.getXpToLevelUp()) {
						user.setLevel(user.getLevel() + 1);
						user.setCurrentXp(user.getCurrentXp() - ruleLevel.getXpToLevelUp());
						currentUserXp = user.getCurrentXp();
						ruleLevel = this.findAdequatedRuleLevel(user);
					}
				}
				break;
			case "badge":
				//TODO
				break;
			case "attribute":
				break;
			case "level":
				break;
			default:
				break;
		}
	}

	@Override
	public RuleLevel findAdequatedRuleLevel(User user) {
		RuleLevel ruleLevel = null;
		int highestLevelRule = this.ruleLevelDao.findHighestLevelRange();
		
		if(highestLevelRule < user.getLevel())
			ruleLevel = this.ruleLevelDao.findByLevelRange(highestLevelRule);
		else
			ruleLevel = this.ruleLevelDao.findByLevelRange(user.getLevel());
		
		return ruleLevel;
	}

}