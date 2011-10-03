package com.seriesmanager.views;




import com.seriesmanager.business.Serie;
import com.seriesmanager.persistence.PSerie;
import com.seriesmanager.views.mediator.SeriesDetailMediator;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * class to build a view for a series detail 
 * */
public class SerieDetailView extends LinearLayout{
	private Serie serie;
	EditText etName;
	private Button btnPlusSeason;
	private Button btnMinusSeason;
	private Button btnPlusEpisode;
	private Button btnMinusEpisode;
	private EditText etSeason;
	private EditText etEpisode;
	private Context context;
	public SerieDetailView(final Context context) {
		super(context);
		
		this.context = context;

		// building the view
		LinearLayout generalLayout = new LinearLayout(context);
		LinearLayout.LayoutParams generalLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		generalLayout.setOrientation(VERTICAL);
		//init definition of name layout
		LinearLayout nameLayout = new LinearLayout(context);
		LinearLayout.LayoutParams nameLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		etName = new EditText(context);
		
		etName.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams nameParam = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 50);
		//etName.setPadding(17, 12, 5, 0);

		nameLayout.addView(etName, nameParam);

		//end definition of name layout
		
		//init definition of season layout
		LinearLayout seasonLayout = new LinearLayout(context);
		LinearLayout.LayoutParams seasonLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		seasonLayout.setOrientation(HORIZONTAL);
		TextView tvSeason = new TextView(context);
		tvSeason.setText("Season ");
		tvSeason.setTextColor(Color.LTGRAY);
		tvSeason.setTextSize(16);
		tvSeason.setShadowLayer(3, 2, 2, Color.GRAY);
		LinearLayout.LayoutParams seasonParam = new LinearLayout.LayoutParams(150, 50);
		LinearLayout.LayoutParams widgetParam = new LinearLayout.LayoutParams(50, 50);
		tvSeason.setPadding(17, 12, 0, 0);
		
		seasonLayout.addView(tvSeason, seasonParam);
		
		btnMinusSeason = new Button(context);
		btnMinusSeason.setText(" - ");
		etSeason = new EditText(context);
		//determine that only digits will be accepted 
		etSeason.setKeyListener(new DigitsKeyListener(false, true));
		//determine length limit
		InputFilter[] filterArray = new InputFilter[1];
		filterArray[0] = new InputFilter.LengthFilter(3);
		etSeason.setFilters(filterArray);
		btnPlusSeason = new Button(context);
		btnPlusSeason.setText(" + ");
		
		seasonLayout.addView(btnMinusSeason , widgetParam);
		seasonLayout.addView(etSeason, widgetParam);
		seasonLayout.addView(btnPlusSeason, widgetParam);
		
		//end season layout
		
		//init definition of episode layout
		LinearLayout episodeLayout = new LinearLayout(context);
		LinearLayout.LayoutParams episodeLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		episodeLayout.setOrientation(HORIZONTAL);
		TextView tvEpisode = new TextView(context);
		tvEpisode.setText("Episode ");
		tvEpisode.setTextColor(Color.LTGRAY);
		tvEpisode.setTextSize(16);
		tvEpisode.setShadowLayer(3, 2, 2, Color.GRAY);
		LinearLayout.LayoutParams episodeParam = new LinearLayout.LayoutParams(150, 250);
		tvEpisode.setPadding(17, 12, 0, 0);
		
		episodeLayout.addView(tvEpisode, episodeParam);
		
		btnMinusEpisode = new Button(context);
		btnMinusEpisode.setText(" - ");
		etEpisode = new EditText(context);
		//determine that only digits will be accepted 
		etEpisode.setKeyListener(new DigitsKeyListener(false, true));
		//determine length limit
		etEpisode.setFilters(filterArray);
		btnPlusEpisode = new Button(context);
		btnPlusEpisode.setText(" + ");
		
		episodeLayout.addView(btnMinusEpisode , widgetParam);
		episodeLayout.addView(etEpisode, widgetParam);
		episodeLayout.addView(btnPlusEpisode, widgetParam);
		
		//end episode layout
		
		//defining buttons layout
		LinearLayout buttonsLayout = new LinearLayout(context);
		LinearLayout.LayoutParams buttonsLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		buttonsLayout.setOrientation(HORIZONTAL);
		buttonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		//defining save changes button
		Button btnSaveChanges = new Button(context);
		btnSaveChanges.setText("Save Changes");
		btnSaveChanges.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveChanges();
			}
		});
		LayoutParams btnParams = new LayoutParams(150, LayoutParams.WRAP_CONTENT);
		//end save changes button
		
		//defining delete button
		Button btnDelete = new Button(context);
		btnDelete.setText("Delete");
		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				confirmDialog();
			}
		});
		
		//end delete button
		buttonsLayout.addView(btnSaveChanges, btnParams);
		buttonsLayout.addView(btnDelete, btnParams);
		//adding the views
		generalLayout.addView(nameLayout, nameLayoutParams);
		generalLayout.addView(seasonLayout, seasonLayoutParams);
		generalLayout.addView(episodeLayout, episodeLayoutParams);
		generalLayout.addView(buttonsLayout, buttonsLayoutParams);
		 
		addView(generalLayout, generalLayoutParams);
	}

	public void setSerie(Serie serie){
		this.serie = serie;
		etName.setText(serie.getName());
	}
	
	private void saveChanges(){
		serie.setName(etName.getText().toString());
		serie.setSeason(Integer.valueOf(etSeason.getText().toString()));
		serie.setEpisode(Integer.valueOf(etEpisode.getText().toString()));
		PSerie persistence = new PSerie(context);
		if(persistence.searchSerie(context, serie.getId()).getId() != 0){
			persistence.updateSerie(serie);
			Toast.makeText(context, "Changes saved!", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(context, "This serie was deleted, press back!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void confirmDialog(){
		Builder alert = new Builder(context);
		alert.setMessage("Are you sure?");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				deleteSerie();
				
			}
		});
		alert.setNegativeButton("No", null);
		alert.show();
	}
	
	private void deleteSerie(){
		PSerie persistence = new PSerie(context);
		persistence.excludeSerieBd(serie.getId());
		Toast.makeText(context, "Serie deleted!", Toast.LENGTH_LONG).show();
	}
	public void initiateMediator(){
		SeriesDetailMediator mediator = new SeriesDetailMediator(this.serie, btnPlusSeason, btnMinusSeason, btnPlusEpisode, btnMinusEpisode, etSeason, etEpisode);
	}
}
