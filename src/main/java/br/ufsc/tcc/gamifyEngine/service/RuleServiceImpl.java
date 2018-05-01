package br.ufsc.tcc.gamifyEngine.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import br.ufsc.tcc.gamifyEngine.model.Badge;
import br.ufsc.tcc.gamifyEngine.model.FeedbackAttributes;
import br.ufsc.tcc.gamifyEngine.model.FeedbackLevel;
import br.ufsc.tcc.gamifyEngine.model.FeedbackXp;
import br.ufsc.tcc.gamifyEngine.model.LevelReward;
import br.ufsc.tcc.gamifyEngine.model.LogEvent;
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
	
	@Autowired
	UserService userService;
	
	@Autowired
	LogService logService;
	
	List<FeedbackAttributes> attributesChanges = new ArrayList<FeedbackAttributes>();
	List<Badge> badgesChanges = new ArrayList<Badge>();
	FeedbackXp xpChanges = new FeedbackXp();
	FeedbackLevel levelChanges = new FeedbackLevel();
	
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
						this.levelChanges.setLevelIncreased(true);
						this.levelChanges.setNewLevel(user.getLevel());
						
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
					
					if(!user.getBadges().contains(ruleBadgeAtt.getBadge())) {
						for(UserAttribute userAttribute: user.getAttributes()) {
							if(userAttribute.getAttribute().getId() == ruleBadgeAtt.getAttribute().getId() && userAttribute.getValue() >= ruleBadgeAtt.getGoalValue() ) 
							{
								//TODO verificar se está no LOG
								user.getBadges().add(ruleBadgeAtt.getBadge());
								badgesChanges.add(ruleBadgeAtt.getBadge());
//								ruleBadgeAtt.getRule().setFinished(true);
								this.ruleBadgeDao.save(ruleBadgeAtt);
							}
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
							.forEach(attribute -> {
								attribute.setValue(attribute.getValue() + amount);
								
								boolean hasAttribute = false;
								
								for(FeedbackAttributes feedbackAtt : this.attributesChanges) {
									if(feedbackAtt.getAttribute().getId() == attribute.getAttribute().getId()) {
										hasAttribute = true;
										feedbackAtt.setAmountChanged(feedbackAtt.getAmountChanged() + amount);
									}
								}
								
								if(!hasAttribute) {
									this.attributesChanges.add(new FeedbackAttributes(attribute.getAttribute(), amount));
								}
							});
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
		Integer highestLevelRule = this.ruleLevelDao.findHighestLevelRange();
		
		if(highestLevelRule > 0) {
			if(highestLevelRule < user.getLevel())
				ruleLevel = this.ruleLevelDao.findByLevelRange(highestLevelRule);
			else
				ruleLevel = this.ruleLevelDao.findByLevelRange(user.getLevel());
			
			return ruleLevel;
			
		}
		return null;
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

	@Override
	public Iterable<LevelReward> getLevelRewardsByRuleLevel(int idRuleLevel) {
		return this.levelRewardDao.getLevelRewardsByRuleLevel(idRuleLevel);
	}

	@Override
	public Map<String, Object> processRule(int userId, int ruleId) {
		
		Map< String, Object > feedBackChanges = new HashMap< String, Object>();
		
		Rule rule = this.getRule(ruleId);
		User user = userService.getUser(userId);
		String ruleType = rule.getType();
		
		boolean completed = false;
		
		List<LogEvent> logs = this.logService.getLogByUserAndRule(userId, ruleId);
		int timesCompleted = logs.size();
		
		if(rule.getTimesToComplete() == timesCompleted) {
			completed = true;
		}
		
		User userUpdated = null;
		
		if(!completed) {
			
			timesCompleted++;
			
			//TODO ruleType troca pra enum depois
			switch (ruleType) {
			case "attribute":
				List<RuleAttribute> ruleAttributes = null;
				ruleAttributes = this.getRuleAttributeByRule(ruleId);

				//atualiza os atributos do usuário
				for (RuleAttribute ruleAttribute : ruleAttributes) {
					int amount = ruleAttribute.getAmount();
					int attId = ruleAttribute.getAttribute().getId();
					
					user.getAttributes().stream()
							.filter(attribute -> attribute.getAttribute().getId() == attId)
							.forEach( attribute -> {
								attribute.setValue(attribute.getValue() + amount);
								attributesChanges.add(new FeedbackAttributes(attribute.getAttribute(), amount));
							});
				}
				
				break;
			case "badge":
				RuleBadge ruleBadge = null;
				ruleBadge = this.getRuleBadgesByRule(ruleId);
				if(timesCompleted >= rule.getTimesToComplete())	{
					user.getBadges().add(ruleBadge.getBadge());
					badgesChanges.add(ruleBadge.getBadge());
					this.evaluate("badge", user, null);
				}
				break;
			default:
				break;
			}
			
			//sempre vai ganhar XP, mesmo que a rule não for totalmente completa
			if(rule.getXp() != 0) {
				user.setXp(user.getXp() + rule.getXp());
				user.setCurrentXp(user.getCurrentXp() + rule.getXp());
				
				xpChanges.setXpChanged(true);
				xpChanges.setAmountChanged(rule.getXp());
				xpChanges.setUserTotalXp(user.getXp());
				xpChanges.setUserNewCurrentXp(user.getCurrentXp());
				
				this.evaluate("xp", user, null);
				
			}

			userUpdated = userService.saveUser(user);
			
			//faz log do evento
			LogEvent logEvent = new LogEvent();
			logEvent.setRule(rule.getId());
			logEvent.setUser(user.getId());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			logEvent.setDateEvent(timestamp);
			this.logService.insertLog(logEvent);
		}
		
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		feedBackChanges.put("attributes", attributesChanges);
		feedBackChanges.put("badges", badgesChanges);
		feedBackChanges.put("experience", xpChanges);
		feedBackChanges.put("level", levelChanges);
		
		if(userUpdated != null)
			response.put("user", userUpdated);
		else
			response.put("user", user);
		
		response.put("feedBackChanges", feedBackChanges);
		
		return response;
	}

//	@Override
//	public LevelReward getLevelReward(int levelRewardId) {
//		return levelRewardDao.findById(levelRewardId);
//		
//	}
}