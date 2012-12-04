package com.seriesmanager.connectivity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.seriesmanager.business.Episode;
import com.seriesmanager.business.Serie;

public class ServicesParser {

	private static String URL_POROMENOS = "http://imdbapi.poromenos.org/js/?name=";
	private static String URL_DEANCLAT = "http://www.deanclatworthy.com/imdb/?q=";
	private static String URL_IMDBAPI = "http://www.omdbapi.com/?t=";
	
	public static void parseURLPoromenos(Serie serie) throws Exception {

		String seriesName = serie.getTitle().trim().replace(" ", "%20");
		String ulrString = URL_POROMENOS + seriesName;
		if(serie.getYear() != null && ! serie.getYear().equals(""));{
			ulrString = ulrString + "&year=" + serie.getYear();
		}
		URL url = new URL(ulrString);
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
					serie.setUpSeriesEpisodes();
				}
			}else{
				throw new Exception("Problem occured parsing json");
			}
		}
	}

	public static ArrayList<String> findMatchesFromPoromenos(Serie serie, Context ctx) throws MalformedURLException{
		
		ArrayList<String> shows = Mjolnir.findMatch(serie.getTitle().trim());
		if(shows.size() == 0){
			String seriesName = serie.getTitle().trim().replace(" ", "%20");
			URL url = new URL(URL_POROMENOS + "%25"+seriesName+"%25");
			String json = MoviesJSON.getJSONdata(url);
			try{
			JSONObject obj = new JSONObject(json);
			if(!obj.has("error")){
				if(obj.has("shows")){
					JSONArray searchMatches = obj.getJSONArray("shows");
					if(searchMatches != null){
						for(int i=0; i < searchMatches.length(); i++){
							JSONObject show = searchMatches.getJSONObject(i);
							shows.add(show.getString("name")+"("+show.getString("year")+")");
						}
					}
				}
			}else{
				Toast.makeText(ctx, obj.getString("error"),
						Toast.LENGTH_LONG).show();
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return shows;
	}

	@Deprecated
	public static void parseURLDeanClat(Serie serie) throws Exception {
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

	public static void parseURLimbdAPI(Serie serie) throws Exception {

		String seriesName = serie.getTitle().trim().replace(" ", "+");
		String urlString = null;
		urlString = URL_IMDBAPI + "%25"+seriesName+"%25";			
		
		if(serie.getYear() != null && !serie.getYear().equals("")){
			urlString = urlString + "&y="+serie.getYear();
		}
		URL url = new URL(urlString);
		
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
				serie.setRating(obj.getString("imdbRating"));
				serie.setGenres(obj.getString("Genre"));
				serie.setVotes(obj.getString("imdbVotes"));
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
	
}
