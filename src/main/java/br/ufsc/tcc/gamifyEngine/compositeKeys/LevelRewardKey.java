package br.ufsc.tcc.gamifyEngine.compositeKeys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LevelRewardKey implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", nullable = false)
	private int user;
	
	@Column(name = "attribute_id", nullable = false)
	private int attribute;
	
	@Column(name = "rule_level_id", nullable = false)
	private int ruleLevel;
	
	public LevelRewardKey() {
		// TODO Auto-generated constructor stub
	}	
	    
}
