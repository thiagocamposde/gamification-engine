package br.ufsc.tcc.gamifyEngine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity 
public class Rule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Column(unique=true, nullable=false)
	private String name;
	
	@NotNull
	private String type;
	
	@NotNull
	@Column(name="times_to_complete")
	private int timesToComplete;
	
	@NotNull
	@Column(name="xp_reward")
	private int xp;
	
	@NotNull
	private boolean active;

	public Rule() {

	}
	
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public int getTimesToComplete() {
		return timesToComplete;
	}

	public void setTimesToComplete(int timesToComplete) {
		this.timesToComplete = timesToComplete;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
}
