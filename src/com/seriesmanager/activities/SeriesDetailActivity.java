package com.seriesmanager.activities;

import com.seriesmanager.R;
import com.seriesmanager.business.Serie;
import com.seriesmanager.persistence.PSerie;
import com.seriesmanager.views.SerieDetailView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SeriesDetailActivity extends Activity {
	Intent starter;
	Serie serie;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serie_detail);
		starter = getIntent();
		SerieDetailView screen= new SerieDetailView(this);
		setContentView(screen);
		if(starter.getExtras() != null && starter.getExtras().getSerializable("serie") != null){
			serie = (Serie) starter.getExtras().getSerializable("serie");
			screen.setSerie(serie);
			screen.initiateMediator();
		}
		
		
	}
	
	
	
}
