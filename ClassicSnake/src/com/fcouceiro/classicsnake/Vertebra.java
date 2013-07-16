package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Gdx;
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
		this.updateImage();
	}
	
	private void update(){
		this.addAction(Actions.moveBy(this.direction.x * GameMain.incW, this.direction.y * GameMain.incH));
	}
	
	public void setDir(Vector2 newdir)
	{
		last_direction = this.direction.cpy();
		this.direction = newdir.cpy();
		
		this.updateImage();
		this.update();
	}

	private void updateImage(){
		
		if(next_vertebra != null){
			if(direction.equals(GameMain.dir_up.cpy()))
			{
				if(last_direction.equals(GameMain.dir_right))
					next_vertebra.setDrawable(Snake.iLeftUp);
				else if(last_direction.equals(GameMain.dir_left))
					next_vertebra.setDrawable(Snake.iRightUp);
				else if(last_direction.equals(GameMain.dir_up)){
					next_vertebra.setDrawable(Snake.iVertical);
				}
			}
			else if(direction.equals(GameMain.dir_down.cpy())){
				if(last_direction.equals(GameMain.dir_right))
					next_vertebra.setDrawable(Snake.iLeftDown);
				else if(last_direction.equals(GameMain.dir_left))
					next_vertebra.setDrawable(Snake.iRightDown);
				else if(last_direction.equals(GameMain.dir_down)){
					next_vertebra.setDrawable(Snake.iVertical);
					
				}
			}
			else if(direction.equals(GameMain.dir_right.cpy())){
				if(last_direction.equals(GameMain.dir_down))
					next_vertebra.setDrawable(Snake.iRightUp);
				else if(last_direction.equals(GameMain.dir_up))
					next_vertebra.setDrawable(Snake.iRightDown);
				else if(last_direction.equals(GameMain.dir_right)){
					next_vertebra.setDrawable(Snake.iHorizontal);
					
				}
			}
			else if(direction.equals(GameMain.dir_left.cpy())){
				if(last_direction.equals(GameMain.dir_down))
					next_vertebra.setDrawable(Snake.iLeftUp);
				else if(last_direction.equals(GameMain.dir_up))
					next_vertebra.setDrawable(Snake.iLeftDown);
				else if(last_direction.equals(GameMain.dir_left)){
					next_vertebra.setDrawable(Snake.iHorizontal);
					
				}
			}
			
		}
		else{
			
		}
	}
}