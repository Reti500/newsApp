package com.nzuri.newsteller;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class Splash extends Activity implements AnimationListener {

	private final long SPLASH_SCREEN_DELAY = 4500;
	
	private ImageView newsteller;
	private Animation animFade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		newsteller = (ImageView) findViewById(R.id.newsTeller);
		
		animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
		animFade.setAnimationListener(this);
		
		newsteller.startAnimation(animFade);
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent().setClass(Splash.this, MainActivity.class);
				startActivity(mainIntent);
				overridePendingTransition(R.anim.alpha_fast, R.anim.alpha_fast);
				
				finish();
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task, SPLASH_SCREEN_DELAY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	@Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation
        // check for fade in animation
        if (animation == animFade) {
            Toast.makeText(getApplicationContext(), "Animation Stopped",
                    Toast.LENGTH_SHORT).show();
        }
 
    }
 
    @Override
    public void onAnimationRepeat(Animation animation) {
        // Animation is repeating
    }
 
    @Override
    public void onAnimationStart(Animation animation) {
        // Animation started
    }

}
