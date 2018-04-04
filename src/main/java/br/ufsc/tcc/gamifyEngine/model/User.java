package br.ufsc.tcc.gamifyEngine.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int xp;
	
	@Column(name="current_xp")
	private int currentXp;
	
	private int level;
	
	@NotNull
	private boolean active;
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonManagedReference (value="userAttributeReference")
	private List <UserAttribute> attributes;
	
	@JoinTable(name="user_badges", joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name = "badge_id", referencedColumnName="id" ))
	@ManyToMany
	private List <Badge> badges;
	
	public User() {
			
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}

	public List<UserAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserAttribute> attributes) {
		this.attributes = attributes;
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

	public int getId() {
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

	public int getCurrentXp() {
		return currentXp;
	}

	public void setCurrentXp(int currentXp) {
		this.currentXp = currentXp;
	}
}

