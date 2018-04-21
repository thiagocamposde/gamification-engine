package br.ufsc.tcc.gamifyEngine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufsc.tcc.gamifyEngine.dao.LevelRewardDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleAttributeDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleBadgeAttributeDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleBadgeDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleLevelDao;
import br.ufsc.tcc.gamifyEngine.dao.UserDao;
import br.ufsc.tcc.gamifyEngine.model.Attribute;
import br.ufsc.tcc.gamifyEngine.model.LevelReward;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleBadgeAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;
import br.ufsc.tcc.gamifyEngine.model.User;
import br.ufsc.tcc.gamifyEngine.model.UserAttribute;

@Service
public class RuleServiceImpl implements RuleService{
	
	@Autowired
	private RuleDao ruleDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LevelRewardDao levelRewardDao;	
	
	@Autowired
	private RuleAttributeDao ruleAttributeDao;
	
	@Autowired
	private RuleBadgeDao ruleBadgeDao;

	@Autowired
	private RuleLevelDao ruleLevelDao;
	
	@Autowired
	private RuleBadgeAttributeDao ruleBadgeAttributeDao;
	
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
	public void evaluate(String type, User user, Attribute att) { //TODO mudar pra enum
		
		RuleLevel ruleLevel = null;
		
		switch (type) {
			case "xp":
				
				ruleLevel = this.findAdequatedRuleLevel(user);
				
				int currentUserXp = user.getCurrentXp();
				
				if(ruleLevel != null) {
					while(currentUserXp >= ruleLevel.getXpToLevelUp()) {
						user.setLevel(user.getLevel() + 1);
						user.setCurrentXp(user.getCurrentXp() - ruleLevel.getXpToLevelUp());
						currentUserXp = user.getCurrentXp();
						this.evaluate("level", user, null);
						ruleLevel = this.findAdequatedRuleLevel(user);
					}
				}
				break;
			case "badge":
				//TODO asdasd
				break;
			case "attribute":
				//List<RuleBadgeAttribute> ruleBadgesAtt = this.ruleBadgeAttributeDao.findByAttributeId(att.getId());
				List<RuleBadge> ruleBadgesAtt = this.ruleBadgeDao.findByAttributeId(att.getId());
				
				for(RuleBadge ruleBadgeAtt: ruleBadgesAtt){
					
					for(UserAttribute userAttribute: user.getAttributes()) {
						if(userAttribute.getAttribute().getId() == ruleBadgeAtt.getAttribute().getId() && userAttribute.getValue() >= ruleBadgeAtt.getGoalValue()) 
						{
							//TODO verificar se está no LOG
							user.getBadges().add(ruleBadgeAtt.getBadge());
//							ruleBadgeAtt.getRule().setFinished(true);
							this.ruleBadgeDao.save(ruleBadgeAtt);
						}
					}
				}
				break;
			case "level":
				ruleLevel = this.findAdequatedRuleLevel(user);
				List<LevelReward> levelRewards = this.levelRewardDao.findCurrentLevelReward(ruleLevel.getId());
				
				//atualiza os atributos do usuário
				for (LevelReward levelReward : levelRewards) {
					int amount = levelReward.getAmount();
					int attId = levelReward.getAttribute().getId();

					user.getAttributes().stream()
							.filter(attribute -> attribute.getAttribute().getId() == attId)
							.forEach(attribute -> attribute.setValue(attribute.getValue() + amount));
					
					
					this.evaluate("attribute", user, levelReward.getAttribute());
				}
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

	@Override
	public void deleteRule(int ruleId) {
		this.ruleDao.delete(ruleId);
	}

	@Override
	public void deleteRuleAttribute(int ruleAttributeId) {
		this.ruleAttributeDao.delete(ruleAttributeId);
	}

	@Override
	public void deleteRuleBadge(int ruleBadgeId) {
		this.ruleBadgeDao.delete(ruleBadgeId);
		
	}

	@Override
	public void deleteRuleLevel(int ruleLevelId) {
		this.ruleLevelDao.delete(ruleLevelId);
	}

	@Override
	public Rule saveRule(Rule currentRule) {
		return this.ruleDao.save(currentRule);
	}

	@Override
	public RuleBadge saveRuleBadge(RuleBadge ruleBadge) {
		return this.ruleBadgeDao.save(ruleBadge);
		
	}

	@Override
	public RuleLevel saveRuleLevel(RuleLevel ruleLevel) {
		return this.ruleLevelDao.save(ruleLevel);		
	}

	@Override
	public RuleAttribute saveRuleAttribute(RuleAttribute ruleAttribute) {
		return this.ruleAttributeDao.save(ruleAttribute);
	}

	@Override
	public LevelReward saveLevelReward(LevelReward levelReward) {
		return this.levelRewardDao.save(levelReward); 
	}

	@Override
	public RuleBadgeAttribute saveRuleBadgeAttribute(RuleBadgeAttribute ruleBadgeAttribute) {
		return this.ruleBadgeAttributeDao.save(ruleBadgeAttribute); 
	}

	@Override
	public Iterable<RuleAttribute> findAllAttributeRules() {
		return this.ruleAttributeDao.findAll();
	}

	@Override
	public Iterable<RuleBadge> findAllBadgeRules() {
		return this.ruleBadgeDao.findAll();
	}

	@Override
	public Iterable<RuleLevel> findAllLevelRules() {
		return this.ruleLevelDao.findAll();		
	}

//	@Override
//	public LevelReward getLevelReward(int levelRewardId) {
//		return levelRewardDao.findById(levelRewardId);
//		
//	}
}