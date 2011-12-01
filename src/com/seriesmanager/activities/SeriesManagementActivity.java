package com.seriesmanager.activities;

import java.io.InputStream;
import java.io.ObjectOutputStream.PutField;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.seriesmanager.R;
import com.seriesmanager.business.Episode;
import com.seriesmanager.business.Serie;
import com.seriesmanager.connectivity.MoviesJSON;
import com.seriesmanager.connectivity.Utils;
import com.seriesmanager.persistence.PEpisode;
import com.seriesmanager.persistence.PSerie;
import com.seriesmanager.views.mediator.SeriesDetailMediator;

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
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SeriesManagementActivity extends Activity {

	private static int RESULT_ADD = 3;
	private static int RESULT_UPDATE = 4;
	private static String URL_SM = "http://goo.gl/iyBGA";
	private static String URL_POROMENOS = "http://imdbapi.poromenos.org/js/?name=";
	private static String URL_DEANCLAT = "http://www.deanclatworthy.com/imdb/?q=";
	private static String URL_IMDBAPI = "http://www.imdbapi.com/?t=";
	
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
	TextView tvImdb;
	TextView tvRating;
	TextView tvVotes;
	TextView tvWriter;
	TextView tvActors;
	TextView tvGenres;
	TextView tvRuntime;
	TextView tvYear;
	TextView tvLastUpdate;
	TextView tvEpisodeName;
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
		tvImdb =(TextView) findViewById(R.id.tvImdbUrl);
		tvRating =(TextView) findViewById(R.id.tvRating);
		tvVotes =(TextView) findViewById(R.id.tvVotes);
		tvWriter =(TextView) findViewById(R.id.tvWriter);
		tvActors =(TextView) findViewById(R.id.tvActors);
		tvGenres =(TextView) findViewById(R.id.tvGenres);
		tvRuntime =(TextView) findViewById(R.id.tvRuntime);
		tvYear =(TextView) findViewById(R.id.tvYear);
		tvLastUpdate =(TextView) findViewById(R.id.tvLastUpdate);
		tvEpisodeName =(TextView) findViewById(R.id.tvEpisodeName);
		llDetails =(LinearLayout) findViewById(R.id.llDetails);
		ivPoster =(ImageView) findViewById(R.id.ivPoster);
		mediator = new SeriesDetailMediator(serie, btnPlusSeason, btnMinusSeason, btnPlusEpisode, btnMinusEpisode, etSeason, etEpisode, etTitle, tvReleased, tvPlot, tvImdb, tvRating, tvVotes, tvWriter, tvActors, tvGenres, tvRuntime, tvYear, tvLastUpdate, tvEpisodeName, llDetails, ivPoster);
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
			PSerie persistence = new PSerie(context);
			int res = 0;
			PEpisode persistenceEpi = new PEpisode(context);
			if(serie.getId() != 0){
				res = RESULT_UPDATE;
				persistence.updateSerie(serie);
			}else{
				res = RESULT_ADD;
				serie.setId(persistence.addSerie(serie));
			}
			if(persistenceEpi.searchFirstEpisode(serie).getId()!=0){
				persistenceEpi.updateEpisodes(serie);
			}else{
				persistenceEpi.addEpisodes(serie);
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
	
	public void searchSerie(View v){
		if (Utils.isNetworkAvailable(this)) {
			if(serie.getTitle() == null){
				serie.setTitle( etTitle.getText().toString());
			}else if(etTitle.getText() != null && !etTitle.getText().equals("")){

				serie.setTitle( etTitle.getText().toString());
			}
			try {
				if(!serie.getTitle().equals(etTitle.getText().toString()) || serie.getLastUpdate() == null){
					confirmSerieTitle();
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


	public void runJSONParser() {
		try {
			parseURLimbdAPI();
			
			parseURLDeanClat();
			
			parseURLPoromenos();
			
			

		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
	}

	private void confirmSerieTitle() throws MalformedURLException,
			JSONException {
		final ArrayList<String> matches = findMatchesFromPoromenos();
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
					String year = spinnerChoice.substring(spinnerChoice.length()-6, spinnerChoice.length()-1);
					serie.setTitle(title);
					serie.setYear(year);
					//TODO Continuar daqui.
					openDialog();
					new FindSeriesDetailsAsyncTask().execute(null);
					
				}
			});
			alert.show();
		}
	}

	private void parseURLPoromenos() throws Exception {

		String seriesName = serie.getTitle().trim().replace(" ", "%20");
		URL url = new URL(URL_POROMENOS + seriesName);
		String json = MoviesJSON.getJSONdata(url);
		if(json != null){
			JSONObject obj = new JSONObject(json);
			ArrayList<Episode> episodes = new ArrayList<Episode>();
			if(!obj.has("error")){
				
				JSONArray episodesJSON = obj.getJSONObject(serie.getTitle()).getJSONArray("episodes");
				if(episodes != null){
					for(int i=0; i < episodesJSON.length(); i++){
						JSONObject episodeJSON = episodesJSON.getJSONObject(i);
						String name = episodeJSON.getString("name");
						Integer season = episodeJSON.getInt("season");
						Integer number = episodeJSON.getInt("number");
						episodes.add(new Episode(season, number, name));
					}
					serie.setEpisodes(episodes);
				}
			}else{
				throw new Exception("Problem occured parsing json");
			}
		}
	}

	private ArrayList<String> findMatchesFromPoromenos() throws MalformedURLException{
		String seriesName = serie.getTitle().trim().replace(" ", "%20");
		URL url = new URL(URL_POROMENOS + "%25"+seriesName+"%25");
		String json = MoviesJSON.getJSONdata(url);

		ArrayList<String> shows = new ArrayList<String>();
		try{
		JSONObject obj = new JSONObject(json);
		if(!obj.has("error")){
			JSONArray searchMatches = obj.getJSONArray("shows");
			if(searchMatches != null){
				for(int i=0; i < searchMatches.length(); i++){
					JSONObject show = searchMatches.getJSONObject(i);
					shows.add(show.getString("name")+"("+show.getString("year")+")");
				}
				
			}
		}else{
			Toast.makeText(this, obj.getString("error"),
					Toast.LENGTH_LONG).show();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return shows;
	}

	private void parseURLDeanClat() throws Exception {
		URL url = new URL(URL_DEANCLAT + serie.getTitle());
		String json = MoviesJSON.getJSONdata(url);
		if(json != null){
			JSONObject obj = new JSONObject(json);
			if(!obj.has("error")){
				serie.setImdburl(obj.getString("imdburl"));
			}else{
	
				throw new Exception("Problem occured while parsing json");
			}
		}
	}

	private void parseURLimbdAPI() throws Exception {

		String seriesName = serie.getTitle().trim().replace(" ", "%20");
		URL url = new URL(URL_IMDBAPI+ "%25"+seriesName+"%25");
		
		String json = MoviesJSON.getJSONdata(url);
		if(json != null){
			JSONObject obj = new JSONObject(json);
			
			if(!obj.has("error")){
				seriesName = obj.getString("Title");
				if(seriesName.equalsIgnoreCase(serie.getTitle())){
					serie.setTitle(seriesName);
				}else{
					throw new Exception("Matching not found");
				}
				serie.setRating(obj.getString("Rating"));
				serie.setGenres(obj.getString("Genre"));
				serie.setVotes(obj.getString("Votes"));
				serie.setPosterUrl(obj.getString("Poster"));
				if(serie.getPosterUrl()!=null){
					Utils.downloadImage(serie);
				}
				serie.setYear(obj.getString("Year"));
				serie.setPlot(obj.getString("Plot"));
				serie.setReleased(obj.getString("Released"));
				serie.setDirector(obj.getString("Director"));
				serie.setWriter(obj.getString("Writer"));
				serie.setActors(obj.getString("Actors"));
				serie.setRuntime(obj.getString("Runtime"));
				serie.setLastUpdate(new Date());
				
			}else{
				throw new Exception("Problem occured while parsing json");
			}
		}
	}
	
	private class FindSeriesDetailsAsyncTask extends AsyncTask<Serie, Void, Serie>{
		
		
		
		@Override
		protected Serie doInBackground(Serie... series) {
			
			runJSONParser();
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
