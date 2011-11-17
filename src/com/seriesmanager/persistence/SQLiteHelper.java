package com.seriesmanager.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**Helper to access DB*/
public class SQLiteHelper extends SQLiteOpenHelper {
	private static final String CATEGORY = "SeriesManager";
	private String[] scriptSqlCreate;
	private String[] scriptSqlDelete;
	private String[] scriptSqlUpdate1;

		public SQLiteHelper(Context context, String dbName, int dbVersion,
				String[] scriptSQLCreate, String[] scriptSqlDelete, String[] scriptSqlUpdate1) {
			super(context, dbName, null, dbVersion);
			// TODO Auto-generated constructor stub
			this.scriptSqlCreate = scriptSQLCreate;
			this.scriptSqlDelete = scriptSqlDelete;
			this.scriptSqlUpdate1 = scriptSqlUpdate1;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			Log.i(CATEGORY, "database Created");
			int qtdScripts = scriptSqlCreate.length;
			for (int i = 0; i < qtdScripts; i++) {
				String sql = scriptSqlCreate[i];
				db.execSQL(sql);
				this.onUpgrade(db, 1, 0);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			switch (oldVersion) {
			case 1:
				int qtdScripts = scriptSqlUpdate1.length;
				for (int i = 0; i < qtdScripts; i++) {
					String sql = scriptSqlUpdate1[i];
					db.execSQL(sql);
				}

			default:
				break;
			}
		}}
