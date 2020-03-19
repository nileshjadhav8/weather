package com.example.weather.domain;

public class Wind {

	private float speed;

	@Override
	public String toString() {
		return "Wind [speed=" + speed + "]";
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
