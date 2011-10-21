package com.seriesmanager.activities;

import java.util.ArrayList;

import com.seriesmanager.R;
import com.seriesmanager.adapters.SerieAdapter;
import com.seriesmanager.business.Serie;
import com.seriesmanager.persistence.PSerie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SeriesActivity extends Activity {
	
	// Widgets
	ListView lvSeries;
	SerieAdapter adp;
	AlertDialog.Builder alert;
	EditText input;
	
	// Menu items constants. 
	public static final int MENU_ADD = 0;
	
	ArrayList<Serie> series = new ArrayList<Serie>();
	Serie clickedSerie;
	Context ctx = this;
	
	//definition of an click listener for the items in the listview.
	OnItemClickListener ocl = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,  int position, long id) {
			if(lvSeries.getItemAtPosition(position)!=null){
				clickedSerie =(Serie) lvSeries.getItemAtPosition(position);
				Intent i = new Intent(ctx, SeriesDetailActivity.class);
//				Bundle extras = new Bundle();
//				extras.putLong("idSerie", clickedSerie.getId());
//				i.putExtras(extras);
				i.putExtra("serie", clickedSerie);
				startActivity(i);
			}
		}
	};
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.series);

		lvSeries = (ListView) findViewById(R.id.lvSeries);
		lvSeries.setOnItemClickListener(ocl);
	}
	
	/**
	 * Called when the activity regain focus.
	 * used to update the series list.
	 * */
	@Override
	protected void onResume() {
		
		super.onResume();
		
		listSeries();
}	
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
	
	
	
	/**
	 * Method to list series into the listview
	 * */
	
	private void listSeries(){
		PSerie persistence= new PSerie(this);
		series = persistence.listAllSeries(this);
		adp = new SerieAdapter(this, series);
		lvSeries.setAdapter(adp);
		adp.notifyDataSetChanged();
	}
	
	
	public void addSerie(View v){
		Intent i = new Intent(ctx, SeriesDetailActivity.class);
		i.putExtra("serie", new Serie());
		startActivity(i);
	}
}
