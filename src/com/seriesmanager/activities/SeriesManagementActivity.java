package com.seriesmanager.activities;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.seriesmanager.R;
import com.seriesmanager.business.Serie;
import com.seriesmanager.connectivity.ServicesParser;
import com.seriesmanager.connectivity.Utils;
import com.seriesmanager.persistence.PEpisode;
import com.seriesmanager.persistence.PSerie;
import com.seriesmanager.views.mediator.SeriesDetailMediator;

public class SeriesManagementActivity extends Activity {

	private static int RESULT_ADD = 3;
	private static int RESULT_UPDATE = 4;
	private static int RESULT_DELETE = 5;
	private static String URL_SM = "http://goo.gl/iyBGA";
	
	Intent starter;
	Serie serie;
	Button btnPlusSeason;
	Button btnMinusSeason;
	Button btnPlusEpisode;
	Button btnMinusEpisode;
	EditText etSeason;
	EditText etEpisode;
	EditText etTitle;
	TextView tvReleased;
	TextView tvPlot;
	TextView tvRating;
	TextView tvVotes;
	TextView tvWriter;
	TextView tvActors;
	TextView tvGenres;
	TextView tvRuntime;
	TextView tvYear;
	TextView tvLastUpdate;
	TextView tvEpisodeName;
	TextView tvLastSeasonEpisode;
	CheckBox cbAutomaticChange;
	LinearLayout llDetails;
	ImageView ivPoster;
	Context context = this;
	ProgressDialog pd;
	String spinnerChoice;
	SeriesDetailMediator mediator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.serie_management);
		starter = getIntent();
		serie = (Serie) starter.getSerializableExtra("serie");
		btnPlusSeason = (Button) findViewById(R.id.btnPlusSeason);
		btnMinusSeason = (Button) findViewById(R.id.btnMinusSeason);
		btnPlusEpisode = (Button) findViewById(R.id.btnPlusEpisode);
		btnMinusEpisode = (Button) findViewById(R.id.btnMinusEpisode);
		etSeason = (EditText) findViewById(R.id.etSeason);
		etEpisode = (EditText) findViewById(R.id.etEpisode);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etTitle.setText(serie.getTitle());
		tvReleased =(TextView) findViewById(R.id.tvReleased);
		tvPlot =(TextView) findViewById(R.id.tvPlot);
		tvRating =(TextView) findViewById(R.id.tvRating);
		tvVotes =(TextView) findViewById(R.id.tvVotes);
		tvWriter =(TextView) findViewById(R.id.tvWriter);
		tvActors =(TextView) findViewById(R.id.tvActors);
		tvGenres =(TextView) findViewById(R.id.tvGenres);
		tvRuntime =(TextView) findViewById(R.id.tvRuntime);
		tvYear =(TextView) findViewById(R.id.tvYear);
		tvLastUpdate =(TextView) findViewById(R.id.tvLastUpdate);
		tvLastSeasonEpisode =(TextView) findViewById(R.id.tvLastSeasonEpisode);
		tvEpisodeName =(TextView) findViewById(R.id.tvEpisodeName);
		llDetails =(LinearLayout) findViewById(R.id.llDetails);
		ivPoster =(ImageView) findViewById(R.id.ivPoster);
		cbAutomaticChange = (CheckBox) findViewById(R.id.cbAutomaticChange);
		mediator = new SeriesDetailMediator(serie, btnPlusSeason, btnMinusSeason, btnPlusEpisode, btnMinusEpisode, 
				etSeason, etEpisode, etTitle, tvReleased, tvPlot, tvRating, tvVotes, 
				tvWriter, tvActors, tvGenres, tvRuntime, tvYear, tvLastUpdate, tvEpisodeName,tvLastSeasonEpisode, 
				llDetails, ivPoster, cbAutomaticChange);
		
		pd = new ProgressDialog(this);
	}
	
	
	public void saveChanges(View v){
		serie.setName(etTitle.getText().toString());
		if(serie.getTitle() == null || serie.getTitle().equals("") || etSeason.getText().toString().equals("") || etEpisode.getText().toString().equals("")){
			Builder alert = new Builder(context);
			alert.setMessage(context.getString(R.string.insert_data));
			alert.setNeutralButton(context.getString(R.string.ok), null);
			alert.show();
		}else{
			serie.setSeason(Integer.valueOf(etSeason.getText().toString()));
			serie.setEpisode(Integer.valueOf(etEpisode.getText().toString()));
			serie.setAutomaticChange(cbAutomaticChange.isChecked());
			int res = 0;
			if(serie.getId() != 0){
				res = RESULT_UPDATE;
			}else{
				res = RESULT_ADD;
			}
			
			Intent i = new Intent();
			i.putExtra("serie", serie);
			setResult(res, i);
			finish();
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
					File image = new File(serie.getDefaultImageFileString());
					image.delete();
					persistence.excludeSerieBd(serie.getId());
					Intent i = new Intent();
					i.putExtra("serie", serie);
					setResult(RESULT_DELETE, i);
					finish();
				}
			});
			alert.setNegativeButton(context.getString(R.string.no), null);
			alert.show();
		}else{
			finish();
		}
	
	}
	
	public void searchSerie(View v){
		if (Utils.isNetworkAvailable(this)) {
			if(serie.getTitle() == null){
				serie.setTitle( etTitle.getText().toString());
			}else if(etTitle.getText() != null && !etTitle.getText().equals("")){

				serie.setTitle( etTitle.getText().toString());
			}
			try {
				if(!serie.getTitle().equals(etTitle.getText().toString()) || serie.getLastUpdate() == null){
					confirmSeriesTitle();
				}else{
					openDialog();
					new FindSeriesDetailsAsyncTask().execute(null);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(pd.isShowing()){
					pd.dismiss();
				}
			}
		} else {

			Toast.makeText(this, this.getString(R.string.network_problem),
					Toast.LENGTH_LONG).show();
		}
	}

	public InputStream getJSONData(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		URI uri;
		InputStream data = null;
		try {
			uri = new URI(url);
			HttpGet method = new HttpGet(uri);
			HttpResponse response = httpClient.execute(method);
			data = response.getEntity().getContent();
		} catch (Exception e) {
			Toast.makeText(this, this.getString(R.string.network_problem),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		return data;
	}


	public void runServiceRequest() {
		try {
			ServicesParser.parseURLimbdAPI(serie);
			
//			parseURLDeanClat();
			
			ServicesParser.parseURLPoromenos(serie);
			
			

		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}

	private void confirmSeriesTitle() throws MalformedURLException,
			JSONException {
		final ArrayList<String> matches = ServicesParser.findMatchesFromPoromenos(serie, this);
		if(matches.isEmpty()){
			openDialog();
			new FindSeriesDetailsAsyncTask().execute(null);
		}else{
			Builder alert = new Builder(context);
			alert.setMessage(context.getString(R.string.did_you_mean));
			Spinner spinner = new Spinner(this);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, matches);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spinner.setAdapter(adapter);
		    spinner.setSelection(0);
		    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long id) {

					spinnerChoice = matches.get(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					
					spinnerChoice = matches.get(0);
				}
			});
							
			alert.setView(spinner);
			alert.setPositiveButton(this.getString(R.string.ok), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String title = spinnerChoice.substring(0, spinnerChoice.length()-6);
					String year = spinnerChoice.substring(spinnerChoice.length()-5, spinnerChoice.length()-1);
					serie.setTitle(title);
					serie.setYear(year);
					openDialog();
					new FindSeriesDetailsAsyncTask().execute(null);
					
				}
			});
			alert.show();
		}
	}

	
	
	private class FindSeriesDetailsAsyncTask extends AsyncTask<Serie, Void, Serie>{
		
		
		
		@Override
		protected Serie doInBackground(Serie... series) {
			
			runServiceRequest();
			return serie;
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
			if(pd.isShowing()){
				pd.dismiss();
			}
		}
		
		@Override
		protected void onPostExecute(Serie result) {
			super.onPostExecute(result);

			Handler h = new Handler();
			h.post(new Runnable() {
				
				@Override
				public void run() {
					if(serie.getPlot() == null){
						Toast.makeText(context, context.getString(R.string.network_problem),Toast.LENGTH_LONG).show();
					}else{
						mediator.updateView();
					}
				}
			});
			if(pd.isShowing()){
				pd.dismiss();
			}
		}
	}
	public void share(View v){
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		String episode = getResources().getString(R.string.episode);
		String season = getResources().getString(R.string.season);
		String share = getResources().getString(R.string.share);
		String share1 = getResources().getString(R.string.share1);
		String share2 = getResources().getString(R.string.share2);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, share1+" "+ serie.getTitle() +" "+ season+": "+serie.getSeason()+" "+ episode +": "+serie.getEpisode() + " "+ share2 +" " + URL_SM);
		startActivity(Intent.createChooser(sharingIntent, share));
	}
	
	private void openDialog(){
		pd.setMessage(context.getString(R.string.data_update));
		pd.show();
	}
}
