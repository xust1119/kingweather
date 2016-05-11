package com.kingweather.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import com.kingweather.app.model.LocationInfo;


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
}

