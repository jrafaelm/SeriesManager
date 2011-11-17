package com.seriesmanager.business;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.seriesmanager.SeriesManagerActivity;

import android.graphics.Bitmap;
import android.provider.BaseColumns;

public class Serie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Constants for SQLite db manipulation.
	public static final String AUTHORITY = "com.seriesmanager.business.serie";
	public static String[] columns = new String[] {
		Series.PK_ID, 
		Series.NAME, 
		Series.SEASON, 
		Series.EPISODE,
		Series.IMDBURL,
		Series.RATING, 
		Series.POSTER, 	
		Series.PLOT,
		Series.GENRES, 
		Series.VOTES,
		Series.YEAR, 
		Series.TYPE, 
		Series.RELEASED, 
		Series.DIRECTOR, 
		Series.WRITER, 
		Series.ACTORS,
		Series.RUNTIME,
		Series.LAST_UPDATE
	};

	public static final class Series implements BaseColumns {

		public static final String DEFAULT_SORT_ORDER = "pk_id ASC";
		public static final String PK_ID = "pk_id";
		public static final String NAME = "name";
		public static final String SEASON = "season";
		public static final String EPISODE = "episode";
		public static final String IMDBURL = "imdburl";
		public static final String RATING = "rating";
		public static final String POSTER = "poster";
		public static final String PLOT = "plot";
		public static final String GENRES = "genres";
		public static final String VOTES = "votes";
		public static final String YEAR = "year";
		public static final String TYPE = "type";
		public static final String RELEASED = "released";
		public static final String DIRECTOR = "director";
		public static final String WRITER = "writer";
		public static final String ACTORS ="actors";
		public static final String RUNTIME = "runtime";
		public static final String LAST_UPDATE = "last_update";
		

		
	}
	
	private String title;
	private Integer season;
	private Integer episode;
	private String rating;
	private String imdburl;
	private String posterUrl;
	private String plot;
	private String genres;
	private String votes;
	private String year;
	private long type;
	private long id;
	private String released;
	private String director;
	private String writer;
	private String actors;
	private String runtime;
	private Date lastUpdate;
	private Bitmap thumb;
	private ArrayList<Episode> episodes;
	private HashMap<Integer, Integer> seasonEpisodes;
	
	public Serie(String title, int season, int episode) {
		super();
		this.title = title;
		this.season = season;
		this.episode = episode;
	}

	public Serie() {
		season = Integer.valueOf(0);
		episode = Integer.valueOf(0);
	}

	
	
	
	public Serie(String title, Integer season, Integer episode, String rating,
			String imdburl, String poster, String plot, String genres,
			String votes, String year, long type, long id, String released,
			String director, String writer, String actors, String runtime) {
		super();
		this.title = title;
		this.season = season;
		this.episode = episode;
		this.rating = rating;
		this.imdburl = imdburl;
		this.posterUrl = poster;
		this.plot = plot;
		this.genres = genres;
		this.votes = votes;
		this.year = year;
		this.type = type;
		this.id = id;
		this.released = released;
		this.director = director;
		this.writer = writer;
		this.actors = actors;
		this.runtime = runtime;
		this.seasonEpisodes = new HashMap<Integer, Integer>();
	}

	
	public HashMap<Integer, Integer> getSeasonEpisodes() {
		return seasonEpisodes;
	}

	public void setSeasonEpisodes(HashMap<Integer, Integer> seasonEpisodes) {
		this.seasonEpisodes = seasonEpisodes;
	}

	public Bitmap getThumb() {
		return thumb;
	}

	public void setThumb(Bitmap thumb) {
		this.thumb = thumb;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public ArrayList<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(ArrayList<Episode> episodes) {
		this.episodes = episodes;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getImdburl() {
		return imdburl;
	}

	public void setImdburl(String imdburl) {
		this.imdburl = imdburl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String poster) {
		this.posterUrl = poster;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getVotes() {
		return votes;
	}

	public void setVotes(String votes) {
		this.votes = votes;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public void setEpisode(Integer episode) {
		this.episode = episode;
	}

	public String getTitle() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public Integer getEpisode() {
		return episode;
	}

	public void setEpisode(int episode) {
		this.episode = episode;
	}
	
	public String getlastUpdateDB(){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (this.lastUpdate != null) {
				return df.format(this.lastUpdate);
			} else {
				return null;
			}
	}
	
	public String getlastUpdateFormated(){
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if (this.lastUpdate != null) {
			return df.format(this.lastUpdate);
		} else {
			return null;
		}
}

	public String getEpisodeName(Integer season, Integer episode) {
		if (getEpisodes() != null){
			for(Episode epi : getEpisodes()){
				if(epi.getSeason().equals(season) && epi.getNumber().equals(episode)){
					return epi.getName();
				}
			}
		}
		return "";
	}

	public String getDefaultImageFileString() {
		return SeriesManagerActivity.IMAGES_FOLDER + "/"
			+ this.getTitle().trim().replace(" ", "_")+".jpg";
	}

	public void setUpSeriesEpisodes() {
		this.setSeasonEpisodes(new HashMap<Integer, Integer>());
		for(Episode episode : this.getEpisodes()){
			if(this.getSeasonEpisodes().containsKey(episode.getSeason())){
				if(this.getSeasonEpisodes().get(episode.getSeason()) < episode.getNumber()){
					this.getSeasonEpisodes().put(episode.getSeason(), episode.getNumber());
				}
			}else{
				this.getSeasonEpisodes().put(episode.getSeason(), episode.getNumber());
			}
		}
	}
	
	
}
