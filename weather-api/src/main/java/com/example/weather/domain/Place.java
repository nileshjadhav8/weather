package com.example.weather.domain;

public class Place {

	private String country;
	private String city;

	@Override
	public String toString() {
		return "Place [country=" + country + ", city=" + city + "]";
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
