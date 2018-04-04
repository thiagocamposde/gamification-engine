package br.ufsc.tcc.gamifyEngine.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity 
public class LogEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
//	@ManyToOne
//	private Rule rule;
//	
//	@ManyToOne
//	private User user; 
	
	@Column(name="rule_id")
	private int rule;
	
	@Column(name="user_id")
	private int user; 
	
	@Column(name="event_date")
	private Timestamp dateEvent;
	
	public LogEvent() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public Rule getRule() {
//		return rule;
//	}
//
//	public void setRule(Rule rule) {
//		this.rule = rule;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	
	
	public Timestamp getDateEvent() {
		return dateEvent;
	}

	public int getRule() {
		return rule;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public void setDateEvent(Timestamp dateEvent) {
		this.dateEvent = dateEvent;
	}
}
