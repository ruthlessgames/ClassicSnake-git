package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Gdx;

public class Score {

	float final_score;
	float factor;
	
	public Score(int speed,int size)
	{
		factor = speed * size + 1;
	}
	
	public void incScore(Fruit fr)
	{
		this.final_score += fr.getValue() * this.factor;
		Gdx.app.log("score", this.final_score + "");
	}
	
	boolean greater(Score other){
		return(final_score > other.final_score);
	}
}
