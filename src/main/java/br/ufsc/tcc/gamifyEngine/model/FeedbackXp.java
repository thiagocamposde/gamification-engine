package br.ufsc.tcc.gamifyEngine.model;

public class FeedbackXp {
	private boolean xpChanged;
	private int amountChanged;
	private int userTotalXp;
	private int userNewCurrentXp;
	
	public FeedbackXp(boolean xpChanged, int amountChanged, int newXp, int newCurrentXp) {
		this.xpChanged = xpChanged;
		this.amountChanged = amountChanged;
		this.userTotalXp = newXp;
		this.userNewCurrentXp = newCurrentXp;
	}

	public FeedbackXp() {
		// TODO Auto-generated constructor stub
	}

	public boolean isXpChanged() {
		return xpChanged;
	}

	public void setXpChanged(boolean xpChanged) {
		this.xpChanged = xpChanged;
	}

	public int getAmountChanged() {
		return amountChanged;
	}

	public void setAmountChanged(int amountChanged) {
		this.amountChanged = amountChanged;
	}

	public int getUserTotalXp() {
		return userTotalXp;
	}

	public void setUserTotalXp(int userTotalXp) {
		this.userTotalXp = userTotalXp;
	}

	public int getUserNewCurrentXp() {
		return userNewCurrentXp;
	}

	public void setUserNewCurrentXp(int userNewCurrentXp) {
		this.userNewCurrentXp = userNewCurrentXp;
	}
}
