package br.ufsc.tcc.gamifyEngine.compositeKeys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RuleAttributeKey implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id", nullable = false)
	private int user;
	
	@Column(name = "attribute_id", nullable = false)
	private int attribute;
	
	public RuleAttributeKey() {
		// TODO Auto-generated constructor stub
	}	
	    
}
