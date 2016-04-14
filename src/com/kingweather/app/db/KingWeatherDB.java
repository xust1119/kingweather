package com.kingweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.kingweather.app.model.City;
import com.kingweather.app.model.County;
import com.kingweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class KingWeatherDB {
	public static final String DB_NAME = "king_weather";
	public static final int VERSION = 1;
	
	private static KingWeatherDB kingWeatherDB;
	private SQLiteDatabase db;
	
	private KingWeatherDB(Context context){
		KingWeatherOpenHelper dbHelper = new KingWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static KingWeatherDB getInstance(Context context){
		if(kingWeatherDB == null){
			kingWeatherDB = new KingWeatherDB(context);
		}
		return kingWeatherDB;
	}
	
	public void saveProvince(Province province){
		if(province != null){
			ContentValues value = new ContentValues();
			value.put("name", province.getName());
			value.put("code", province.getCode());
		}
	}
	
	public void saveCity(City city){
		if(city != null){
			ContentValues value = new ContentValues();
			value.put("name", city.getName());
			value.put("code", city.getCode());
			value.put("province_code", city.getProvinceCode());
		}
	}
	
	public void saveCounty(County county){
		if(county != null){
			ContentValues value = new ContentValues();
			value.put("name", county.getName());
			value.put("code", county.getCode());
			value.put("province_code", county.getCityCode());
		}
	}
	
	public Province querProvince(String name){
		Province province = null;
		Cursor cursor = db.query("Province", 
				null, 
				"name = ?", 
				new String[]{name}, 
				null, 
				null, 
				null);
		
		if(cursor.getCount() > 0){
			province = new Province(cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("name")),
					cursor.getString(cursor.getColumnIndex("code")));
		}
		
		cursor.close();
		
		return province;
	}
	
	public List<City> getCitysByProvince(String provinceCode){
		List<City> citys = new ArrayList<City>();
		Cursor cursor = db.query("city", 
				null, 
				"province_code=?", 
				new String[]{provinceCode}, 
				null, 
				null, 
				null);
		
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setCode(cursor.getString(cursor.getColumnIndex("code")));
				city.setName(cursor.getString(cursor.getColumnIndex("name")));
				city.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				
				citys.add(city);
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		
		return citys;
	}
	
	public List<County> getCountyByCityCode(String cityCode){
		List<County> counties = new ArrayList<County>();
		Cursor cursor = db.query("County", 
				null, 
				"city_code=?", 
				new String[]{cityCode}, 
				null, 
				null, 
				null);
		
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setCode(cursor.getString(cursor.getColumnIndex("code")));
				county.setName(cursor.getString(cursor.getColumnIndex("name")));
				county.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				
				counties.add(county);
			}while(cursor.moveToNext());
		}
		
		cursor.close();
		return counties;
	}
}
