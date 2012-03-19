package com.seriesmanager.views.mediator;


import java.util.HashMap;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seriesmanager.business.Serie;

public class SeriesDetailMediator {
	private Serie serie;
	private Button btnPlusSeason;
	private Button btnMinusSeason;
	private Button btnPlusEpisode;
	private Button btnMinusEpisode;
	private EditText etSeason;
	private EditText etEpisode;
	private EditText etTitle;
	private TextView tvReleased;
	private TextView tvPlot;
	private TextView tvRating;
	private TextView tvVotes;
	private TextView tvWriter;
	private TextView tvActors;
	private TextView tvGenres;
	private TextView tvRuntime;
	private TextView tvYear;
	private TextView tvLastUpdate;
	private TextView tvEpisodeName;
	private TextView tvLastSeasonEpisode;
	private CheckBox cbAutomaticChange;
	private LinearLayout llDetails;
	private ImageView ivPoster;
	
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
	
	private OnCheckedChangeListener occlAutomaticChange = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			serie.setAutomaticChange(isChecked);
			btnPlusSeason.setEnabled(true);
			btnMinusSeason.setEnabled(true);
			btnPlusEpisode.setEnabled(true);
			btnMinusEpisode.setEnabled(true);
			
		}
	};


	

	public SeriesDetailMediator(Serie serie, Button btnPlusSeason,
			Button btnMinusSeason, Button btnPlusEpisode,
			Button btnMinusEpisode, EditText etSeason, EditText etEpisode,
			EditText etTitle, TextView tvReleased, TextView tvPlot,
			TextView tvRating, TextView tvVotes,
			TextView tvWriter, TextView tvActors, TextView tvGenres,
			TextView tvRuntime, TextView tvYear, TextView tvLastUpdate,
			TextView tvEpisodeName,TextView tvLastSeasonEpisode, LinearLayout llDetails, 
			ImageView ivPoster, CheckBox cbAutomaticChange) {
		super();
		this.serie = serie;
		this.btnPlusSeason = btnPlusSeason;
		this.btnMinusSeason = btnMinusSeason;
		this.btnPlusEpisode = btnPlusEpisode;
		this.btnMinusEpisode = btnMinusEpisode;
		this.etSeason = etSeason;
		this.etEpisode = etEpisode;
		this.etTitle = etTitle;
		this.tvReleased = tvReleased;
		this.tvPlot = tvPlot;
		this.tvRating = tvRating;
		this.tvVotes = tvVotes;
		this.tvWriter = tvWriter;
		this.tvActors = tvActors;
		this.tvGenres = tvGenres;
		this.tvRuntime = tvRuntime;
		this.tvYear = tvYear;
		this.tvLastUpdate = tvLastUpdate;
		this.tvEpisodeName = tvEpisodeName;
		this.tvLastSeasonEpisode = tvLastSeasonEpisode;
		this.llDetails = llDetails;
		this.ivPoster = ivPoster;
		this.cbAutomaticChange = cbAutomaticChange;
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
			cbAutomaticChange.setOnCheckedChangeListener(occlAutomaticChange);
		}
	}
	
	public void updateView(){
		Handler h = new Handler();
		h.post(new Runnable() {
			
			@Override
			public void run() {
				etSeason.setText(serie.getSeason().toString());
				etEpisode.setText(serie.getEpisode().toString());
				if(serie.getTitle() != null && !serie.getTitle().equals(etTitle.getText().toString()))
				{
					etTitle.setText(serie.getTitle());
				}
				if( (serie.getSeasonEpisodes() == null || serie.getSeasonEpisodes().size() == 0) && (serie.getEpisodes()!= null && serie.getEpisodes().size()>0))
				{
					serie.setUpSeriesEpisodes();
				}
				setUpButtons();
				if(serie.getLastUpdate() != null){
					
					tvReleased.setText(serie.getReleased());
					tvPlot.setText(serie.getPlot()); 
//					tvImdb.setText(serie.getImdburl()); 
//					Linkify.addLinks(tvImdb, Linkify.WEB_URLS);
					tvRating.setText(serie.getRating());
					tvVotes.setText(serie.getVotes()); 
					tvWriter.setText(serie.getWriter());
					tvActors.setText(serie.getActors());
					tvGenres.setText(serie.getGenres());
					tvRuntime.setText(serie.getRuntime());
					tvYear.setText(serie.getYear());
					tvLastUpdate.setText(serie.getlastUpdateFormated()); 
					tvEpisodeName.setText(serie.getEpisodeName(serie.getSeason(),serie.getEpisode()));
					tvLastSeasonEpisode.setText(serie.getLastSeasonEpisode());
					llDetails.setVisibility(LinearLayout.VISIBLE);
					cbAutomaticChange.setChecked(serie.hasAutomaticChange());
					if(serie.getPosterUrl()!= null){
						ivPoster.setImageBitmap(BitmapFactory.decodeFile(serie.getDefaultImageFileString()));
					}
				}else{
					llDetails.setVisibility(LinearLayout.INVISIBLE);
				}
			}

			private void setUpButtons() {
				if( !cbAutomaticChange.isChecked() || (serie.getSeasonEpisodes() != null && serie.getSeasonEpisodes().containsKey(serie.getSeason())) 
						&& (serie.getEpisode() <= serie.getSeasonEpisodes().get(serie.getSeason()))){
					btnPlusSeason.setEnabled(true);
					btnMinusSeason.setEnabled(true);
					btnPlusEpisode.setEnabled(true);
					btnMinusEpisode.setEnabled(true);
				}else if(serie.getSeason() == 0 || serie.getEpisode() == 0) {
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
				}else{
					btnPlusEpisode.setEnabled(false);
					btnPlusSeason.setEnabled(false);
				}
				
				
				if( serie.getEpisodes() ==null || serie.getEpisodes().size() == 0){
					btnPlusSeason.setEnabled(true);
					btnPlusEpisode.setEnabled(true);
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
			}
		});
		
		
	}
	
	private void loadView(){
		if(this.etSeason.getText().toString().equals("")){
			this.etSeason.setText("0");
		}
		if(this.etEpisode.getText().toString().equals("")){
			this.etEpisode.setText("0");
		}
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
			serie.setEpisode(0);
			
			setUpPLusButtons();
			
			updateView();
		}
	}




	private void setUpPLusButtons() {
			if(serie.getEpisodes() != null && serie.getEpisodes().size()>0){
				HashMap<Integer, Integer> seasonEpisodes = serie.getSeasonEpisodes();
				if(seasonEpisodes.containsKey(serie.getSeason())){
					
					if(seasonEpisodes.get(serie.getSeason()) !=null && serie.getEpisode() <= seasonEpisodes.get(serie.getSeason())){
						tvEpisodeName.setText(serie.getEpisodeName(serie.getSeason(), serie.getEpisode()));
					}else if(cbAutomaticChange.isChecked()){
						
						if(seasonEpisodes.containsKey(serie.getSeason()+1)){
							serie.setSeason(serie.getSeason()+1);
							serie.setEpisode(0);
							etSeason.setText(serie.getSeason().toString());
							etEpisode.setText("0");
						}else{
							serie.setSeason(serie.getSeason()+1);
							serie.setEpisode(0);
							etSeason.setText(serie.getSeason().toString());
							etEpisode.setText("0");
							btnPlusSeason.setEnabled(false);
							btnPlusEpisode.setEnabled(false);
						}
						
					}else{
						etSeason.setText(serie.getSeason().toString());
						etEpisode.setText(serie.getEpisode().toString());
					}
				}
			}
	}

	
	private void minusSeason(){
		if(serie != null){
			loadView();
			if(serie.getSeason() > 0){
				serie.setSeason(serie.getSeason()-1);
			}
			if(serie.getEpisodes() != null && serie.getEpisodes().size()>0){
				HashMap<Integer, Integer> seasonEpisodes = serie.getSeasonEpisodes();
				if(seasonEpisodes.containsKey(serie.getSeason())){
					if(serie.getEpisode() <= seasonEpisodes.get(serie.getSeason())){
						tvEpisodeName.setText(serie.getEpisodeName(serie.getSeason(), serie.getEpisode()));
					}
				}
			}
			updateView();
		}
	}
	
	private void plusEpisode(){
		if(serie != null){
			loadView();
			serie.setEpisode(serie.getEpisode()+1);
			setUpPLusButtons();
			updateView();
		}
	}
	private void minusEpisode(){
		if(serie != null){
			loadView();
			if(serie.getEpisode() > 0){
				serie.setEpisode(serie.getEpisode()-1);
			}
			if(serie.getEpisodes() != null && serie.getEpisodes().size()>0){
				HashMap<Integer, Integer> seasonEpisodes = serie.getSeasonEpisodes();
				if(serie.getEpisode().intValue() == 0 && seasonEpisodes.get(serie.getSeason()-1)!=null && seasonEpisodes.get(serie.getSeason()-1) > 0){
					serie.setSeason(serie.getSeason()-1);
					serie.setEpisode(seasonEpisodes.get(serie.getSeason()));
					etSeason.setText((serie.getSeason().toString()));
					etEpisode.setText(serie.getEpisode().toString());
					tvEpisodeName.setText(serie.getEpisodeName(serie.getSeason(), serie.getEpisode()));
				}
				if(seasonEpisodes.containsKey(serie.getSeason())){
					if(serie.getEpisode() <= seasonEpisodes.get(serie.getSeason())){
						tvEpisodeName.setText(serie.getEpisodeName(serie.getSeason(), serie.getEpisode()));
					}
				}
			}
			updateView();
		}
	}

}
