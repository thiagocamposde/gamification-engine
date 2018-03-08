package br.ufsc.tcc.gamifyEngine.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.ufsc.tcc.gamifyEngine.model.Attribute;
import br.ufsc.tcc.gamifyEngine.model.Badge;
import br.ufsc.tcc.gamifyEngine.model.LogEvent;
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.RuleLevel;
import br.ufsc.tcc.gamifyEngine.model.User;
import br.ufsc.tcc.gamifyEngine.model.UserAttribute;
import br.ufsc.tcc.gamifyEngine.service.AttributeService;
import br.ufsc.tcc.gamifyEngine.service.BadgeService;
import br.ufsc.tcc.gamifyEngine.service.LogService;
import br.ufsc.tcc.gamifyEngine.service.RuleService;
import br.ufsc.tcc.gamifyEngine.service.UserService;

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

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
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

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable int userId) {
		User user = userService.getUser(userId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/rule/{ruleId}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/attribute/{attributeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAttribute(@PathVariable int attributeId) {
		Attribute att = attributeService.getAttribute(attributeId);

		if (att == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(att, HttpStatus.OK);
	}

	@RequestMapping(value = "/badge/{badgeId}", method = RequestMethod.GET)
	public ResponseEntity<?> getBadge(@PathVariable int badgeId) {
		Badge badge = badgeService.getBadge(badgeId);

		if (badge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(badge, HttpStatus.OK);
	}

	@RequestMapping(value = "/event-rule/{ruleId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> processEvent(@PathVariable int ruleId, @PathVariable int userId) {
		Rule rule = ruleService.getRule(ruleId);
		User user = userService.getUser(userId);
		String ruleType = rule.getType();

		boolean completed = false;
		
		List<LogEvent> logs = this.logService.getLogByUserAndRule(userId, ruleId);		
		
		if(rule.getTimesToComplete() == logs.size()) {
			completed = true;
		}

		List<RuleAttribute> ruleAttributes = null;
		User userUpdated = null;
			
		if(!completed) {
			//TODO ruleType troca pra enum depois
			switch (ruleType) {
			case "attribute":
				
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
				break;
			case "level":
				break;			
			default:
				break;
			}
			
			user.setXp(user.getXp() + rule.getXp());
			user.setCurrentXp(user.getCurrentXp() + rule.getXp());
			
			this.ruleService.evaluate("xp", user);

			userUpdated = userService.saveUser(user);
		}
		
		if(userUpdated != null)
			return new ResponseEntity<>(userUpdated, HttpStatus.OK);
		else
			return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
