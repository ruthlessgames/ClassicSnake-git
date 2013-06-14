package com.fcouceiro.classicsnake;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.fcouceiro.classicsnake.GameMain;
import com.fcouceiro.classicsnake.RequestHandlerBridge;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.android.gms.games.GamesClient;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.UserRecoverableNotifiedException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;



public class MainActivity extends AndroidApplication implements RequestHandlerBridge,GameHelper.GameHelperListener{

	AdView adView;
	GameHelper mHelper;
	
    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
    private final int SHOW_TOAST = 3;
    private final int SHOW_LEADERBOAR = 4;
    private final int SHOW_ACHIEVEMENTS = 7;
    private final int UNLOCK_ACHIEVEMENT = 5;
    private final int INC_ACHIEVEMENT = 6;
    private final int SUBMIT_SCORE = 8;
    
    private String toast_message = "";
    private String achieve_unlock_id = "";
    private int to_submit = 0;
    private int inc_achiev = 0;
    private boolean is_online_and_signed = false;
    private boolean startup = false;
    
    protected Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    break;
                }
                case SHOW_TOAST:{
                	Toast.makeText(adView.getContext(), toast_message, Toast.LENGTH_LONG).show();
            
                	break;
                }
                case SHOW_LEADERBOAR:{
                		startActivityForResult(mHelper.getGamesClient().getLeaderboardIntent(getString(R.string.leaderboardPub)),0);
                	break;
                }
                case UNLOCK_ACHIEVEMENT:
                	mHelper.getGamesClient().unlockAchievement(achieve_unlock_id);
                	break;
                case INC_ACHIEVEMENT:
                	mHelper.getGamesClient().incrementAchievement(achieve_unlock_id, inc_achiev);
                	break;
                case SHOW_ACHIEVEMENTS:
                	startActivityForResult(mHelper.getGamesClient().getAchievementsIntent(),0);
                	break;
                case SUBMIT_SCORE:
                	mHelper.getGamesClient().submitScore(getString(R.string.leaderboardPub), to_submit);
                	break;
            }
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instantiate base_game_act
        
       
        
        
        RelativeLayout layout = new RelativeLayout(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
       
        
        View gameView = initializeForView(new GameMain(this), false);
     
        AdRequest adreq = new AdRequest();
        //adreq.addTestDevice("90E7CA239B2B610FB2242326D511B267");

        
     // Create and setup the AdMob view
        adView = new AdView(this, AdSize.BANNER, "a151b46c7661def"); // Put in your secret key here
        adView.loadAd(adreq);

     // Add the libgdx view
        layout.addView(gameView);

        
        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(adView, adParams);
        
        mHelper = new GameHelper(this);
        mHelper.setup(this);
        
        // Hook it all up
        setContentView(layout);
        
    }

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void showToast(String to_show) {
		// TODO Auto-generated method stub
		this.toast_message = to_show;
		handler.sendEmptyMessage(SHOW_TOAST);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		this.showToast("Failed");
		is_online_and_signed = true;
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		this.showToast("Success");
		is_online_and_signed = true;
		
		this.unlockAchievement(3);
		
		
		if(!startup){
			this.incrementAchievement(4, 1);
		this.incrementAchievement(5, 1);
		this.incrementAchievement(6, 1);
	
		startup = true;
		}
	}

	@Override
	public void starConnections() {
		// TODO Auto-generated method stub
		this.runOnUiThread(new Runnable() {
			   public void run() {
			      //put your code here
				   mHelper.beginUserInitiatedSignIn();
			   }
			});
		
	}

	@Override
	public boolean getOnlineAndSigned() {
		// TODO Auto-generated method stub
		return this.is_online_and_signed;
	}

	@Override
	public void showLeaderboard() {
		// TODO Auto-generated method stub
		if(this.is_online_and_signed){
		handler.sendEmptyMessage(SHOW_LEADERBOAR);
		}
	}

	private String getAchievByIdx(int idx)
	{
		int f = 0;
		switch(idx){
		case 0:
			f = R.string.firstgame;
			break;
		case 1:
			f = R.string.firstmedium;
			break;
		case 2:
			f = R.string.firstmicro;
			break;
		case 3:
			f = R.string.firstinternet;
			break;
		case 4:
			f = R.string.rockie;
			break;
		case 5:
			f = R.string.gammer;
			break;
		case 6:
			f = R.string.veteran;
			break;
		case 7:
			f = R.string.eater;
			break;
		case 8:
			f = R.string.eaterII;
			break;
		case 9:
			f = R.string.eaterIII;
			break;
			
		}
		
		return getString(f);
	}
	
	@Override
	public void unlockAchievement(int idx) {
		// TODO Auto-generated method stub
		if(this.is_online_and_signed){
			this.achieve_unlock_id = this.getAchievByIdx(idx);
			handler.sendEmptyMessage(UNLOCK_ACHIEVEMENT);
		}
	}

	@Override
	public void incrementAchievement(int idx, int inc) {
		// TODO Auto-generated method stub
		if(this.is_online_and_signed){
			this.achieve_unlock_id = this.getAchievByIdx(idx);
			this.inc_achiev = inc;
			handler.sendEmptyMessage(INC_ACHIEVEMENT);
		}
	}

	@Override
	public void showAchievements() {
		// TODO Auto-generated method stub
		if(this.is_online_and_signed){
		handler.sendEmptyMessage(SHOW_ACHIEVEMENTS);
		}
	}

	@Override
	public void submitScore(int score) {
		// TODO Auto-generated method stub
		if(this.is_online_and_signed){
			to_submit = score;
			handler.sendEmptyMessage(SUBMIT_SCORE);
			}
	}


	
}
