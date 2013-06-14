package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameInput implements GestureListener,InputProcessor{

	Snake snake;
	
	public GameInput(Snake snake){
		this.snake = snake;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		
		if (snake.can_change_dir) {
			Vector2 new_dir = null;
			//movimento vai ser na horizontal
			if (Math.abs(velocityX) > Math.abs(velocityY)) {
				if (velocityX >= 0)
					new_dir = GameMain.dir_right;
				else
					new_dir = GameMain.dir_left;
			} else { //movimento na vertical
				if (velocityY >= 0)
					new_dir = GameMain.dir_down;
				else
					new_dir = GameMain.dir_up;
			}
			if (new_dir != null
					&& new_dir.cpy().mul(-1f).angle() != snake.cur_dir.angle()){
				snake.cur_dir = new_dir;
				snake.can_change_dir = false;
			}
			
			
		}
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (snake.can_change_dir) {
			// TODO Auto-generated method stub
			Vector2 new_dir = null;
			if (keycode == Keys.DPAD_UP) {
				new_dir = GameMain.dir_up;
			} else if (keycode == Keys.DOWN) {
				new_dir = GameMain.dir_down;
			} else if (keycode == Keys.RIGHT) {
				new_dir = GameMain.dir_right;
			} else if (keycode == Keys.LEFT) {
				new_dir = GameMain.dir_left;
			}
			if (new_dir != null
					&& new_dir.cpy().mul(-1f).angle() != snake.cur_dir.angle()){
				snake.cur_dir = new_dir;
				snake.can_change_dir = false;
			}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
