package com.kingweather.app.model;

public class WeatherInfo {
	private String cityName;
	private String temp1;
	private String temp2;
	private String description;
	private String code;
	private String date;
	
	public WeatherInfo(String name, String temp1, String temp2, String description, String code, String date) {
		cityName = name;
		this.temp1 = temp1;
		this.temp2 = temp2;
		this.description = description;
		this.code = code;
		this.date = date;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
