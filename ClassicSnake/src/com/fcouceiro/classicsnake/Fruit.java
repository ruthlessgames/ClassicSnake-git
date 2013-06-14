package com.fcouceiro.classicsnake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Fruit extends Image{

	private int value;
	
	public Fruit(int value)
	{
		super(new TextureRegion(GameMain.asm.get("data/libgdx.png",Texture.class)));
		this.value = value;
		this.setSize(GameMain.incW, GameMain.incH);
	}
	
	public void setValue(int v){
		this.value = v;
	}
	
	public int getValue(){
		return this.value;
	}
}
