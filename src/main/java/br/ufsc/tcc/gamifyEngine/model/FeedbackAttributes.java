package br.ufsc.tcc.gamifyEngine.model;

public class FeedbackAttributes {

	private Attribute attribute;
	private int amountChanged;
	
	public FeedbackAttributes() {
	}
	
	public FeedbackAttributes(Attribute att, int amountChanged) {
		this.attribute = att;
		this.amountChanged = amountChanged;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public int getAmountChanged() {
		return amountChanged;
	}

	public void setAmountChanged(int amountChanged) {
		this.amountChanged = amountChanged;
	}
}
