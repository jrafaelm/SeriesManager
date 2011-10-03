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

	public SQLiteHelper(Context context, String nomeBanco, int versaoBanco,
			String[] scriptSQLCreate, String[] scriptSqlDelete) {
		super(context, nomeBanco, null, versaoBanco);
		// TODO Auto-generated constructor stub
		this.scriptSqlCreate = scriptSQLCreate;
		this.scriptSqlDelete = scriptSqlDelete;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(CATEGORY, "Banco Criado");
		int qtdScripts = scriptSqlCreate.length;
		for (int i = 0; i < qtdScripts; i++) {
			String sql = scriptSqlCreate[i];
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
		// TODO Auto-generated method stub
		int qtdScripts = scriptSqlDelete.length;
		for (int i = 0; i < qtdScripts; i++) {
			String sql = scriptSqlDelete[i];
			db.execSQL(sql);
		}
		onCreate(db);
	}
}
