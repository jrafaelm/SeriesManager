package com.seriesmanager.activities;

import com.seriesmanager.R;
import com.seriesmanager.business.Serie;
import com.seriesmanager.persistence.PSerie;
import com.seriesmanager.views.SerieDetailView;
import com.seriesmanager.views.mediator.SeriesDetailMediator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SeriesDetailActivity extends Activity {
	Intent starter;
	Serie serie;
	Button btnPlusSeason;
	Button btnMinusSeason;
	Button btnPlusEpisode;
	Button btnMinusEpisode;
	EditText etSeason;
	EditText etEpisode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serie_detail);
		starter = getIntent();
		serie = (Serie) starter.getSerializableExtra("serie");
		btnPlusSeason = (Button) findViewById(R.id.btnPlusSeason);
		btnMinusSeason = (Button) findViewById(R.id.btnMinusSeason);
		btnPlusEpisode = (Button) findViewById(R.id.btnPlusEpisode);
		btnMinusEpisode = (Button) findViewById(R.id.btnMinusEpisode);
		etSeason = (EditText) findViewById(R.id.etSeason);
		etEpisode = (EditText) findViewById(R.id.etEpisode);
		SeriesDetailMediator mediator = new SeriesDetailMediator(serie, btnPlusSeason, btnMinusSeason, btnPlusEpisode, btnMinusEpisode, etSeason, etEpisode);
	}
	
	
	
	public void deleteSerie(View v){
		
	}
}
