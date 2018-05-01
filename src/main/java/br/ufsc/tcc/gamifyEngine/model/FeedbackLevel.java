package br.ufsc.tcc.gamifyEngine.model;

public class FeedbackLevel {
	private boolean levelIncreased;
	private int newLevel;
	
	public FeedbackLevel(boolean levelIncreased, int newLevel) {
		this.levelIncreased = levelIncreased;
		this.newLevel = newLevel;
	}
	
	public FeedbackLevel() {
		// TODO Auto-generated constructor stub
	}

	public boolean isLevelIncreased() {
		return levelIncreased;
	}

	public void setLevelIncreased(boolean levelIncreased) {
		this.levelIncreased = levelIncreased;
	}

	public int getNewLevel() {
		return newLevel;
	}

	public void setNewLevel(int newLevel) {
		this.newLevel = newLevel;
	}
	
}
