package com.example.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.repository.WeatherRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses=WeatherRepository.class)
public class WeatherApiApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(WeatherApiApplication.class, args);
	}
}
