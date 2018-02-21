package br.ufsc.tcc.gamifyEngine.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.ufsc.tcc.gamifyEngine.compositeKeys.RuleAttributeKey;

@Entity
@Table(name="user_attributes")
public class UserAttribute {
	
	/*ATTRIBUTES*/
	@EmbeddedId
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonBackReference
	private RuleAttributeKey id;
	
	@MapsId("user")	
	@ManyToOne
	@JsonBackReference
	private User user;
	
	@MapsId("attribute")	
	@ManyToOne
	private Attribute attribute;
	private int value;
	
	/*CONSTRUCTOR*/
	public UserAttribute() {	}
	
	
	/*GETTERS AND SETTERS*/
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public RuleAttributeKey getId() {
		return id;
	}
	public void setId(RuleAttributeKey id) {
		this.id = id;
	}
}
