package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.LoadingScreen;
import com.ruthlessgames.api.StylesManager;
import com.ruthlessgames.api.UI;

public class GameLoad extends UI implements LoadingScreen{

	AssetManager asm;
	Label load_lbl;
	boolean start_control = false;
	
	GameMain maingame;
	
	public GameLoad(GameMain main) {
		super(GameMain.batch, GameMain.main_font, false);
		// TODO Auto-generated constructor stub
		this.maingame = main;
		this.pop_UI();
		this.createGame();
		this.setLoad();
	}

	@Override
	public void createGame() {
		// TODO Auto-generated method stub
		maingame.wait_screen = new WaitScreen(maingame);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(asm.update() && !start_control)
		{
			GameMain.loaded = true;
			maingame.android_bridge.showUiLayout(true);
			start_control = true;
			
			load_lbl.addAction(Actions.sequence(Actions.fadeOut(1),Actions.run(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					maingame.setScreen(maingame.wait_screen);
				}
				
			})));
		}
		
		float progress = asm.getProgress();
		this.load_lbl.setText("Loading: "+progress *100);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void setAssetManager(AssetManager asm) {
		// TODO Auto-generated method stub
		this.asm = asm;
	}

	@Override
	public void pop_UI() {
		// TODO Auto-generated method stub
		Texture bg = new Texture(Gdx.files.internal("default/rlogo.png"));
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//table.setBackground(new TextureRegionDrawable(new Sprite(bg)));
		
		this.load_lbl = new Label("Initializing",StylesManager.skin);
		load_lbl.setPosition(maingame.w / 2 -50, maingame.h /2);
		
		table.addActor(load_lbl);
	}

	@Override
	public void setLoad() {
		// TODO Auto-generated method stub
		TextureParameter param = new TextureParameter();
		param.minFilter = TextureFilter.Linear;
		param.magFilter = TextureFilter.Linear;
		
	
			GameMain.asm.load("data/libgdx.png", Texture.class,param);
			GameMain.asm.load("data/body-down.png", Texture.class,param);
			GameMain.asm.load("data/body-up.png", Texture.class,param);
			GameMain.asm.load("data/body-default.png", Texture.class,param);
	}

}
