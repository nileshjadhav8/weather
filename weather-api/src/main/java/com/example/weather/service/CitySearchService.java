package com.example.weather.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.weather.domain.CityWeather;
import com.example.weather.domain.Coordinates;
import com.example.weather.domain.MainInfo;
import com.example.weather.domain.Place;
import com.example.weather.domain.Weather;
import com.example.weather.domain.Wind;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class CitySearchService {

	private CityWeather cityWeather;

	private JSONObject jsonObject;

	private static final Logger logger = LoggerFactory.getLogger(CitySearchService.class);

	public CityWeather search(String city) {
		cityWeather = getCityInformation(city);
		return cityWeather;
	}

	private CityWeather getCityInformation(String city) {

		logger.info("CitySearchService::getCityInformation() start..");

		logger.info("city: " + city);

		cityWeather = new CityWeather();
		initializeCityWeather();
		Client client = Client.create();
		InputStream inputStream;
		JSONObject jsonobj;
		String opencageKey = "";
		String darkskyKey = "";
		String lat = "";
		String lng = "";
		String country = "";
		try {

			logger.info("Reading properties file");

			Properties prop = new Properties();

			String propFileName = "application.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			darkskyKey = prop.getProperty("darkskyKey");

			opencageKey = prop.getProperty("opencageKey");

			logger.info("opencageKey: " + opencageKey);

			logger.info("darkskyKey: " + darkskyKey);

			String latnlg = "https://api.opencagedata.com/geocode/v1/json?key=" + opencageKey + "&q=" + city
					+ "&pretty=1&no_annotations=1";

			WebResource webResource1 = client.resource(latnlg);

			ClientResponse response1 = webResource1.accept("application/json").get(ClientResponse.class);

			if (response1.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response1.getStatus());
			}

			String output1 = response1.getEntity(String.class);

			JSONObject ob = new JSONObject(output1);

			JSONArray arr = ob.getJSONArray("results");

			JSONObject o = arr.getJSONObject(0);

			jsonobj = (JSONObject) o.get("bounds");

			lat = jsonobj.getJSONObject("southwest").getString("lat");

			lng = jsonobj.getJSONObject("southwest").getString("lng");

			jsonobj = (JSONObject) o.get("components");

			country = jsonobj.getString("country");

			logger.info("Getting data from api.darksky.net");

			String endpoint1 = "https://api.darksky.net/forecast/" + darkskyKey + "/" + lat + "," + lng;

			WebResource webResource2 = client.resource(endpoint1);

			ClientResponse response2 = webResource2.accept("application/json").get(ClientResponse.class);

			if (response2.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
			}

			String output2 = response2.getEntity(String.class);

			jsonObject = new JSONObject(output2);

			logger.info("Response from api.darksky.net" + jsonObject);

			cityWeather.getPlace().setCountry(country);

			cityWeather.getPlace().setCity(city);

			cityWeather.getCoordinates().setLat(lat);

			cityWeather.getCoordinates().setLongitude(lng);

			cityWeather.getWeather().setDescription(jsonObject.getJSONObject("currently").getString("summary"));

			cityWeather.getWeather().setIcon(jsonObject.getJSONObject("currently").getString("icon").toString());

			cityWeather.getMainInfo()
					.setHumidity(Float.parseFloat(jsonObject.getJSONObject("currently").getString("humidity")));
			cityWeather.getMainInfo()
					.setPressure(Float.parseFloat(jsonObject.getJSONObject("currently").getString("pressure")));
			cityWeather.getMainInfo()
					.setTemp(Float.parseFloat(jsonObject.getJSONObject("currently").getString("temperature")));

			cityWeather.setVisibility(Float.parseFloat(jsonObject.getJSONObject("currently").getString("visibility")));

			cityWeather.getMainInfo().setWindGust(jsonObject.getJSONObject("currently").getString("windGust"));

			cityWeather.getMainInfo().setCloudCover(jsonObject.getJSONObject("currently").getString("cloudCover"));

			cityWeather.getWeather()
					.setDewPoint(jsonObject.getJSONObject("currently").getString("dewPoint").toString());

			cityWeather.getWind()
					.setSpeed(Float.parseFloat(jsonObject.getJSONObject("currently").getString("windSpeed")));

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityWeather;
	}

	private void initializeCityWeather() {
		cityWeather.setCoordinates(new Coordinates());
		cityWeather.setMainInfo(new MainInfo());
		cityWeather.setPlace(new Place());
		cityWeather.setWeather(new Weather());
		cityWeather.setWind(new Wind());
	}
}
