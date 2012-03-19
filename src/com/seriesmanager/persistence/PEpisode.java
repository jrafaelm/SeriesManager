package com.seriesmanager.persistence;

import java.util.ArrayList;

import com.seriesmanager.business.Episode;
import com.seriesmanager.business.Serie;
import com.seriesmanager.business.Episode.Episodes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class PEpisode extends PGeneric {

	private static final String TABLE_NAME = "tb_episode";
	
	public PEpisode(Context ctx) {
		super(ctx);
	}

	public Cursor getCursor() {
		try {
			super.open();
			return super.db.query(TABLE_NAME, Episode.columns, null, null, null,
					null, Episodes.DEFAULT_SORT_ORDER);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public ArrayList<Episode> listBySerie(Serie serie) {
		Cursor c = getCursor();
		ArrayList<Episode> episodes = new ArrayList<Episode>();
		if (c.moveToFirst()) {

			int idxName = c.getColumnIndex(Episodes.NAME);
			int idxId = c.getColumnIndex(Episodes.PK_ID);
			int idxSeason = c.getColumnIndex(Episodes.SEASON);
			int idxEpisode = c.getColumnIndex(Episodes.NUMBER);
			int idxIdSerie = c.getColumnIndex(Episodes.ID_SERIE);
			
			do {
				if(serie.getId() == c.getLong(idxIdSerie)){
					Episode episode = new Episode();
					episode.setName(c.getString(idxName));
					episode.setId(c.getLong(idxId));
					episode.setSeason(c.getInt(idxSeason));
					episode.setNumber(c.getInt(idxEpisode));				
					episodes.add(episode);
					if(serie.getSeasonEpisodes().containsKey(episode.getSeason())){
						if(serie.getSeasonEpisodes().get(episode.getSeason()) < episode.getNumber()){
							serie.getSeasonEpisodes().put(episode.getSeason(), episode.getNumber());
						}
					}else{
						serie.getSeasonEpisodes().put(episode.getSeason(), episode.getNumber());
					}
				}
			} while (c.moveToNext());
		}
		c.close();
		super.close();
		return episodes;
	}

	public ArrayList<Episode> listBySerieAndSeason(Serie serie) {
		Cursor c = getCursor();
		ArrayList<Episode> episodes = new ArrayList<Episode>();
		if (c.moveToFirst()) {

			int idxName = c.getColumnIndex(Episodes.NAME);
			int idxId = c.getColumnIndex(Episodes.PK_ID);
			int idxSeason = c.getColumnIndex(Episodes.SEASON);
			int idxEpisode = c.getColumnIndex(Episodes.NUMBER);
			int idxIdSerie = c.getColumnIndex(Episodes.ID_SERIE);
			
			do {
				if(serie.getId() == c.getLong(idxIdSerie) && serie.getSeason().intValue() ==  c.getInt(idxSeason)){
					Episode episode = new Episode();
					episode.setName(c.getString(idxName));
					episode.setId(c.getLong(idxId));
					episode.setSeason(c.getInt(idxSeason));
					episode.setNumber(c.getInt(idxEpisode));				
					episodes.add(episode);
				}
			} while (c.moveToNext());
		}
		c.close();
		super.close();
		return episodes;
	}
	
	public Episode searchEpisode(Serie serie) {
		Cursor c = getCursor();

		Episode episode = new Episode();
		if (c.moveToFirst()) {

			int idxName = c.getColumnIndex(Episodes.NAME);
			int idxId = c.getColumnIndex(Episodes.PK_ID);
			int idxSeason = c.getColumnIndex(Episodes.SEASON);
			int idxEpisode = c.getColumnIndex(Episodes.NUMBER);
			int idxIdSerie = c.getColumnIndex(Episodes.ID_SERIE);

			do {
				if(serie.getId() == c.getLong(idxIdSerie) && 
						serie.getSeason().intValue() ==  c.getInt(idxSeason) && 
						serie.getEpisode().intValue() ==  c.getInt(idxEpisode)){
					episode.setName(c.getString(idxName));
					episode.setId(c.getLong(idxId));
					episode.setSeason(c.getInt(idxSeason));
					episode.setNumber(c.getInt(idxEpisode));
					break;
				}
			} while (c.moveToNext());
		}
		c.close();
		super.close();
		return episode;
	}
	public Episode searchFirstEpisode(Serie serie) {
		Cursor c = getCursor();

		Episode episode = new Episode();
		if (c.moveToFirst()) {

			int idxName = c.getColumnIndex(Episodes.NAME);
			int idxId = c.getColumnIndex(Episodes.PK_ID);
			int idxSeason = c.getColumnIndex(Episodes.SEASON);
			int idxEpisode = c.getColumnIndex(Episodes.NUMBER);
			int idxIdSerie = c.getColumnIndex(Episodes.ID_SERIE);

			do {
				if(serie.getId() == c.getLong(idxIdSerie) && 
						1 ==  c.getInt(idxSeason) && 
						1 ==  c.getInt(idxEpisode)){
					episode.setName(c.getString(idxName));
					episode.setId(c.getLong(idxId));
					episode.setSeason(c.getInt(idxSeason));
					episode.setNumber(c.getInt(idxEpisode));
					break;
				}
			} while (c.moveToNext());
		}
		c.close();
		super.close();
		return episode;
	}
	
	public void excludeEpisodeBd(long pk_id) {
		String where = "pk_id = " + pk_id;
		
		try {
				super.open();
				db.delete(TABLE_NAME, where, null);
			} catch (SQLException e) {
				Log.e("SeriesManager",
						"Error trying to exclude Episode: " + e.toString());
			}finally {
				super.close();
			}
		
	}

	public void excludeEpisodesBd(long serieId) {
		String where = "id_serie = " + serieId;
		
		try {
				super.open();
				db.delete(TABLE_NAME, where, null);
			} catch (SQLException e) {
				Log.e("SeriesManager",
						"Error trying to exclude Episode: " + e.toString());
			}finally {
				super.close();
			}
		
	}
	
	public void addEpisode(Episode episode,Serie serie) {
		try {
			super.open();
			ContentValues values = new ContentValues();
			values.put("id_serie", serie.getId());
			values.put("name", episode.getName());
			values.put("season", episode.getSeason());
			values.put("number", episode.getNumber());
			db.insert(TABLE_NAME, null, values);
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to include Episode:  "
					+ e.toString());
		} finally {
			super.close();
		}
	}
	
	public void addEpisodes(Serie serie) {
		try {
			super.open();
			if(serie.getEpisodes() != null){
				for(Episode episode : serie.getEpisodes()){
					ContentValues values = new ContentValues();
	
					values.put("id_serie", serie.getId());
					values.put("name", episode.getName());
					values.put("season", episode.getSeason());
					values.put("number", episode.getNumber());
					db.insert(TABLE_NAME, null, values);
				}
			}
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to include Episode:  "
					+ e.toString());
		} finally {
			super.close();
		}
	}
	
	public void updateEpisode(Episode episode,Serie serie) {
		try {
			String where = "pk_id = " + episode.getId();
			super.open();
			
			ContentValues values = new ContentValues();

			values.put("id_serie", serie.getId());
			values.put("name", episode.getName());
			values.put("season", episode.getSeason());
			values.put("number", episode.getNumber());
			
			db.update(TABLE_NAME, values, where, null);
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to update Episode:  "
					+ e.toString());
		} finally {
			super.close();
		}
	}
	public void updateEpisodes( Serie serie) {
		try {
			super.open();
			for(Episode episode : serie.getEpisodes()){

				String where = "pk_id = " + episode.getId();
				ContentValues values = new ContentValues();

				values.put("id_serie", serie.getId());
				values.put("name", episode.getName());
				values.put("season", episode.getSeason());
				values.put("number", episode.getNumber());
				if(episode.getId() == 0){
					db.insert(TABLE_NAME, null, values);
				}else{
					db.update(TABLE_NAME, values, where, null);
				}
			}
		} catch (SQLException e) {
			Log.e("SeriesManager", "Error trying to update Episodes:  "
					+ e.toString());
		} finally {
			super.close();
		}
	}
}
