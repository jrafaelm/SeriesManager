package com.seriesmanager;


import java.io.File;


import com.airpush.android.Airpush;
import com.seriesmanager.activities.SeriesActivity;
import com.seriesmanager.persistence.PScript;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SeriesManagerActivity extends Activity {
	public static final String SERIES_MANAGER_FOLDER = Environment
	.getExternalStorageDirectory() + "/SeriesManager";
	public static final String IMAGES_FOLDER = SERIES_MANAGER_FOLDER
	+ "/Images";
	//DB manipulation variables
	
	PScript persistence;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        AirpushAd ad= new AirpushAd();
//        ad.advertisement(getApplicationContext());
        File diretorio1 = new File(SERIES_MANAGER_FOLDER);
		diretorio1.mkdir();
        File diretorio2 = new File(IMAGES_FOLDER);
		diretorio2.mkdir();
        
        persistence = new PScript(this);
        persistence.close();
        
       
    
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
		}, 2000);
    	
    }
    
	private void start(){
		Intent i = new Intent(this, SeriesActivity.class);
		startActivity(i);
	}
	
}