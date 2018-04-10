package com.samplepoint.beans;

public class Emitter {
	private int id;
	private String emitterName;
	private float longitude;
	private float latitude;

	public Emitter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Emitter(String emitterName, float longitude, float latitude) {
		super();
		this.emitterName = emitterName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmitterName() {
		return emitterName;
	}

	public void setEmitterName(String emitterName) {
		this.emitterName = emitterName;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

}
