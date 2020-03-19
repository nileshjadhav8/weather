package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.weather.domain.CityWeather;

public interface WeatherRepository extends MongoRepository<CityWeather, String>{
	

}
