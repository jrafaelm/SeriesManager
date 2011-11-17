package com.seriesmanager.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**Generic class for DB usage*/
public class PGeneric {

	protected SQLiteDatabase db;
	protected static final String DB_NAME = "SeriesManager.db";
	
	protected Context ctx;
	
	
	public PGeneric(Context ctx) {
		this.ctx = ctx;
	}

	/**Método para abrir/criar BD*/
	public void open() {
		if (db == null) {
			db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,
					null);
		} else if (!db.isOpen()) {
			db = ctx.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,
					null);
		}
	}
	
	/**Método para fechar BD*/
	public void close() {
		if (db != null) {
			db.close();
		}
	}
	
	public boolean isOpen(){
		return db.isOpen();
	}
	
}
