package br.ufsc.tcc.gamifyEngine.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.ufsc.tcc.gamifyEngine.dao.LevelRewardDao;
import br.ufsc.tcc.gamifyEngine.dao.RuleBadgeDao;
import br.ufsc.tcc.gamifyEngine.service.AttributeService;
import br.ufsc.tcc.gamifyEngine.service.BadgeService;
import br.ufsc.tcc.gamifyEngine.service.LogService;
import br.ufsc.tcc.gamifyEngine.service.RuleService;
import br.ufsc.tcc.gamifyEngine.service.UserService;


public class Engine {
	
	@Autowired
	UserService userService;

	@Autowired
	RuleService ruleService;

	@Autowired
	AttributeService attributeService;

	@Autowired
	BadgeService badgeService;
	
	@Autowired
	LogService logService;
	
	LevelRewardDao levelRewardDao;
	
	RuleBadgeDao ruleBadgeDao;
	
	public Engine() {
		
	}
	
	public Map<String, Object> processEvent(int ruleId, int userId) {
		Rule rule = this.ruleService.getRule(ruleId);
		User user = userService.getUser(userId);
		String ruleType = rule.getType();
		
		Map< String, Map<Attribute, Integer> > diff = new HashMap< String, Map<Attribute, Integer> >();
		
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
				ruleAttributes = ruleService.getRuleAttributeByRule(ruleId);
				
				Map<Attribute, Integer> attributesMap = new HashMap<Attribute, Integer>();

				//atualiza os atributos do usuário
				for (RuleAttribute ruleAttribute : ruleAttributes) {
					int amount = ruleAttribute.getAmount();
					int attId = ruleAttribute.getAttribute().getId();

					user.getAttributes().stream()
							.filter(attribute -> attribute.getAttribute().getId() == attId)
							.forEach( attribute -> {
								attribute.setValue(attribute.getValue() + amount);
								attributesMap.put(attribute.getAttribute(), amount);
							});
								
				}
				
				diff.put("attributes", attributesMap);
				
				break;
			case "badge":
				RuleBadge ruleBadge = null;
				ruleBadge = ruleService.getRuleBadgesByRule(ruleId);
				if(timesCompleted >= rule.getTimesToComplete())	{
					user.getBadges().add(ruleBadge.getBadge());
					this.ruleService.evaluate("badge", user, null);
				}
				break;					
			default:
				break;
			}
			
			//sempre vai ganhar XP, mesmo que a rule não for totalmente completa
			user.setXp(user.getXp() + rule.getXp());
			user.setCurrentXp(user.getCurrentXp() + rule.getXp());
			
			this.ruleService.evaluate("xp", user, null);

			userUpdated = userService.saveUser(user);
			
			//faz log do evento
			LogEvent logEvent = new LogEvent();
			logEvent.setRule(rule.getId());
			logEvent.setUser(user.getId());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			logEvent.setDateEvent(timestamp);
			this.logService.insertLog(logEvent);
		}
		
		
		
//		example.put("newLevel", "2");
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("user", userUpdated);
		response.put("diff", diff);
		
		return response;
		
		
	}
	
	public void evaluate(String type, User user, Attribute att) { //TODO mudar pra enum
		
		RuleLevel ruleLevel = null;
		
		switch (type) {
			case "xp":
				
				ruleLevel = this.ruleService.findAdequatedRuleLevel(user);
				
				int currentUserXp = user.getCurrentXp();
				
				if(ruleLevel != null) {
					while(currentUserXp >= ruleLevel.getXpToLevelUp()) {
						user.setLevel(user.getLevel() + 1);
						user.setCurrentXp(user.getCurrentXp() - ruleLevel.getXpToLevelUp());
						currentUserXp = user.getCurrentXp();
						this.evaluate("level", user, null);
						ruleLevel = this.ruleService.findAdequatedRuleLevel(user);
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
				ruleLevel = this.ruleService.findAdequatedRuleLevel(user);
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
}
