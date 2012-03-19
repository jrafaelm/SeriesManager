package com.seriesmanager.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.seriesmanager.business.Serie;
import com.seriesmanager.business.Serie.Series;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class PSerie extends PGeneric{
	private static final String TABLE_NAME = "tb_serie";
	PEpisode persistenceEpi;
	public PSerie(Context ctx) {
		super(ctx);
		this.persistenceEpi = new PEpisode(ctx);
	}

	public Cursor getCursor() {
		try {
			super.open();
			return super.db.query(TABLE_NAME, Serie.columns, null, null, null,
					null, Series.DEFAULT_SORT_ORDER);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public ArrayList<Serie> listAllSeries(Context context) {
		Cursor c = getCursor();
		ArrayList<Serie> series = new ArrayList<Serie>();
		if (c.moveToFirst()) {

			int idxName = c.getColumnIndex(Series.NAME);
			int idxId = c.getColumnIndex(Series.PK_ID);
			int idxSeason = c.getColumnIndex(Series.SEASON);
			int idxEpisode = c.getColumnIndex(Series.EPISODE);
			int idxImdbUrl = c.getColumnIndex(Series.IMDBURL);
			int idxRating =c.getColumnIndex(Series.RATING);
			int idxPoster =c.getColumnIndex(Series.POSTER); 	
			int idxPlot =c.getColumnIndex(Series.PLOT);
			int idxGenres =c.getColumnIndex(Series.GENRES); 
			int idxVotes =c.getColumnIndex(Series.VOTES);
			int idxYear =c.getColumnIndex(Series.YEAR); 
			int idxType =c.getColumnIndex(Series.TYPE);
			int idxReleased =c.getColumnIndex(Series.RELEASED); 
			int idxDirector =c.getColumnIndex(Series.DIRECTOR); 
			int idxWriter =c.getColumnIndex(Series.WRITER); 
			int idxActors =c.getColumnIndex(Series.ACTORS);
			int idxRuntime =c.getColumnIndex(Series.RUNTIME);
			int idxLastUpdate =c.getColumnIndex(Series.LAST_UPDATE);
			int idxAutomaticChange =c.getColumnIndex(Series.AUTOMATIC_CHANGE);
			
			do {
				
				Serie serie = new Serie();
				serie.setName(c.getString(idxName));
				serie.setId(c.getLong(idxId));
				serie.setSeason(c.getInt(idxSeason));
				serie.setEpisode(c.getInt(idxEpisode));
				serie.setImdburl(c.getString(idxImdbUrl));
				serie.setRating(c.getString(idxRating));
				serie.setPosterUrl(c.getString(idxPoster));
				serie.setPlot(c.getString(idxPlot));
				serie.setGenres(c.getString(idxGenres));
				serie.setVotes(c.getString(idxVotes));
				serie.setYear(c.getString(idxYear));
				serie.setType(c.getInt(idxType));
				serie.setReleased(c.getString(idxReleased));
				serie.setDirector(c.getString(idxDirector));
				serie.setWriter(c.getString(idxWriter));
				serie.setActors(c.getString(idxActors));
				serie.setRuntime(c.getString(idxRuntime));
				serie.setAutomaticChange(c.getInt(idxAutomaticChange) == 0? false : true);

				Bitmap image = BitmapFactory.decodeFile(serie.getDefaultImageFileString());
				if(image != null){
					image = Bitmap.createScaledBitmap(image, 100, 100, true);
					serie.setThumb(image);
				}
				try {

					String dtStr = c.getString(idxLastUpdate);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date dt = (Date) df.parse(dtStr);

					serie.setLastUpdate(dt);

				} catch (Exception e) {
				}
				serie.setSeasonEpisodes(new HashMap<Integer, Integer>());
				serie.setEpisodes(persistenceEpi.listBySerie(serie));
				if(!super.isOpen()){
					super.open();
				}
				series.add(serie);
			} while (c.moveToNext());
		}
		c.close();
		super.close();
		return series;
	}

		public Serie searchSerie(Context context, long idSerie) {
		Cursor c = getCursor();
		
		Serie serie = new Serie();
		if (c.moveToFirst()) {

			int idxName = c.getColumnIndex(Series.NAME);
			int idxId = c.getColumnIndex(Series.PK_ID);
			int idxSeason = c.getColumnIndex(Series.SEASON);
			int idxEpisode = c.getColumnIndex(Series.EPISODE);
			int idxImdbUrl = c.getColumnIndex(Series.IMDBURL);
			int idxRating =c.getColumnIndex(Series.RATING);
			int idxPoster =c.getColumnIndex(Series.POSTER); 	
			int idxPlot =c.getColumnIndex(Series.PLOT);
			int idxGenres =c.getColumnIndex(Series.GENRES); 
			int idxVotes =c.getColumnIndex(Series.VOTES);
			int idxYear =c.getColumnIndex(Series.YEAR); 
			int idxType =c.getColumnIndex(Series.TYPE);
			int idxReleased =c.getColumnIndex(Series.RELEASED); 
			int idxDirector =c.getColumnIndex(Series.DIRECTOR); 
			int idxWriter =c.getColumnIndex(Series.WRITER); 
			int idxActors =c.getColumnIndex(Series.ACTORS);
			int idxRuntime =c.getColumnIndex(Series.RUNTIME);
			int idxLastUpdate =c.getColumnIndex(Series.LAST_UPDATE);
			int idxAutomaticChange =c.getColumnIndex(Series.AUTOMATIC_CHANGE);
						
				
			do {
				if(c.getLong(idxId) == idSerie){
					
					serie.setName(c.getString(idxName));
					serie.setId(c.getLong(idxId));
					serie.setSeason(c.getInt(idxSeason));
					serie.setEpisode(c.getInt(idxEpisode));
					serie.setImdburl(c.getString(idxImdbUrl));
					serie.setRating(c.getString(idxRating));
					serie.setPosterUrl(c.getString(idxPoster));
					serie.setPlot(c.getString(idxPlot));
					serie.setGenres(c.getString(idxGenres));
					serie.setVotes(c.getString(idxVotes));
					serie.setYear(c.getString(idxYear));
					serie.setType(c.getInt(idxType));
					serie.setReleased(c.getString(idxReleased));
					serie.setDirector(c.getString(idxDirector));
					serie.setWriter(c.getString(idxWriter));
					serie.setActors(c.getString(idxActors));
					serie.setRuntime(c.getString(idxRuntime));
					serie.setAutomaticChange(c.getInt(idxAutomaticChange) == 0? false : true);
					
					try {

						String dtStr = c.getString(idxLastUpdate);
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date dt = (Date) df.parse(dtStr);

						serie.setLastUpdate(dt);

					} catch (Exception e) {
					}

					serie.setEpisodes(persistenceEpi.listBySerie(serie));
					break;
				}
			} while (c.moveToNext());
		}
		c.close();
		super.close();
		return serie;
	}
	
	public void excludeSerieBd(long pk_id) {
		String where = "pk_id = " + pk_id;
		
		try {
				super.open();
				db.delete(TABLE_NAME, where, null);
				persistenceEpi.excludeEpisodesBd(pk_id);
			} catch (SQLException e) {
				Log.e("SeriesManager",
						"Error trying to exclude Serie: " + e.toString());
			}finally {
				super.close();
			}
		
	}

	public long addSerie(Serie serie) {
		try {
			super.open();
			ContentValues values = new ContentValues();
			values.put(Series.NAME, serie.getTitle());
			values.put(Series.SEASON, serie.getSeason());
			values.put(Series.EPISODE, serie.getEpisode());
			values.put(Series.RATING,serie.getRating());
			values.put(Series.POSTER,serie.getPosterUrl());
			values.put(Series.PLOT,serie.getPlot());
			values.put(Series.GENRES,serie.getGenres());
			values.put(Series.VOTES,serie.getVotes());
			values.put(Series.YEAR,serie.getYear());
			values.put(Series.TYPE,serie.getType());
			values.put(Series.RELEASED,serie.getReleased());
			values.put(Series.DIRECTOR,serie.getDirector());
			values.put(Series.WRITER,serie.getWriter());
			values.put(Series.ACTORS,serie.getActors());
			values.put(Series.RUNTIME,serie.getRuntime());
			values.put(Series.LAST_UPDATE,serie.getlastUpdateDB());
			values.put(Series.AUTOMATIC_CHANGE,serie.hasAutomaticChange() ? 1 : 0);
			return db.insert(TABLE_NAME, null, values);
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to include Serie:  "
					+ e.toString());
		}finally{
			super.close();
		}

		return -1;
	}
	
	public void updateSerie(Serie serie) {
		try {
			String where = "pk_id = " + serie.getId();
			super.open();
			ContentValues values = new ContentValues();
			values.put(Series.NAME, serie.getTitle());
			values.put(Series.SEASON, serie.getSeason());
			values.put(Series.EPISODE, serie.getEpisode());
			values.put(Series.RATING,serie.getRating());
			values.put(Series.POSTER,serie.getPosterUrl());
			values.put(Series.PLOT,serie.getPlot());
			values.put(Series.GENRES,serie.getGenres());
			values.put(Series.VOTES,serie.getVotes());
			values.put(Series.YEAR,serie.getYear());
			values.put(Series.TYPE,serie.getType());
			values.put(Series.RELEASED,serie.getReleased());
			values.put(Series.DIRECTOR,serie.getDirector());
			values.put(Series.WRITER,serie.getWriter());
			values.put(Series.ACTORS,serie.getActors());
			values.put(Series.RUNTIME,serie.getRuntime());
			values.put(Series.LAST_UPDATE,serie.getlastUpdateDB());
			values.put(Series.AUTOMATIC_CHANGE,serie.hasAutomaticChange() ? 1 : 0);
			
			db.update(TABLE_NAME, values, where, null);
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to update Serie:  "
					+ e.toString());
		} finally {
			super.close();
		}
	}
}
