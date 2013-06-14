package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.ruthlessgames.api.StylesManager;
import com.ruthlessgames.api.UI;

public class GameMainMenu extends UI{

	GameMain maingame;
	ButtonGroup speedG,sizeG;
	Label lblBest,lblLastScore;
	
	public GameMainMenu(GameMain main)
	{
		super(GameMain.batch,GameMain.main_font,false,Color.WHITE);
		this.maingame = main;
		this.pop_UI();
	}
	
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(ui_input);
		if(GameMain.best_session_score == -1) lblBest.setText("Best score: N/A");
		else lblBest.setText("Best score: " + GameMain.best_session_score);
		
		if(GameMain.last_score == -1) lblLastScore.setText("Last score: N/A");
		else lblLastScore.setText("Last score: "+ GameMain.last_score);
	}
	
	private void pop_UI()
	{
		LabelStyle lblBlack = new LabelStyle();
		lblBlack.font = StylesManager.skin.getFont("normal-font");
		
		TextButtonStyle btnLinkStyle = new TextButtonStyle();
		btnLinkStyle.overFontColor = Color.BLUE;
		btnLinkStyle.down = null;
		btnLinkStyle.downFontColor = Color.ORANGE;
		btnLinkStyle.up = null;
		btnLinkStyle.fontColor = Color.BLACK;
		btnLinkStyle.font = new BitmapFont();
		
		
		lblBest = new Label("Best score:",lblBlack);
		lblBest.setPosition(3*GameMain.ui_incW, 7*GameMain.ui_incH);
		
		lblLastScore = new Label("Last score: N/A",lblBlack);
		lblLastScore.setPosition(3*GameMain.ui_incW, 6*GameMain.ui_incH);
		
		final TextButton btnSign = new TextButton("Sign in with google",btnLinkStyle);
		btnSign.setPosition(GameMain.ui_incW, GameMain.ui_incH);
		btnSign.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}
			
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < btnSign.getWidth() && x >0 && y<btnSign.getHeight() && y > 0)
					if(Gdx.app.getType() == ApplicationType.Android) {
						if(!maingame.android_bridge.getOnlineAndSigned()){
							maingame.android_bridge.starConnections();
						}
					}
			}
		});
		
		final TextButton btnLeaderboard = new TextButton("Show leaderboard",btnLinkStyle);
		btnLeaderboard.setPosition(10*GameMain.ui_incW, 2*GameMain.ui_incH);
		btnLeaderboard.setSize(5*GameMain.ui_incW, GameMain.ui_incH);
		btnLeaderboard.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}
			
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < btnLeaderboard.getWidth() && x >0 && y<btnLeaderboard.getHeight() && y > 0)
					if(Gdx.app.getType() == ApplicationType.Android) {
						if(maingame.android_bridge.getOnlineAndSigned()){
							maingame.android_bridge.showLeaderboard();
						}
						else maingame.android_bridge.showToast("You need to sign in first.");
					}
			}
		});
		
		final TextButton btnAchievements= new TextButton("See your progress",btnLinkStyle);
		btnAchievements.setPosition(10*GameMain.ui_incW, GameMain.ui_incH);
		btnAchievements.setSize(5*GameMain.ui_incW, GameMain.ui_incH);
		btnAchievements.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}
			
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < btnAchievements.getWidth() && x >0 && y<btnAchievements.getHeight() && y > 0)
					if(Gdx.app.getType() == ApplicationType.Android) {
						if(maingame.android_bridge.getOnlineAndSigned()){
							maingame.android_bridge.showAchievements();
						}
						else maingame.android_bridge.showToast("You need to sign in first.");
					}
			}
		});
		
		final TextButton btnPlay = new TextButton("Play!",StylesManager.skin);
		btnPlay.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < btnPlay.getWidth() && x >0 && y<btnPlay.getHeight() && y > 0)
					play();
			}
		});
		
		btnPlay.setBounds(maingame.w * (12.0f/28.0f), maingame.h / 10.0f, maingame.w / 7.0f, maingame.h / 10.0f);
		
		Table difTab = new Table();
		difTab.setPosition(4*GameMain.ui_incW, 3*GameMain.ui_incH);
		
		speedG = new ButtonGroup();
		speedG.add(new CheckBox(" 1",StylesManager.skin),new CheckBox(" 2",StylesManager.skin),new CheckBox(" 3",StylesManager.skin),new CheckBox(" 4",StylesManager.skin),new CheckBox(" 5",StylesManager.skin));
		speedG.setChecked("1");
		
		int i = 0;
		for(Actor act:speedG.getButtons()){
			act.setPosition(i*GameMain.ui_incW, GameMain.ui_incH);
			difTab.addActor(act);
			i+=2;
		}
		
		sizeG = new ButtonGroup();
		sizeG.add(new CheckBox(" Huge",StylesManager.skin),new CheckBox(" Medium",StylesManager.skin),new CheckBox(" Micro",StylesManager.skin));
		sizeG.setChecked("Huge");
		
		int j = 0;
		for(Actor act:sizeG.getButtons()){
			act.setPosition(j*GameMain.ui_incW, 0);
			difTab.addActor(act);
			j+=4;
		}
		
		table.addActor(difTab);
		table.addActor(btnPlay);
		table.addActor(lblBest);
		table.addActor(lblLastScore);
		table.addActor(btnLeaderboard);
		table.addActor(btnAchievements);
		table.addActor(btnSign);
	}
	
	private void play(){
		GameMain.lines = 0;
		GameMain.columns = 0;
		
		CheckBox gridIndex = (CheckBox) sizeG.getChecked();
		int grid_ind = 0;
		
		if(gridIndex.getText().toString().contains("Huge")){
			GameMain.columns = 15;
			GameMain.lines = GameMain.ratio * GameMain.columns;
			grid_ind = 1;
		}
		else if(gridIndex.getText().toString().contains("Medium")){
			GameMain.columns = 12;
			GameMain.lines = GameMain.ratio * GameMain.columns;
			grid_ind = 2;
			
			if(Gdx.app.getType() == ApplicationType.Android) maingame.android_bridge.unlockAchievement(1);
		}
		else if(gridIndex.getText().toString().contains("Micro")){
			GameMain.columns = 6;
			GameMain.lines = GameMain.ratio * GameMain.columns;
			grid_ind = 3;
			if(Gdx.app.getType() == ApplicationType.Android) maingame.android_bridge.unlockAchievement(2);
		}
		
		CheckBox selSpeed = (CheckBox) speedG.getChecked();
		float selSpeedf = selSpeed.getText().charAt(1) - '0';
		float speed = 1.2f -  0.2f * selSpeedf;
		
		GameMain.incH = (GameMain.h / GameMain.lines);
		GameMain.incW = (GameMain.w / GameMain.columns);
		
		GameMain.speed_s = (int) selSpeedf -1;
		GameMain.size_g = grid_ind;
		
		if(Gdx.app.getType() == ApplicationType.Android) maingame.android_bridge.unlockAchievement(0);
		
		this.maingame.startGame(speed);
	}
}
