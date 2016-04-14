package com.kingweather.app.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class KingWeatherOpenHelper extends SQLiteOpenHelper {
	//b121661547925f82c7b63ba1bacca7bb

	public static final String CREATE_PROVINCE = "create table Province ("
			+ "id integer primary key autoincrement,"
			+ "name text,"
			+ "code text)";
	
	public static final String CREATE_CITY = "create table City("
			+ "id integer primary key autoincrement,"
			+ "name text,"
			+ "province_code text,"
			+ "code text)";
	
	public static final String CREATE_COUNTY = "create table County("
			+ "id integer primary key autoincrement,"
			+ "name text,"
			+ "city_code text,"
			+ "code text)";
			
	public KingWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_PROVINCE);
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_COUNTY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
