package com.fcouceiro.classicsnake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Vertebra extends Image{

	public Vertebra next_vertebra;
	public Vector2 direction,last_direction;
	
	public Vertebra(Vector2 direction)
	{
		super(Snake.iHorizontal);
		this.next_vertebra = null;
		this.setSize(GameMain.incW, GameMain.incH);
		this.direction = direction;
		this.last_direction = direction;
	}
	
	private void update(){
		this.addAction(Actions.moveBy(this.direction.x * GameMain.incW, this.direction.y * GameMain.incH));
	}
	
	public void setDir(Vector2 newdir)
	{
		last_direction = this.direction.cpy();
		this.direction = newdir.cpy();
			
		if(direction.y == GameMain.dir_up.y || direction.y == GameMain.dir_down.y) this.setDrawable(Snake.iVertical);
		else this.setDrawable(Snake.iHorizontal);
		
		this.update();
	}

}