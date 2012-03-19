package com.seriesmanager.persistence;

import com.seriesmanager.business.Serie.Series;

import android.content.Context;


/**Classe responsible for the DB scripts*/
public class PScript extends PGeneric{
	private static final String[] SCRIPT_DATABASE_DELETE = {
		"DROP TABLE IF EXISTS tb_serie" };

	private static final String[] SCRIPT_CREATE_TABLES = {
		

		"CREATE TABLE [tb_serie] ("
				+ "[pk_id] INTEGER NOT NULL PRIMARY KEY,"
				+ "[season] INTEGER," 
				+ "[episode] INTEGER," 
				+ "[name] VARCHAR(40));"
	};
	
	private static final String[] SCRIPT_DATABASE_UPDATE1 = {
		

		"CREATE TABLE [tb_episode] ("
				+ "[pk_id] INTEGER NOT NULL PRIMARY KEY,"
				+ "[id_serie] INTEGER NOT NULL,"
				+ "[season] INTEGER," 
				+ "[number] INTEGER," 
				+ "[name] VARCHAR(60));",
				
		"ALTER TABLE [tb_serie] ADD COLUMN imdburl text;",
		"ALTER TABLE [tb_serie] ADD COLUMN rating text;",
		"ALTER TABLE [tb_serie] ADD COLUMN poster text;",
		"ALTER TABLE [tb_serie] ADD COLUMN plot text;",
		"ALTER TABLE [tb_serie] ADD COLUMN genres text;",
		"ALTER TABLE [tb_serie] ADD COLUMN votes text;",
		"ALTER TABLE [tb_serie] ADD COLUMN year integer;",
		"ALTER TABLE [tb_serie] ADD COLUMN type integer;",
		"ALTER TABLE [tb_serie] ADD COLUMN released text;",
		"ALTER TABLE [tb_serie] ADD COLUMN director text;",
		"ALTER TABLE [tb_serie] ADD COLUMN writer text;",
		"ALTER TABLE [tb_serie] ADD COLUMN actors text;",
		"ALTER TABLE [tb_serie] ADD COLUMN runtime text;",
		"ALTER TABLE [tb_serie] ADD COLUMN last_update text;"
	};
	
	private static final String[] SCRIPT_DATABASE_UPDATE2 = {
		

		
				
		"ALTER TABLE [tb_serie] ADD COLUMN automatic_change INTEGER DEFAULT 1;"
	};

private static final String DB_NAME = "SeriesManager.db";
private static final int DB_VERSION = 3;

private SQLiteHelper dbHelper;

public PScript(Context ctx) {
	super(ctx);
	// TODO Auto-generated constructor stub
	dbHelper = new SQLiteHelper(ctx, DB_NAME, DB_VERSION,
			SCRIPT_CREATE_TABLES, SCRIPT_DATABASE_DELETE , SCRIPT_DATABASE_UPDATE1,SCRIPT_DATABASE_UPDATE2);
	db = dbHelper.getWritableDatabase();
}

@Override
public void close() {
	if (dbHelper != null) {
		dbHelper.close();
	}
}
}
