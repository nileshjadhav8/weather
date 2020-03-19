package com.example.weather.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.weather.domain.CityWeather;

@Service
public class WeatherService {
	@Autowired
	private CitySearchService citySearchService;

	public CityWeather searchByCity(String city) {
		CityWeather cityWeather = citySearchService.search(city);
		return cityWeather;
	}

}
