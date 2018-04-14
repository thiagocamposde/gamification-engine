package br.ufsc.tcc.gamifyEngine.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.ufsc.tcc.gamifyEngine.compositeKeys.LevelRewardKey;

@Entity
@Table(name="level_reward")
public class LevelReward {
	
	/*ATTRIBUTES*/
	@EmbeddedId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonBackReference
	private LevelRewardKey id;
	
//	@MapsId("user")	
//	@ManyToOne
//	@JsonBackReference
//	private User user;
	
	@MapsId("attribute")	
	@ManyToOne
	private Attribute attribute;
	
	@MapsId("ruleLevel")	
	@ManyToOne
	private RuleLevel ruleLevel;
	
	private int amount;
	
	/*CONSTRUCTOR*/
	public LevelReward() {	}
	
	/*GETTERS AND SETTERS*/
	public LevelRewardKey getId() {
		return id;
	}

	public void setId(LevelRewardKey id) {
		this.id = id;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public RuleLevel getRuleLevel() {
		return ruleLevel;
	}

	public void setRuleLevel(RuleLevel ruleLevel) {
		this.ruleLevel = ruleLevel;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
