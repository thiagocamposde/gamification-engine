package br.ufsc.tcc.gamifyEngine.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufsc.tcc.gamifyEngine.compositeKeys.LevelRewardKey;
import br.ufsc.tcc.gamifyEngine.compositeKeys.RuleAttributeKey;
import br.ufsc.tcc.gamifyEngine.model.Attribute;
import br.ufsc.tcc.gamifyEngine.model.Badge;
import br.ufsc.tcc.gamifyEngine.model.LevelReward;
import br.ufsc.tcc.gamifyEngine.model.LogEvent;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleBadgeAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;
import br.ufsc.tcc.gamifyEngine.model.User;
import br.ufsc.tcc.gamifyEngine.model.UserAttribute;
import br.ufsc.tcc.gamifyEngine.service.AttributeService;
import br.ufsc.tcc.gamifyEngine.service.BadgeService;
import br.ufsc.tcc.gamifyEngine.service.LogService;
import br.ufsc.tcc.gamifyEngine.service.RuleService;
import br.ufsc.tcc.gamifyEngine.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class RestApiController {

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
	
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	
	/**
	 * 
	 *  -------------------------------------- USER ------------------------------------
	 * 
	 * **/
	
	@RequestMapping(value = "/users/", method = RequestMethod.GET)
	public ResponseEntity<?> listAllUsers() {
		List<User> usersList = new ArrayList<User>();
		Iterable<User> users = userService.findAllUsers();

		for (User user : users) {
			usersList.add(user);
		}

		System.out.println(usersList);

		if (usersList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(usersList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable int userId) {
		User user = userService.getUser(userId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/", method = RequestMethod.POST)
	public ResponseEntity<?> postUser(@RequestBody User user) {
		
		user.setAttributes(new ArrayList<>());
		user.setBadges(new ArrayList<>());
		
		User newUser = this.userService.saveUser(user);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> putUser(@PathVariable int userId, @RequestBody User user) {
		
		logger.info("Updating User with id {}", userId);
		
		User currentUser = userService.getUser(userId);
		
		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", userId);
		    return new ResponseEntity<>("Unable to update. User with id " + userId + " not found.", HttpStatus.NOT_FOUND);
		}
		
		currentUser.setActive(user.isActive());
		currentUser.setXp(user.getXp());
		currentUser.setLevel(user.getLevel());
		currentUser.setCurrentXp(user.getCurrentXp());
		
		//tratamento necessário para que o Hibernate não perca referência do attribute
		currentUser.getAttributes().clear();
		currentUser.getAttributes().addAll(user.getAttributes());
		currentUser.setAttributes(currentUser.getAttributes());
		
		currentUser.setBadges(user.getBadges());
		
		currentUser.getAttributes().stream().forEach(attUser -> {
			attUser.setId(new RuleAttributeKey(userId, attUser.getAttribute().getId()));
		});
		
        this.userService.saveUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable int userId) {
		try {
			userService.deleteUser(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 
	 *  -------------------------------------- ATTRIBUTE ------------------------------------
	 * 
	 * **/
	
	@RequestMapping(value = "/attributes/", method = RequestMethod.GET)
	public ResponseEntity<?> getAttributes() {
		
		try {
			Iterable<Attribute> atts = attributeService.findAllAttributes();
			
			List<Attribute> attrList = new ArrayList<Attribute>();

			for (Attribute attribute : atts) {
				attrList.add(attribute);
			}
			
			
			return new ResponseEntity<>(attrList, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/attributes/{attributeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAttribute(@PathVariable int attributeId) {
		Attribute att = attributeService.getAttribute(attributeId);
		if (att == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(att, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/attributes/", method = RequestMethod.POST)
	public ResponseEntity<?> postAttribute(@RequestBody Attribute attribute) {
		
		Attribute newAttribute = this.attributeService.saveAttribute(attribute);
		return new ResponseEntity<>(newAttribute, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/attributes/{attributeId}", method = RequestMethod.PUT)
	public ResponseEntity<?> putAttribute(@PathVariable int attributeId, @RequestBody Attribute attribute) {
		Attribute currentAttribute = this.attributeService.getAttribute(attributeId);
		
		if (currentAttribute == null) {
			logger.error("Unable to update. Attribute with id {} not found.", attributeId);
		    return new ResponseEntity<>("Unable to update. Attribute with id " + attributeId + " not found.", HttpStatus.NOT_FOUND);
		}
		currentAttribute.setDescription(attribute.getDescription());
		currentAttribute.setName(attribute.getName());
		
        this.attributeService.saveAttribute(currentAttribute);
        return new ResponseEntity<Attribute>(currentAttribute, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/attributes/{attributeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAttribute(@PathVariable int attributeId) {
		try {
			attributeService.deleteAttribute(attributeId);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_GATEWAY);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 
	 *  -------------------------------------- BADGE ------------------------------------
	 * 
	 **/
	
	@RequestMapping(value = "/badges/", method = RequestMethod.GET)
	public ResponseEntity<?> getbadges() {
		
		try {
			Iterable<Badge> badgesList = badgeService.findAllbadges();
			
			List<Badge> attrList = new ArrayList<Badge>();

			for (Badge badge : badgesList) {
				attrList.add(badge);
			}
			
			
			return new ResponseEntity<>(attrList, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badges/{badgeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBadge(@PathVariable int badgeId) {
		Badge att = badgeService.getBadge(badgeId);
		if (att == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(att, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badges/", method = RequestMethod.POST)
	public ResponseEntity<?> postBadge(@RequestBody Badge badge) {
		
		Badge newBadge = this.badgeService.saveBadge(badge);
		return new ResponseEntity<>(newBadge, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badges/{badgeId}", method = RequestMethod.PUT)
	public ResponseEntity<?> putBadge(@PathVariable int badgeId, @RequestBody Badge badge) {
		Badge currentBadge = this.badgeService.getBadge(badgeId);
		
		if (currentBadge == null) {
			logger.error("Unable to update. Badge with id {} not found.", badgeId);
		    return new ResponseEntity<>("Unable to update. Badge with id " + badgeId + " not found.", HttpStatus.NOT_FOUND);
		}
		currentBadge.setDescription(badge.getDescription());
		currentBadge.setName(badge.getName());
		
        this.badgeService.saveBadge(currentBadge);
        return new ResponseEntity<Badge>(currentBadge, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/badges/{badgeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBadge(@PathVariable int badgeId) {
		try {
			badgeService.deleteBadge(badgeId);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_GATEWAY);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * 
	 *  -------------------------------------- RULE ------------------------------------
	 * 
	 **/
	
	@RequestMapping(value = "/rules/", method = RequestMethod.GET)
	public ResponseEntity<?> getRules() {
		List<Rule> rulesList = new ArrayList<Rule>();
		Iterable<Rule> rules = ruleService.findAllRules();

		for (Rule rule : rules) {
			rulesList.add(rule);
		}

		System.out.println(rulesList);

		if (rulesList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(rulesList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rules/{ruleId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRule(@PathVariable int ruleId) {
		Rule rule = ruleService.getRule(ruleId);

		if (rule == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rule, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rules/", method = RequestMethod.POST)
	public ResponseEntity<?> postRule(@RequestBody Rule rule) {
		
		Rule newRule = this.ruleService.saveRule(rule);
		return new ResponseEntity<>(newRule, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rules/{ruleId}", method = RequestMethod.PUT)
	public ResponseEntity<?> putRule(@PathVariable int ruleId, @RequestBody Rule rule) {
		Rule currentRule = this.ruleService.getRule(ruleId);
		
		if (currentRule == null) {
			logger.error("Unable to update. Rule with id {} not found.", ruleId);
		    return new ResponseEntity<>("Unable to update. Rule with id " + ruleId + " not found.", HttpStatus.NOT_FOUND);
		}
		
		currentRule.setName(rule.getName());
		currentRule.setActive(rule.isActive());
		currentRule.setFinished(rule.isFinished());
		currentRule.setTimesToComplete(rule.getTimesToComplete());
		currentRule.setType(rule.getType());
		currentRule.setXp(rule.getXp());
		
        this.ruleService.saveRule(currentRule);
        return new ResponseEntity<Rule>(currentRule, HttpStatus.OK);		
	}
	
	
	@RequestMapping(value = "/rules/{ruleId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRule(@PathVariable int ruleId) {
		try {
			ruleService.deleteRule(ruleId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * 
	 *  -------------------------------------- USER BADGES ------------------------------------
	 * 
	 **/
	@RequestMapping(value = "/badges/{badgeId}/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUserBadge(@PathVariable int badgeId, @PathVariable int userId) {
		try {
			this.userService.deleteUserBadge(userId, badgeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badges/{badgeId}/users/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> putUserBadge(@PathVariable int badgeId, @PathVariable int userId) {
		try {
			User user = this.userService.getUser(userId);
			
			if(!user.getBadges().contains(this.badgeService.getBadge(badgeId))){
				user.getBadges().add(this.badgeService.getBadge(badgeId));
			}
			
			this.userService.saveUser(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * 
	 *  -------------------------------------- LOGS ------------------------------------
	 * 
	 **/
	
	@RequestMapping(value = "/logs/{logId}", method = RequestMethod.GET)
	public ResponseEntity<?> getLog(@PathVariable int logId) {		

		LogEvent log = this.logService.getLog(logId);

		if (log == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(log, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logs/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getLogs(@PathVariable int userId) {
		try {
			List<LogEvent> logs = this.logService.getUserLogs(userId);
			if( logs != null)
				return new ResponseEntity<>(logs, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logs/", method = RequestMethod.POST)
	public ResponseEntity<?> createLog(@RequestBody LogEvent logEvent) {
		try {
			LogEvent newLog = this.logService.saveLog(logEvent);
			if(newLog != null)
				return new ResponseEntity<>(newLog, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logs/{logId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLog(@RequestBody LogEvent log, @PathVariable int logId) {
		try {
			LogEvent currentLog = this.logService.getLog(logId);
			currentLog.setDateEvent(log.getDateEvent());
			currentLog.setRule(log.getRule());
			currentLog.setUser(log.getUser());
			LogEvent logUpdated = this.logService.saveLog(currentLog);
			
			if(logUpdated != null)
				return new ResponseEntity<>(logUpdated, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logs/rules/{ruleId}/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUserLog(@PathVariable int ruleId, @PathVariable int userId) {
		try {
			this.logService.deleteRuleLogsFromUser(ruleId, userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logs/{logId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLog(@PathVariable int logId) {		
		try {
			logService.deleteLog(logId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * 
	 *  -------------------------------------- RULE ATTRIBUTES ------------------------------------
	 * 
	 **/

	@RequestMapping(value = "/attributes/rules", method = RequestMethod.GET)
	public ResponseEntity<?> getAllAttributeRules() {
		List<RuleAttribute> ruleAttributeList = new ArrayList<RuleAttribute>();
		Iterable<RuleAttribute> rules = ruleService.findAllAttributeRules();

		for (RuleAttribute rule : rules) {
			ruleAttributeList.add(rule);
		}

		if (ruleAttributeList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(ruleAttributeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/attributes/rules/{ruleAttributeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRuleAttribute(@PathVariable int ruleAttributeId) {
		RuleAttribute ruleAtt = ruleService.getRuleAttribute(ruleAttributeId);

		if (ruleAtt == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleAtt, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/attributes/rules", method = RequestMethod.POST)
	public ResponseEntity<?> createRuleAttribute(@RequestBody RuleAttribute ruleAttribute) {
		try {
			RuleAttribute newRuleAttribute = this.ruleService.saveRuleAttribute(ruleAttribute);
			if(newRuleAttribute != null)
				return new ResponseEntity<>(newRuleAttribute, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 
	 *  -------------------------------------- RULE BADGES ------------------------------------
	 * 
	 **/

	
	@RequestMapping(value = "/badges/rules", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBadgeRules() {
		List<RuleBadge> ruleBadgeList = new ArrayList<RuleBadge>();
		Iterable<RuleBadge> rules = ruleService.findAllBadgeRules();

		for (RuleBadge rule : rules) {
			ruleBadgeList.add(rule);
		}

		if (ruleBadgeList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(ruleBadgeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badges/rules/{ruleBadgeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRuleBadge(@PathVariable int ruleBadgeId) {
		RuleBadge ruleBadge = ruleService.getRuleBadge(ruleBadgeId);

		if (ruleBadge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleBadge, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badges/rules", method = RequestMethod.POST)
	public ResponseEntity<?> createRuleBadge(@RequestBody RuleBadge ruleBadge) {
		try {
			RuleBadge newRuleBadge = this.ruleService.saveRuleBadge(ruleBadge);
			if(newRuleBadge != null)
				return new ResponseEntity<>(newRuleBadge, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/badges/rules/{ruleBadgeId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLog(@RequestBody RuleBadge ruleBadge, @PathVariable int ruleBadgeId) {
		try {
			RuleBadge currentRuleBadge = this.ruleService.getRuleBadge(ruleBadgeId);
			currentRuleBadge.setBadge(ruleBadge.getBadge());
			currentRuleBadge.setRule(ruleBadge.getRule());
			RuleBadge ruleBadgeUpdated = this.ruleService.saveRuleBadge(currentRuleBadge);
			
			if(ruleBadgeUpdated != null)
				return new ResponseEntity<>(ruleBadgeUpdated, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badges/rules/{ruleBadgeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRuleBadge(@PathVariable int ruleBadgeId) {
		try {
			ruleService.deleteRuleBadge(ruleBadgeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 
	 *  -------------------------------------- RULE LEVEL ------------------------------------
	 * 
	 **/

	@RequestMapping(value = "/level/rules/{ruleLevelId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRuleLevel(@PathVariable int ruleLevelId) {
		RuleLevel ruleLevel = ruleService.getRuleLevel(ruleLevelId);

		if (ruleLevel == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleLevel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/level/rules/{ruleLevelId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRuleLevel(@PathVariable int ruleLevelId) {
		try {
			ruleService.deleteRuleLevel(ruleLevelId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/level/rules/", method = RequestMethod.POST)
	public ResponseEntity<?> insertRuleLevel(@RequestBody RuleLevel ruleLevel) {
		try {
			
			RuleLevel newRuleLevel = ruleService.saveRuleLevel(ruleLevel);
			if(newRuleLevel != null)
				return new ResponseEntity<>(newRuleLevel, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/level/rules/{ruleLevelId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLog(@RequestBody RuleLevel ruleLevel, @PathVariable int ruleLevelId) {
		try {
			RuleLevel currentRuleLevel = this.ruleService.getRuleLevel(ruleLevelId);
			currentRuleLevel.setEndLevel(ruleLevel.getEndLevel());
			currentRuleLevel.setRule(ruleLevel.getRule());
			currentRuleLevel.setStartLevel(ruleLevel.getStartLevel());
			currentRuleLevel.setXpToLevelUp(ruleLevel.getXpToLevelUp());
			
			RuleLevel ruleLevelUpdated = this.ruleService.saveRuleLevel(currentRuleLevel);
			
			if(ruleLevelUpdated != null)
				return new ResponseEntity<>(ruleLevelUpdated, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 
	 *  -------------------------------------- LEVEL REWARD ------------------------------------
	 * 
	 **/
	
//	@RequestMapping(value = "/level/rewards/{levelRewardId}", method = RequestMethod.GET)
//	public ResponseEntity<?> getLevelReward(@PathVariable int levelRewardId) {
//		LevelReward levelReward = ruleService.getLevelReward(levelRewardId);
//
//		if (levelReward == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<>(levelReward, HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/level/rewards/", method = RequestMethod.POST)
	public ResponseEntity<?> insertRuleLevel(@RequestBody LevelReward levelReward) {
		try {
			LevelRewardKey lrk = new LevelRewardKey(levelReward.getAttribute().getId(), levelReward.getRuleLevel().getId());
			levelReward.setId(lrk);
			
			LevelReward newLevelReward = ruleService.saveLevelReward(levelReward);
			if(newLevelReward != null)
				return new ResponseEntity<>(newLevelReward, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	/**
	 * 
	 *  -------------------------------------- BADGE RULE ATTRIBUTE ------------------------------------
	 * 
	 **/
	
	
	@RequestMapping(value = "/badges/attributes/rewards/", method = RequestMethod.POST)
	public ResponseEntity<?> insertRuleBadgeAttribute(@RequestBody RuleBadgeAttribute ruleBadgeAttribute) {
		try {
			RuleBadgeAttribute newuleBadgeAttribute = ruleService.saveRuleBadgeAttribute(ruleBadgeAttribute);
			if(newuleBadgeAttribute != null)
				return new ResponseEntity<>(newuleBadgeAttribute, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	/**
	 * 
	 *  -------------------------------------- ENGINE ------------------------------------
	 * 
	 **/
	@RequestMapping(value = "/event-rule/{ruleId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> processEvent(@PathVariable int ruleId, @PathVariable int userId) {
		Rule rule = ruleService.getRule(ruleId);
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
				ruleAttributes = ruleService.getRuleAttributeByRule(ruleId);

				//atualiza os atributos do usuário
				for (RuleAttribute ruleAttribute : ruleAttributes) {
					int amount = ruleAttribute.getAmount();
					int attId = ruleAttribute.getAttribute().getId();

					user.getAttributes().stream()
							.filter(attribute -> attribute.getAttribute().getId() == attId)
							.forEach(attribute -> attribute.setValue(attribute.getValue() + amount));
				}
				
				break;
			case "badge":
				RuleBadge ruleBadge = null;
				ruleBadge = ruleService.getRuleBadgesByRule(ruleId);
				if(timesCompleted == rule.getTimesToComplete())	{
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
		
		if(userUpdated != null)
			return new ResponseEntity<>(userUpdated, HttpStatus.OK);
		else
			return new ResponseEntity<>(user, HttpStatus.OK);
	}
}