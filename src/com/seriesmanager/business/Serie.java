package com.seriesmanager.business;

import java.io.Serializable;

import android.provider.BaseColumns;

public class Serie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Constants for SQLite db manipulation.
	public static final String AUTHORITY = "com.seriesmanager.business.serie";
	public static String[] columns = new String[] {Series.PK_ID, Series.NAME, Series.SEASON, Series.EPISODE};

	public static final class Series implements BaseColumns {

		public static final String DEFAULT_SORT_ORDER = "pk_id ASC";
		public static final String PK_ID = "pk_id";
		public static final String NAME = "name";
		public static final String SEASON = "season";
		public static final String EPISODE = "episode";

	}
	
	private String name;
	private Integer season;
	private Integer episode;
	private long id;
	
	public Serie(String name, int season, int episode) {
		super();
		this.name = name;
		this.season = season;
		this.episode = episode;
	}

	public Serie() {
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
	
	
}
