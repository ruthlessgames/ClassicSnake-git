package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ruthlessgames.api.LoadingScreen;
import com.ruthlessgames.api.StylesManager;
import com.ruthlessgames.api.UI;

public class GameLoad extends UI implements LoadingScreen{

	AssetManager asm;
	Label load_lbl;

	
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
		
		maingame.main_menu = new GameMainMenu(maingame);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(asm.update())
		{
			maingame.setScreen(maingame.main_menu);
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
		
		table.setBackground(new TextureRegionDrawable(new Sprite(bg)));
		
		this.load_lbl = new Label("Initializing",StylesManager.skin);
		load_lbl.setPosition(maingame.w / 2 -50, maingame.h /6);
		
		table.addActor(load_lbl);
	}

	@Override
	public void setLoad() {
		// TODO Auto-generated method stub
		TextureParameter param = new TextureParameter();
		param.minFilter = TextureFilter.Linear;
		param.magFilter = TextureFilter.Linear;
		
	
			GameMain.asm.load("data/libgdx.png", Texture.class,param);
		
	}

}
