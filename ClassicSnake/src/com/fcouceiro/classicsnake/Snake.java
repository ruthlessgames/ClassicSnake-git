package com.fcouceiro.classicsnake;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Snake {
	Table display_table;
	GameMain maingame;
	Vertebra cabeca = null;
	Vector2 cur_dir;
	boolean can_change_dir = true;
	
	float speed = 1; //update time in seconds
	
	int updatetime = 0;
	int spawn_fruit = 60;
	Fruit fruit;
	
	Vertebra to_add;
	boolean to_add_new_vert = false;
	int colisionbox_folga = 5;
	
	Random rdm_int;
	
	Score score_actual;
	
	static TextureRegionDrawable iHorizontal,iVertical,iRightUp,iRightDown,iLeftUp,iLeftDown;
	
	public Snake(Vertebra cabeca,Table display_table,Score actual,GameMain maingame)
	{
		this.display_table = display_table;
		this.cabeca = cabeca;
		this.cur_dir = GameMain.dir_right;
		this.maingame = maingame;
		
		fruit = new Fruit(5);
		
		display_table.addActor(fruit);
		rdm_int = new Random();
		
		
		score_actual = actual;
	
		//generate the first fruit
		int y_r = this.rdm_int.nextInt((int)GameMain.lines);
		int x_r = this.rdm_int.nextInt((int)GameMain.columns);
		
		this.fruit.setPosition(GameMain.incW * x_r, GameMain.incH * y_r);
		this.fruit.setVisible(true);
		
		iHorizontal = new TextureRegionDrawable(new TextureRegion(GameMain.asm.get("data/snake-body/horizontal.png",Texture.class)));
		iVertical = new TextureRegionDrawable(new TextureRegion(GameMain.asm.get("data/snake-body/vertical.png",Texture.class)));
		iRightDown = new TextureRegionDrawable(new TextureRegion(GameMain.asm.get("data/snake-body/right-down.png",Texture.class)));
		iRightUp  = new TextureRegionDrawable(new TextureRegion(GameMain.asm.get("data/snake-body/right-up.png",Texture.class)));
		iLeftUp  = new TextureRegionDrawable(new TextureRegion(GameMain.asm.get("data/snake-body/left-up.png",Texture.class)));
		iLeftDown  = new TextureRegionDrawable(new TextureRegion(GameMain.asm.get("data/snake-body/left-down.png",Texture.class)));
	}
	
	
	
	public void addToTable()
	{
		Vertebra temp = cabeca;
		
		while(temp != null){
			this.display_table.addActor(temp);
			temp = temp.next_vertebra;
		}
	}
	
	public void addVertebra()
	{
		Vertebra temp = cabeca;
		Vertebra last_vert = null;
		
		while(temp != null){
			last_vert = temp;
			temp = temp.next_vertebra;
		}
		
		Vertebra vert_to_add = new Vertebra(last_vert.last_direction);

		vert_to_add.setPosition(last_vert.getX(), last_vert.getY());
		
		this.to_add = vert_to_add;
		this.to_add_new_vert = true;
	}
	
	
	
	public void genNewFruit()
	{
		this.score_actual.incScore(fruit);
		maingame.incFruits();
		
		int y_r;
		int x_r;
		int gen_times=0;

		while(true){
			gen_times++;
			y_r = this.rdm_int.nextInt((int)GameMain.lines);
			x_r = this.rdm_int.nextInt((int)GameMain.columns);
			boolean control = false;
			Vertebra temp = cabeca;
			while(temp !=null)
			{
				if(temp.getX() + colisionbox_folga >= x_r && temp.getX() + temp.getWidth() - colisionbox_folga <= x_r + temp.getWidth() && temp.getY() + colisionbox_folga >= y_r && temp.getY() + temp.getHeight() - colisionbox_folga <= y_r + temp.getHeight()){
					control = true;
				}
				
				temp = temp.next_vertebra;
			}
				
			if(!control) break;
			
		}
		
		this.fruit.setPosition(GameMain.incW * x_r, GameMain.incH * y_r);
		this.fruit.setVisible(true);
		
		this.addVertebra();
		Gdx.app.log("GEN FRUIT", "Generated " + gen_times + " times");
	}
	
	public int update()
	{
		if(updatetime >= speed * 60){
			if(cabeca.getX() + colisionbox_folga >= fruit.getX() && cabeca.getY() + colisionbox_folga>= fruit.getY() && cabeca.getX() + cabeca.getWidth() - colisionbox_folga<=  fruit.getX() + fruit.getWidth() && cabeca.getY() + cabeca.getHeight() - colisionbox_folga <= fruit.getY() + fruit.getHeight() ){
				Gdx.app.log("colision", "fruit");
				this.genNewFruit();
			}
			
			cabeca.setDir(this.cur_dir);
			//update cabeca image
			if(cabeca.direction.equals(GameMain.dir_down.cpy()) || cabeca.direction.equals(GameMain.dir_up.cpy())) cabeca.setDrawable(Snake.iVertical);
			else cabeca.setDrawable(Snake.iHorizontal);
			
			Vertebra aux = cabeca.next_vertebra;
			Vertebra previous = cabeca;
			while(aux != null){
				aux.setDir(previous.last_direction);
				
				previous = aux;
				aux = aux.next_vertebra;
				
				if(cabeca.getX() + colisionbox_folga >= previous.getX() && cabeca.getY() + colisionbox_folga>= previous.getY() && cabeca.getX() + cabeca.getWidth() - colisionbox_folga<=  previous.getX() + previous.getWidth() && cabeca.getY() + cabeca.getHeight() - colisionbox_folga <= previous.getY() + previous.getHeight() ){
					Gdx.app.log("colision", "body");
					return -1;
				}
				
			}
			
			
			//check collision with border
			if(cabeca.getX() < 0|| cabeca.getX() > GameMain.columns*GameMain.incW || cabeca.getY() < 0 || cabeca.getY() > GameMain.lines*GameMain.incH)
			{
				Gdx.app.log("colision", "border");
				return -1;
			}
			
			
			if(this.to_add_new_vert)
			{
				previous.next_vertebra = this.to_add;
				this.display_table.addActor(to_add);
				this.to_add_new_vert = false;
			}
			
			
			
			updatetime = 0;
			this.can_change_dir = true;
		}	
		else
			updatetime++;
		
		return 1;
	}
}
