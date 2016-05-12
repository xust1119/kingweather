package com.kingweather.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kingweather.app.model.LocationInfo;
import com.kingweather.app.model.WeatherInfo;


public class Utility {
	
	private static Map<String, String> handleLocactionId(String areaId){
		Map<String, String> codes = new HashMap<String, String>();
		codes.put("province", areaId.substring(0, 5));
		codes.put("city", areaId.substring(5, 7));
		codes.put("county", areaId.substring(7));
		return codes;
	}
	
	public static List<LocationInfo> handleLocationResponse(String response){
		List<LocationInfo> locations = new ArrayList<LocationInfo>();
		try {
			JSONObject json = new JSONObject(response);
			
			if(json.getString("errMsg").equals("success")){
				JSONArray locationInfos = json.getJSONArray("retData");
				int len = locationInfos.length();
				
				for(int index = 0; index < len; ++index){
					JSONObject location = locationInfos.getJSONObject(index);
					String provinceName = location.getString("province_cn");
					String districtName = location.getString("district_cn");
					String areaName = location.getString("name_cn");
					String areaId = location.getString("area_id");
					
					locations.add(new LocationInfo(provinceName, districtName, areaName, areaId));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			return locations;
		}
	}
	
	public static void handleWeatherJson(Context context, String repsonse){
		try {
			JSONObject json = new JSONObject(repsonse);
			
			if(json.getString("errMsg").equals("success")){
				JSONObject weatherJson = json.getJSONObject("retData");
				
				String description = weatherJson.getString("weather");
				String temp1 = weatherJson.getString("l_tmp");
				String temp2 = weatherJson.getString("h_tmp");
				String date = weatherJson.getString("date") + weatherJson.getString("time");
				String name = weatherJson.getString("city");
				String code = weatherJson.getString("citycode");
				saveWeatherInfo(context, name, description, temp1, temp2, date, code);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveWeatherInfo(Context context, 
										String locationName, 
										String description,
										String temp1, 
										String temp2,
										String date,
										String code){
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("isSelectedLocation", true);
		editor.putString("locationName", locationName);
		editor.putString("description", description);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("updataDate", date);
		editor.putString("code", code);
		
		editor.commit();
	}
	
	public static boolean isSelectedLocation(Context context){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getBoolean("isSelectedLocation", false);
	}
	
	public static WeatherInfo getWeatherInfo(Context context){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return new WeatherInfo(sp.getString("locationName", "null"), 
				sp.getString("temp1", "null"), 
				sp.getString("temp2", "null"), 
				sp.getString("description", "null"), 
				sp.getString("code", "null"), 
				sp.getString("updataDate", "null"));
	}
}


