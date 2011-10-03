package com.seriesmanager.views.mediator;


import com.seriesmanager.business.Serie;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SeriesDetailMediator {
	private Serie serie;
	private Button btnPlusSeason;
	private Button btnMinusSeason;
	private Button btnPlusEpisode;
	private Button btnMinusEpisode;
	private EditText etSeason;
	private EditText etEpisode;
	
	private OnClickListener oclPlusSeason = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			plusSeason();
			
		}
	};
	
	private OnClickListener oclMinusSeason = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			minusSeason();
			
		}
	};
	private OnClickListener oclPlusEpisode = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			plusEpisode();
			
		}
	};
	private OnClickListener oclMinusEpisode = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			minusEpisode();
			
		}
	};
	
	public SeriesDetailMediator(Serie serie, Button btnPlusSeason, Button btnMinusSeason,
			Button btnPlusEpisode, Button btnMinusEpisode,
			EditText etSeason, EditText etEpisode) {
		super();
		setSerie(serie);
		setBtnPlusSeason(btnPlusSeason);
		setBtnMinusSeason(btnMinusSeason);
		setBtnPlusEpisode(btnPlusEpisode);
		setBtnMinusEpisode(btnMinusEpisode);
		setEtSeason(etSeason);
		setEtEpisode(etEpisode);
		initialConfig();
	}


	

	public Serie getSerie() {
		return serie;
	}




	public void setSerie(Serie serie) {
		this.serie = serie;
	}




	public void setBtnPlusSeason(Button btnPlusSeason) {
		this.btnPlusSeason = btnPlusSeason;
	}



	public void setBtnMinusSeason(Button btnMinusSeason) {
		this.btnMinusSeason = btnMinusSeason;
	}



	public void setBtnPlusEpisode(Button btnPlusEpisode) {
		this.btnPlusEpisode = btnPlusEpisode;
	}



	public void setBtnMinusEpisode(Button btnMinusEpisode) {
		this.btnMinusEpisode = btnMinusEpisode;
	}



	public void setEtSeason(EditText etSeason) {
		this.etSeason = etSeason;
	}



	public void setEtEpisode(EditText etEpisode) {
		this.etEpisode = etEpisode;
	}
	
	public void initialConfig(){
		if(serie != null){
			updateView();
			
			btnPlusSeason.setOnClickListener(oclPlusSeason);
			btnMinusSeason.setOnClickListener(oclMinusSeason);
			btnPlusEpisode.setOnClickListener(oclPlusEpisode);
			btnMinusEpisode.setOnClickListener(oclMinusEpisode);
		}
	}
	
	private void updateView(){
		this.etSeason.setText(serie.getSeason().toString());
		this.etEpisode.setText(serie.getEpisode().toString());
		if(serie.getSeason() == 0){
			btnMinusSeason.setEnabled(false);
		}else{
			btnMinusSeason.setEnabled(true);
		}
		if(serie.getEpisode() == 0){
			btnMinusEpisode.setEnabled(false);
		}else{
			btnMinusEpisode.setEnabled(true);
		}
	}
	
	private void loadView(){
		if(Integer.valueOf(this.etSeason.getText().toString()) != serie.getSeason().intValue()){
			serie.setSeason(Integer.valueOf(this.etSeason.getText().toString()));
		}
		if(Integer.valueOf(this.etEpisode.getText().toString()) != serie.getEpisode().intValue()){
			serie.setEpisode(Integer.valueOf(this.etEpisode.getText().toString()));
		}
		
	}
	
	private void plusSeason(){
		if(serie != null){
			loadView();
			serie.setSeason(serie.getSeason()+1);
			updateView();
		}
	}

	private void minusSeason(){
		if(serie != null){
			loadView();
			if(serie.getSeason() > 0){
				serie.setSeason(serie.getSeason()-1);
			}
			updateView();
		}
	}
	
	private void plusEpisode(){
		if(serie != null){
			loadView();
			serie.setEpisode(serie.getEpisode()+1);
			updateView();
		}
	}
	private void minusEpisode(){
		if(serie != null){
			loadView();
			if(serie.getEpisode() > 0){
				serie.setEpisode(serie.getEpisode()-1);
			}
			updateView();
		}
	}

}
