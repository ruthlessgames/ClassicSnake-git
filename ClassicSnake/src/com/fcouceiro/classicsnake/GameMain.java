package com.fcouceiro.classicsnake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ruthlessgames.api.StylesManager;

public class GameMain extends Game {
	private OrthographicCamera camera;
	public static SpriteBatch batch;
	public static BitmapFont main_font;
	private GameDisplay game;
	public WaitScreen wait_screen;
	public static Vector2 dir_right,dir_left,dir_up,dir_down,for_next_vert;
	public static boolean loaded = false;
	public static AssetManager asm;
	
	public static float incH,incW,ui_incH,ui_incW,lines,columns;
	public static float w,h;
	public static int speed_s,size_g;
	public static float ratio;
	
	//score statics
	public static int best_session_score = -1;
	public static int last_score = -1;
	//android bridge
	RequestHandlerBridge android_bridge;
	
	public GameMain(RequestHandlerBridge bridge)
	{
		this.android_bridge = bridge;
	}
	
	
	@Override
	public void create() {		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		ratio = h / w;
		
		camera = new OrthographicCamera(w, h);
		Gdx.app.log("Display", w +"x" + h);
		batch = new SpriteBatch();
		batch.getTransformMatrix().setToTranslation(-1*(w/2), -1*(h/2), 0);
		
		//load arial font
		main_font = new BitmapFont(Gdx.files.internal("default/UI/fonts/arial.fnt"),
		         Gdx.files.internal("default/UI/fonts/arial.png"), false);
		
		//set initial values
		dir_right = new Vector2(1,0);
		dir_left = new Vector2(-1,0);
		dir_up = new Vector2(0,1);
		dir_down = new Vector2(0,-1);
		
		ui_incH = (int) (h / (ratio * 16));
		ui_incW = (int) (w / 16);
		
		//setup styles manager
		StylesManager setup_styles = new StylesManager("default");
		
		asm = new AssetManager();
		GameLoad loading_screen = new GameLoad(this);
		loading_screen.setAssetManager(asm);
		
		
		this.setScreen(loading_screen);
		
	}
	
	public boolean pauseOnGoingGame(){
		if(game != null && !game.ended){
			game.pause_ingame();
			return true;
		}
		
		return false;
	}
	
	public void resumeOnGoingGame()
	{
		game.resume_ingame();
	}
	
	public void startGame(float snake_speed)
	{
		//reset directions
		if(game != null) game.dispose();
		game = null;
		game = new GameDisplay(this,snake_speed);
		this.setScreen(game);
		this.android_bridge.showUiLayout(false);
	}
	
}
