package com.seriesmanager.business;

import java.io.Serializable;

import android.provider.BaseColumns;

public class Episode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Constants for SQLite db manipulation.
	public static final String AUTHORITY = "com.seriesmanager.business.episode";
	public static String[] columns = new String[] {Episodes.PK_ID, Episodes.NAME, Episodes.SEASON, Episodes.NUMBER, Episodes.ID_SERIE};

	public static final class Episodes implements BaseColumns {

		public static final String DEFAULT_SORT_ORDER = "pk_id ASC";
		public static final String PK_ID = "pk_id";
		public static final String ID_SERIE = "id_serie";
		public static final String NAME = "name";
		public static final String SEASON = "season";
		public static final String NUMBER = "number";

	}
	
	private Integer season;
	private Integer number;
	private String name;
	private long idSerie;
	private long id;
	
	
	public Episode(Integer season, Integer number, String name, long idSerie) {
		super();
		this.season = season;
		this.number = number;
		this.name = name;
		this.idSerie = idSerie;
	}
	public Episode() {
		// TODO Auto-generated constructor stub
	}
	
	public Episode(Integer season, Integer number, String name) {
		super();
		this.season = season;
		this.number = number;
		this.name = name;
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getIdSerie() {
		return idSerie;
	}
	public void setIdSerie(long idSerie) {
		this.idSerie = idSerie;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
