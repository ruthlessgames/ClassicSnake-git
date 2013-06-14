package com.fcouceiro.classicsnake;

public interface RequestHandlerBridge {
	public void showAds(boolean show);
	public void showToast(String to_show);
	public void starConnections();
	public boolean getOnlineAndSigned();
	public void showLeaderboard();
	public void showAchievements();
	public void unlockAchievement(int index);
	public void incrementAchievement(int index, int inc);
	public void submitScore(int score);
}
