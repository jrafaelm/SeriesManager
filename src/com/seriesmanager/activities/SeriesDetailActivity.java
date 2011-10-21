package com.seriesmanager.activities;

import com.seriesmanager.R;
import com.seriesmanager.business.Serie;
import com.seriesmanager.persistence.PSerie;
import com.seriesmanager.views.mediator.SeriesDetailMediator;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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
	EditText etTitle;
	Context context = this;
	
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
		etTitle = (EditText) findViewById(R.id.etTitle);
		etTitle.setText(serie.getName());
		SeriesDetailMediator mediator = new SeriesDetailMediator(serie, btnPlusSeason, btnMinusSeason, btnPlusEpisode, btnMinusEpisode, etSeason, etEpisode);
	}
	
	@Override
	public void onBackPressed() {
		saveChanges();
		finish();
		
	}
	
	private void saveChanges(){
		serie.setName(etTitle.getText().toString());
		if(serie.getName() == null || serie.getName().equals("")){
			Builder alert = new Builder(context);
			alert.setMessage(context.getString(R.string.insert_title));
			alert.setNeutralButton(context.getString(R.string.ok), null);
		}else{
			serie.setSeason(Integer.valueOf(etSeason.getText().toString()));
			serie.setEpisode(Integer.valueOf(etEpisode.getText().toString()));
			PSerie persistence = new PSerie(context);
			if(serie.getId() != 0){
				persistence.updateSerie(serie);
			}else{
				persistence.addSerie(serie);
			}
		}
	}
	
	public void deleteSerie(View v){
		if(serie.getId() != 0){
		Builder alert = new Builder(context);
			alert.setMessage(context.getString(R.string.confirm));
			alert.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					PSerie persistence = new PSerie(context);
					persistence.excludeSerieBd(serie.getId());
					finish();
				}
			});
			alert.setNegativeButton(context.getString(R.string.no), null);
			alert.show();
		}else{
			finish();
		}
	
	}
}
