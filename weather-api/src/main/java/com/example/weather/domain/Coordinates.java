package com.example.weather.domain;

public class Coordinates {

	private String longitude;
	private String lat;

	@Override
	public String toString() {
		return "Coordinates [longitude=" + longitude + ", lat=" + lat + "]";
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

}
