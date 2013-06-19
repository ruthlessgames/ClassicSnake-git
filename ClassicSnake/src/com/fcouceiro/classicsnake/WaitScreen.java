package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.ruthlessgames.api.UI;

public class WaitScreen extends UI{

	GameMain maingame;
	boolean startup = false;
	public WaitScreen(GameMain main)
	{
		super(GameMain.batch,GameMain.main_font,false);
		maingame = main;
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
	}

	public void create_startup_achievements()
	{
		if(!startup && maingame.android_bridge.getOnlineAndSigned()){
		SequenceAction startup_action = new SequenceAction();
		
		startup_action.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				maingame.android_bridge.incrementAchievement(4, 1);
		
			
			}}));
		
		startup_action.addAction(Actions.delay(3));
		
		startup_action.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				maingame.android_bridge.incrementAchievement(5, 1);
				
			}}));
		
		startup_action.addAction(Actions.delay(3));
		
		startup_action.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				maingame.android_bridge.incrementAchievement(6, 1);
		
			}}));
		
		table.addAction(startup_action);
		}
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
