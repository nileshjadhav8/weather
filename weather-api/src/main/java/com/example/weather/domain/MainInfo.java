package com.example.weather.domain;

public class MainInfo {

	private float temp;
	private float pressure;
	private float humidity;
	private String windGust;
	private String cloudCover;

	@Override
	public String toString() {
		return "MainInfo [temp=" + temp + ", pressure=" + pressure + ", humidity=" + humidity + ", windGust=" + windGust
				+ ", cloudCover=" + cloudCover + "]";
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float f) {
		this.humidity = f;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	/**
	 * @return the windGust
	 */
	public String getWindGust() {
		return windGust;
	}

	/**
	 * @param windGust the windGust to set
	 */
	public void setWindGust(String windGust) {
		this.windGust = windGust;
	}

	/**
	 * @return the cloudCover
	 */
	public String getCloudCover() {
		return cloudCover;
	}

	/**
	 * @param cloudCover the cloudCover to set
	 */
	public void setCloudCover(String cloudCover) {
		this.cloudCover = cloudCover;
	}

}
