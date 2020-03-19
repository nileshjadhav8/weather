package com.example.weather.domain;

public class Weather {

	private String description;
	private String icon;
	private String dewPoint;

	@Override
	public String toString() {
		return "Weather [icon=" + icon + ", description=" + description + ", dewPoint=" + dewPoint + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dewPoint
	 */
	public String getDewPoint() {
		return dewPoint;
	}

	/**
	 * @param dewPoint the dewPoint to set
	 */
	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
