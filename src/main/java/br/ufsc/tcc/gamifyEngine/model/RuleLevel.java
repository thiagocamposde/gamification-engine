package br.ufsc.tcc.gamifyEngine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class RuleLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Rule rule;
	
	@Column(name="start_level")
	private int startLevel;
	
	@Column(name="end_level")
	private int endLevel;
	
	@Column(name="xp_to_level_up")
	private int xpToLevelUp;
	
	public RuleLevel() {
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public int getEndLevel() {
		return endLevel;
	}

	public void setEndLevel(int endLevel) {
		this.endLevel = endLevel;
	}

	public int getXpToLevelUp() {
		return xpToLevelUp;
	}

	public void setXpToLevelUp(int xpToLevelUp) {
		this.xpToLevelUp = xpToLevelUp;
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
}