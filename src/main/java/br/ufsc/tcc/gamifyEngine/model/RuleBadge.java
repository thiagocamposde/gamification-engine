package br.ufsc.tcc.gamifyEngine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class RuleBadge {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Rule rule;
	
	@OneToOne
	private Badge badge;
	
	@ManyToOne
	private Attribute attribute;
	
	@Column(name="goal_value", nullable = true)
	private Integer goalValue;

	public long getId() {
		return id;
	}
	
	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Integer getGoalValue() {
		return goalValue;
	}

	public void setGoalValue(Integer goalValue) {
		this.goalValue = goalValue;
	}
	
}