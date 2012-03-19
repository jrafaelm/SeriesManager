package com.seriesmanager.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.seriesmanager.R;
import com.seriesmanager.adapters.SerieAdapter;
import com.seriesmanager.business.Serie;
import com.seriesmanager.persistence.PEpisode;
import com.seriesmanager.persistence.PSerie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
	int visibleTop = 0;
	int idVisiblePosition = 0;
	long saveResult = -1;
	boolean addFlag = true;
	Context context = this;
	private static final int RESULT_ADD = 3;
	private static final int RESULT_UPDATE = 4;
	// Menu items constants. 
	public static final int MENU_ADD = 0;
	private static final int RESULT_DELETE = 5;
	
	ArrayList<Serie> series = new ArrayList<Serie>();
	Serie clickedSerie;
	Context ctx = this;
	
	//definition of an click listener for the items in the listview.
	OnItemClickListener ocl = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,  int position, long id) {
			if(lvSeries.getItemAtPosition(position)!=null){
				clickedSerie =(Serie) lvSeries.getItemAtPosition(position);
				clickedSerie.setThumb(null);
				Intent i = new Intent(ctx, SeriesManagementActivity.class);
//				Bundle extras = new Bundle();
//				extras.putLong("idSerie", clickedSerie.getId());
//				i.putExtras(extras);
				i.putExtra("serie", clickedSerie);
				saveListPosition();
				startActivityForResult(i, 0);
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

		listSeries();
	}
	
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Serie updatedSerie;	
		int index = -1;
		switch (resultCode) {
		case RESULT_ADD:
			updatedSerie = (Serie) data.getSerializableExtra("serie");
			reSetThumb(updatedSerie);
			series.add(updatedSerie);
			addFlag = true;
			new SaveChangesAsyncTask().execute(updatedSerie);
			break;
		case RESULT_UPDATE:
			updatedSerie = (Serie) data.getSerializableExtra("serie");
			for(Serie s : series){
				index++;
				if(s.getId() == updatedSerie.getId()){
					break;
				}
			}
			if( index > -1){
				series.remove(index);
				reSetThumb(updatedSerie);
				series.add(updatedSerie);
			}
			
			addFlag = false;
			new SaveChangesAsyncTask().execute(updatedSerie);
			break;
			
		case RESULT_DELETE:
			updatedSerie = (Serie) data.getSerializableExtra("serie");
			for(Serie s : series){
				index++;
				if(s.getId() == updatedSerie.getId()){
					break;
				}
			}
			if( index > -1){
				series.remove(index);
			}
			break;
		
		}
		lvSeries.setSelectionFromTop(idVisiblePosition, visibleTop);
		if(resultCode == RESULT_ADD || resultCode == RESULT_UPDATE || resultCode == RESULT_DELETE){
			updateView();
		}else{
			if(clickedSerie != null){
				reSetThumb(clickedSerie);
			}
		}
	}

	private void reSetThumb(Serie updatedSerie) {
		Bitmap image = BitmapFactory.decodeFile(updatedSerie.getDefaultImageFileString());
		if(image != null){
			image = Bitmap.createScaledBitmap(image, 100, 100, true);
			updatedSerie.setThumb(image);
		}
	}
	
	/**
	 * Method to list series into the listview
	 * */
	
	private void listSeries(){
		PSerie persistence= new PSerie(this);
		series = persistence.listAllSeries(this);
		updateView();
	}

	private void updateView() {
		Collections.sort(series);
		adp = new SerieAdapter(this, series);
		lvSeries.setAdapter(adp);
		lvSeries.setSelectionFromTop(idVisiblePosition, visibleTop);
		adp.notifyDataSetChanged();
	}
	
	
	public void addSerie(View v){
		Intent i = new Intent(ctx, SeriesManagementActivity.class);
		i.putExtra("serie", new Serie());
		saveListPosition();
		startActivityForResult(i, 0);
	}
	
	private void saveListPosition(){
		// save index and top position
		idVisiblePosition = lvSeries.getFirstVisiblePosition();
		View v = lvSeries.getChildAt(0);
		visibleTop = (v == null) ? 0 : v.getTop();

	}
	
	
	
	private long save(Serie serie){
		PSerie persistence = new PSerie(this);
		PEpisode persistenceEpi = new PEpisode(this);
		if(addFlag){
			

			persistenceEpi.addEpisodes(serie);
			serie.setId(persistence.addSerie(serie));
			return serie.getId();
			
		}else{
			
			if(persistenceEpi.searchFirstEpisode(serie).getId()!=0){
				persistenceEpi.updateEpisodes(serie);
			}else{
				persistenceEpi.addEpisodes(serie);
			}
			persistence.updateSerie(serie);
			return serie.getId();
		}
		
		
	}
	
	private class SaveChangesAsyncTask extends AsyncTask<Serie, Void, Long>{
		
		@Override
		protected Long doInBackground(Serie... series) {
			
			Long saveResult = save(series[0]);
			return saveResult;
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			
		}
		
		@Override
		protected void onPostExecute(final Long result) {
			super.onPostExecute(result);
			if(result.longValue() == -1){

				Looper.prepare();
				Handler h = new Handler();
				h.post(new Runnable() {
					
					@Override
					public void run() {
						
							Toast.makeText(context, context.getString(R.string.save_problem),Toast.LENGTH_LONG).show();
						
					}
				});
			}
		}
	}
}
