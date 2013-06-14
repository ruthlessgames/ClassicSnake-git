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
	boolean paused = false;
	
	//ui stuff
	SequenceAction end_game_action;
	Window score_wdm;
	Label lbl_wdm_score;
	TextButton sub_score;
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
	
	@Override
	public void hide(){
		Gdx.input.setInputProcessor(null);
		if(Gdx.app.getType() == ApplicationType.Android)
		maingame.android_bridge.showAds(true);
		
		if(this.score_actual.final_score > GameMain.best_session_score)
		{
			GameMain.best_session_score = (int) this.score_actual.final_score;
		}
		
		GameMain.last_score = (int) this.score_actual.final_score;
	}

	public void pause(){
		this.paused = true;
	}
	
	public void resume(){
		this.paused = false;
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(!this.paused){
			switch(snake.update())
			{
			case -1:
				this.pause();
				this.showScorePlacard();
				break;
			}
		}
		else{
			if(this.start_counting)
			{
				lbl_wdm_score.setText("Score: " + this.counting);
				
				if(this.counting != score_actual.final_score){
					this.counting++;
					
					if(counting > 750){
						sub_score.setText("Submit!");
						sub_score.setDisabled(false);
					}
				}
			
			}
		}
		
		stage.act(delta);
		stage.draw();
	}
	
	public void showScorePlacard()
	{
		score_wdm.setVisible(true);
		score_wdm.getColor().a = 0;
		score_wdm.addAction(this.end_game_action);
	}
	
	
	private void pop_UI() {
		end_game_action = new SequenceAction();
		end_game_action.addAction(Actions.delay(3));
		end_game_action.addAction(Actions.fadeIn(0.5f));
		end_game_action.addAction(Actions.run(new Runnable() {
        public void run () {
        	start_counting = true;	
        }
		}));

		score_wdm = new Window("Your score!",StylesManager.skin);
		score_wdm.setMovable(false);
		Vector2 wdm_size = new Vector2((GameMain.w / GameMain.ui_incW - 2) * GameMain.ui_incW, (GameMain.h / GameMain.ui_incH - 2) * GameMain.ui_incH);
		score_wdm.setSize(wdm_size.x,wdm_size.y);
		score_wdm.setPosition(GameMain.ui_incW, GameMain.ui_incH);
		score_wdm.setVisible(false);
		
		lbl_wdm_score = new Label("Score: 0",StylesManager.skin);
		lbl_wdm_score.setPosition(wdm_size.x /2 - GameMain.ui_incW, 4*GameMain.ui_incH);
		
		sub_score = new TextButton("-------",StylesManager.skin);
		sub_score.setDisabled(true);
		sub_score.setPosition(wdm_size.x /2 - GameMain.ui_incW, 3*GameMain.ui_incH);
		sub_score.setSize(GameMain.ui_incW * 2,GameMain.ui_incH);
		sub_score.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < sub_score.getWidth() && x >0 && y<sub_score.getHeight() && y > 0)
					if(Gdx.app.getType() == ApplicationType.Android)
					maingame.android_bridge.submitScore((int) score_actual.final_score);
			}
		});
		
		final TextButton btnOkScore = new TextButton("OK",StylesManager.skin);
		btnOkScore.setPosition(wdm_size.x /2 - GameMain.ui_incW, GameMain.ui_incH);
		btnOkScore.setSize(GameMain.ui_incW * 2,GameMain.ui_incH);
		btnOkScore.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

				return true;
			}

			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if(x < btnOkScore.getWidth() && x >0 && y<btnOkScore.getHeight() && y > 0)
					maingame.android_bridge.showUiLayout(true);
			}
		});
		
		score_wdm.addActor(sub_score);
		score_wdm.addActor(lbl_wdm_score);
		score_wdm.addActor(btnOkScore);
		stage.addActor(score_wdm);
	}
}
