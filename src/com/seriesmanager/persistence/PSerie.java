package com.seriesmanager.persistence;

import java.util.ArrayList;

import com.seriesmanager.business.Serie;
import com.seriesmanager.business.Serie.Series;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class PSerie extends PGeneric{
	private static final String TABLE_NAME = "tb_serie";
	Cursor cursorSerie;
	public PSerie(Context ctx) {
		super(ctx);
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
			
			do {
				
				Serie serie = new Serie();
				
				
				serie.setName(c.getString(idxName));
				serie.setId(c.getLong(idxId));
				serie.setSeason(c.getInt(idxSeason));
				serie.setEpisode(c.getInt(idxEpisode));
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
			do {
				if(c.getLong(idxId) == idSerie){
					
					serie.setId(idSerie);
					serie.setName(c.getString(idxName));
					serie.setSeason(c.getInt(idxSeason));
					serie.setEpisode(c.getInt(idxEpisode));
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
			} catch (SQLException e) {
				Log.e("SeriesManager",
						"Error trying to exclude Serie: " + e.toString());
			}finally {
				super.close();
			}
		
	}

	public void addSerie(Serie serie) {
		try {
			super.open();
			ContentValues values = new ContentValues();
			values.put("name", serie.getName());
			values.put("season", serie.getSeason());
			values.put("episode", serie.getEpisode());
			db.insert(TABLE_NAME, null, values);
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to include Serie:  "
					+ e.toString());
		} finally {
			super.close();
		}
	}
	
	public void updateSerie(Serie serie) {
		try {
			String where = "pk_id = " + serie.getId();
			super.open();
			ContentValues values = new ContentValues();
			values.put("name", serie.getName());
			values.put("season", serie.getSeason());
			values.put("episode", serie.getEpisode());
			
			db.update(TABLE_NAME, values, where, null);
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to update Serie:  "
					+ e.toString());
		} finally {
			super.close();
		}
	}
}
