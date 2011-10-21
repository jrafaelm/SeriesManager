package com.seriesmanager;


import com.seriesmanager.activities.SeriesActivity;
import com.seriesmanager.persistence.PScript;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SeriesManagerActivity extends Activity {

	//DB manipulation variables
	
	PScript persistence;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        persistence = new PScript(this);
        persistence.close();
        
        Toast.makeText(this, this.getResources().getString(R.string.credits), Toast.LENGTH_LONG).show();
        
    
    }
    @Override
    public void onBackPressed() {
    }
    
    @Override
    protected void onResume() {
    	
    	super.onResume();
    	Handler h = new Handler();
    	h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				start();
			}
		}, 3000);
    	
    }
    
	private void start(){
		Intent i = new Intent(this, SeriesActivity.class);
		startActivity(i);
	}
	
}