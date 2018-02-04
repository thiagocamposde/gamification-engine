package br.ufsc.tcc.gamifyEngine.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int xp;
	
	private int level;
	
	@NotNull
	private boolean active;
	
	@ManyToMany
	@JoinTable(name="user_attributes", joinColumns={@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="attribute_id")})
	private List <Attribute> attributes;
	
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public User() {
		
	}
	
	public User(int id) {
		this.id = id;		
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
}

