package com.example.weather.exports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

public class Headers {

	private static final List<String> HEADERS = new ArrayList<>();

	public Headers() {
		createHeaders();
	}

	public void createHeaders() {
		HEADERS.add("COUNTRY");
		HEADERS.add("CITY");
		HEADERS.add("LATITUDE");
		HEADERS.add("LONGITUDE");
		HEADERS.add("DESCRIPTION");
		HEADERS.add("ICON");
		HEADERS.add("TEMPERATURE");
		HEADERS.add("DEW POINT");
		HEADERS.add("PRESSURE");
		HEADERS.add("HUMIDITY");
		HEADERS.add("WIND GUST");
		HEADERS.add("CLOUD COVER");
		HEADERS.add("VISIBILITY");
		HEADERS.add("WIND SPEED");
	}

	public List<String> getHeaders() {
		createHeaders();
		return HEADERS;
	}

}
