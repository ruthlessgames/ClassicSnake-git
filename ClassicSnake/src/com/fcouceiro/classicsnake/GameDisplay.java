package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.ruthlessgames.api.StylesManager;
import com.ruthlessgames.api.UI;

public class GameDisplay extends UI{

	GameMain maingame;
	GestureDetector input_processor;
	Snake snake;
	Score score_actual;
	public boolean paused = false;
	boolean ended = false;
	boolean new_best_score = false;
	//ui stuff
	float alpha = 1.0f;
	boolean start_counting = false;
	int counting = 0;
	
	public GameDisplay(GameMain maingame,float snake_speed) {
		super(GameMain.batch, GameMain.main_font, false);
		// TODO Auto-generated constructor stub
		this.maingame = maingame;
		//create snake
		score_actual = new Score(GameMain.speed_s,GameMain.size_g);
		Vertebra cabeca = new Vertebra(GameMain.dir_right);
	
		cabeca.setPosition((int)(GameMain.columns / 2)*GameMain.incW, (int)(GameMain.lines / 2)*GameMain.incH);
		
		snake = new Snake(cabeca,this.table,score_actual);
		snake.speed = snake_speed;
		snake.addToTable();
		
		//set input managers
		GameInput gm_in = new GameInput(snake);
		input_processor = new GestureDetector(gm_in);
		this.ui_input.addProcessor(input_processor);
		
		if(Gdx.app.getType() == ApplicationType.Desktop)
		this.ui_input.addProcessor(gm_in);
		
		//generate UI ingame
		this.pop_UI();
	}
	
	@Override
	public void show(){
		Gdx.input.setInputProcessor(ui_input);
		if(Gdx.app.getType() == ApplicationType.Android)
		maingame.android_bridge.showAds(false);
	}

	public void pause_ingame(){
		this.paused = true;
		maingame.android_bridge.showAds(true);
		
		
		
	}
	
	public void resume_ingame(){
		this.paused = false;
		maingame.android_bridge.showAds(false);
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(alpha, alpha, alpha, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(!this.paused){
			switch(snake.update())
			{
			case -1:
				this.showScorePlacard();
				this.pause_ingame();
				break;
			}
		}
		
		stage.act(delta);
		stage.draw();
	}
	
	public void showScorePlacard()
	{
		this.ended = true;
		
			if (this.score_actual.final_score > GameMain.best_session_score) {
				GameMain.best_session_score = (int) this.score_actual.final_score;
				this.new_best_score = true;
			}
			GameMain.last_score = (int) this.score_actual.final_score;
		
		
		stage.addAction(Actions.run(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						alpha = 0;
					}}));
		
		maingame.android_bridge.showScorePlacard((int)score_actual.final_score,this.new_best_score);
	}
	
	
	private void pop_UI() {

	}
}
