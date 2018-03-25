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

import br.ufsc.tcc.gamifyEngine.compositeKeys.RuleAttributeKey;
import br.ufsc.tcc.gamifyEngine.model.Attribute;
import br.ufsc.tcc.gamifyEngine.model.Badge;
import br.ufsc.tcc.gamifyEngine.model.LogEvent;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;
import br.ufsc.tcc.gamifyEngine.model.User;
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
	 * Retorna todos os usuários
	 *  
	 **/
	@RequestMapping(value = "/users/", method = RequestMethod.GET)
	public ResponseEntity<?> listAllUsers() {
		List<User> usersList = new ArrayList<User>();
		Iterable<User> users = userService.findAllUsers();

		System.out.println(users);

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
		
		User userCreated = this.userService.saveUser(user);
		return new ResponseEntity<>(userCreated, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> putUser(@PathVariable int userId, @RequestBody User user) {
		
		logger.info("Updating User with id {}", userId);
		
		User currentUser = userService.getUser(userId);
		
		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", userId);
		    return new ResponseEntity<>(new Exception("Unable to update. User with id " + userId + " not found."),
		            HttpStatus.NOT_FOUND);
		}
		
		currentUser.setActive(user.isActive());
		currentUser.setXp(user.getXp());
		currentUser.setLevel(user.getLevel());
		currentUser.setCurrentXp(user.getCurrentXp());
		
		user.getAttributes().stream().forEach(attUser -> {
			attUser.setId(new RuleAttributeKey(userId, attUser.getAttribute().getId()));
		});
		
		currentUser.setAttributes(user.getAttributes());
		currentUser.setBadges(user.getBadges());
		
 
        this.userService.saveUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);		
	}

	@RequestMapping(value = "/rules/{ruleId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRule(@PathVariable int ruleId) {
		Rule rule = ruleService.getRule(ruleId);

		if (rule == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rule, HttpStatus.OK);
	}

	@RequestMapping(value = "/rule-attribute/{ruleAttributeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRuleAttribute(@PathVariable int ruleAttributeId) {
		RuleAttribute ruleAtt = ruleService.getRuleAttribute(ruleAttributeId);

		if (ruleAtt == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleAtt, HttpStatus.OK);
	}

	@RequestMapping(value = "/rule-badge/{ruleBadgeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRuleBadge(@PathVariable int ruleBadgeId) {
		RuleBadge ruleBadge = ruleService.getRuleBadge(ruleBadgeId);

		if (ruleBadge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleBadge, HttpStatus.OK);
	}

	@RequestMapping(value = "/rule-level/{ruleLevelId}", method = RequestMethod.GET)
	public ResponseEntity<?> getRuleLevel(@PathVariable int ruleLevelId) {
		RuleLevel ruleLevel = ruleService.getRuleLevel(ruleLevelId);

		if (ruleLevel == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleLevel, HttpStatus.OK);
	}

	@RequestMapping(value = "/attributes/{attributeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAttribute(@PathVariable int attributeId) {
		Attribute att = attributeService.getAttribute(attributeId);

		if (att == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(att, HttpStatus.OK);
	}

	@RequestMapping(value = "/badges/{badgeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBadge(@PathVariable int badgeId) {
		Badge badge = badgeService.getBadge(badgeId);

		if (badge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(badge, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logs/{logId}", method = RequestMethod.GET)
	public ResponseEntity<?> getLogs(@PathVariable int logId) {		

		LogEvent log = this.logService.getLog(logId);

		if (log == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(log, HttpStatus.OK);
	}

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
			logEvent.setRule(rule);
			logEvent.setUser(user);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			logEvent.setDateEvent(timestamp);
			this.logService.insertLog(logEvent);
		}
		
		if(userUpdated != null)
			return new ResponseEntity<>(userUpdated, HttpStatus.OK);
		else
			return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	
	//DELETE METHODS	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable int userId) {
		try {
			userService.deleteUser(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
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

	@RequestMapping(value = "/rule-attribute/{ruleAttributeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRuleAttribute(@PathVariable int ruleAttributeId) {
		try {
			ruleService.deleteRuleAttribute(ruleAttributeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/rule-badge/{ruleBadgeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRuleBadge(@PathVariable int ruleBadgeId) {
		try {
			ruleService.deleteRuleBadge(ruleBadgeId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/rule-level/{ruleLevelId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRuleLevel(@PathVariable int ruleLevelId) {
		try {
			ruleService.deleteRuleLevel(ruleLevelId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
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

	@RequestMapping(value = "/badges/{badgeId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBadge(@PathVariable int badgeId) {		

		try {
			badgeService.deleteBadge(badgeId);
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
	
}