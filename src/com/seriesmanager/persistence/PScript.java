package com.seriesmanager.persistence;

import android.content.Context;


/**Classe responsible for the DB scripts*/
public class PScript extends PGeneric{
	private static final String[] SCRIPT_DATABASE_DELETE = {
		"DROP TABLE IF EXISTS tb_serie" };

	private static final String[] SCRIPT_TABLES = {
		

		"CREATE TABLE [tb_serie] ("
				+ "[pk_id] INTEGER NOT NULL PRIMARY KEY,"
				+ "[season] INTEGER," 
				+ "[episode] INTEGER," 
				+ "[name] VARCHAR(40));"
	};

private static final String DB_NAME = "SeriesManager.db";
private static final int DB_VERSION = 1;

private SQLiteHelper dbHelper;

public PScript(Context ctx) {
	super(ctx);
	// TODO Auto-generated constructor stub
	dbHelper = new SQLiteHelper(ctx, DB_NAME, DB_VERSION,
			SCRIPT_TABLES, SCRIPT_DATABASE_DELETE);
	db = dbHelper.getWritableDatabase();
}

@Override
public void close() {
	if (dbHelper != null) {
		dbHelper.close();
	}
}
}
