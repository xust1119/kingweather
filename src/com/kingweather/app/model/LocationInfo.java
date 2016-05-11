package com.kingweather.app.model;

public class LocationInfo {
	private String provinceName;
	private String cityName;
	private String countyName;
	private String id;
	
	public LocationInfo(String province, String city, String county, String id) {
		// TODO Auto-generated constructor stub
		provinceName = province;
		cityName = city;
		countyName = county;
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s省 %s市 %s", provinceName, cityName, countyName);
	}

	public String getProvinceName() {
		return provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public String getId() {
		return id;
	}

}
