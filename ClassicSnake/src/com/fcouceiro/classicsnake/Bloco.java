package com.fcouceiro.classicsnake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bloco extends Image{

	public Bloco(){
		super(new TextureRegion(GameMain.asm.get("data/libgdx.png",Texture.class)));
		this.setSize(GameMain.incW, GameMain.incH);
	}
}
