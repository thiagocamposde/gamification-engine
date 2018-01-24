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

import br.ufsc.tcc.gamifyEngine.model.Rule;
import br.ufsc.tcc.gamifyEngine.model.RuleReward;
import br.ufsc.tcc.gamifyEngine.model.User;
import br.ufsc.tcc.gamifyEngine.service.RuleService;
import br.ufsc.tcc.gamifyEngine.service.UserService;

@RestController
@RequestMapping("/api")
public class RestApiController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RuleService ruleService;
		
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
	public ResponseEntity<?> getUser(@PathVariable long userId)
	{	
		User user = userService.getUser(userId);
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/event-rule/{ruleId}/{userId}", method = RequestMethod.GET)	
	public ResponseEntity<?> processEvent(@PathVariable long ruleId, @PathVariable long userId)
	{	
		Rule rule = ruleService.getRule(ruleId);
		User user = userService.getUser(userId);
		
		System.out.println(rule);
		System.out.println(user);
		
		System.out.println(rule.getType());
		User userUpdated = null;
		
		if(rule.getType().equals("reward")) {
//			RuleReward ruleReward = ruleService.getRuleReward(ruleId);
			//se for reward, veficar o tipo e adicionar o amount no user
			//verificar se existem regras relacionadas as rewards
			//repetir o processo
			
			user.setXp(user.getXp() + rule.getXp());
			userUpdated = userService.saveUser(user);
			
		}
		
		if(userUpdated == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(userUpdated, HttpStatus.OK);
	}
}
