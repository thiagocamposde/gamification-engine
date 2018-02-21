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
import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleAttribute;
import br.ufsc.tcc.gamifyEngine.model.RuleBadge;
import br.ufsc.tcc.gamifyEngine.model.User;
import br.ufsc.tcc.gamifyEngine.service.AttributeService;
import br.ufsc.tcc.gamifyEngine.service.BadgeService;
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
		
	@RequestMapping(value = "/user/", method = RequestMethod.GET)	
	public ResponseEntity<?> listAllUsers()
	{	
		List<User> usersList = new ArrayList<User>();
		Iterable<User> users = userService.findAllUsers();
		
		System.out.println(users);
		
		for (User user : users) {
			usersList.add(user);
	    }
		
		System.out.println(usersList);
		
		if(usersList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(usersList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)	
	public ResponseEntity<?> getUser(@PathVariable int userId)
	{	
		User user = userService.getUser(userId);
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rule/{ruleId}", method = RequestMethod.GET)	
	public ResponseEntity<?> getRule(@PathVariable int ruleId)
	{	
		Rule rule = ruleService.getRule(ruleId);
		
		if(rule == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rule, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rule-attribute/{ruleAttributeId}", method = RequestMethod.GET)	
	public ResponseEntity<?> getRuleAttribute(@PathVariable int ruleAttributeId)
	{	
		RuleAttribute ruleAtt = ruleService.getRuleAttribute(ruleAttributeId);
		
		if(ruleAtt == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleAtt, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rule-badge/{ruleBadgeId}", method = RequestMethod.GET)	
	public ResponseEntity<?> getRuleBadge(@PathVariable int ruleBadgeId)
	{	
		RuleBadge ruleBadge = ruleService.getRuleBadge(ruleBadgeId);
		
		if(ruleBadge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ruleBadge, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/attribute/{attributeId}", method = RequestMethod.GET)	
	public ResponseEntity<?> getAttribute(@PathVariable int attributeId)
	{	
		Attribute att = attributeService.getAttribute(attributeId);
		
		if(att == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(att, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/badge/{badgeId}", method = RequestMethod.GET)	
	public ResponseEntity<?> getBadge(@PathVariable int badgeId)
	{	
		Badge badge = badgeService.getBadge(badgeId);
		
		if(badge == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(badge, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/event-rule/{ruleId}/{userId}", method = RequestMethod.GET)	
	public ResponseEntity<?> processEvent(@PathVariable int ruleId, @PathVariable int userId)
	{	
		Rule rule = ruleService.getRule(ruleId);
		User user = userService.getUser(userId);
		String ruleType = rule.getType();
		
		user.setXp(user.getXp() + rule.getXp());
		
		RuleAttribute ruleAttribute = null;
		
        switch (ruleType) {
            case "attribute":
            	Rule a = new Rule ();
            	a.setId(1);
            	ruleAttribute = ruleService.getRuleAttributeByRule(a);
            	
            	Object result = userService.getUserAttribute(user.getId(), ruleAttribute.getAttribute().getId());
            	
//            	List<Attribute> userAttributes = user.getAttributes();
            	
            	
            	
                break;
            case "badge": 
                break;
            case "level": 
                break;
            default: 
                break;
        }
        
        User userUpdated = userService.saveUser(user);
		
//			//se for reward, veficar o tipo e adicionar o amount no user
//			//verificar se existem regras relacionadas as rewards
//			//repetir o processo
//			
//			user.setXp(user.getXp() + rule.getXp());
//			userUpdated = userService.saveUser(user);
//			
//		}
		
		if(userUpdated == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ruleAttribute, HttpStatus.OK);
	}
}
