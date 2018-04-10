package com.samplepoint.beans;

public class AnalogData {
	private int id;
	private String time;
	private String area;
	private float longitude;
	private float latitude;
	private float height;
	private float speed;
	private int testModeId;
	private float frequency;
	private float fieldStrength;
	private float snr;
	private float transforLat;
	private float transforLng;
	private float distance;
	private float angle;
	private int eid;
	public AnalogData() {
		super(); 
	}
	
	public AnalogData(String time, String area, float longitude, float latitude, float height, float speed,
			int testModeId, float frequency, float fieldStrength, float snr, float transforLat, float transforLng,
			float distance, float angle, int eid) {
		super();
		this.time = time;
		this.area = area;
		this.longitude = longitude;
		this.latitude = latitude;
		this.height = height;
		this.speed = speed;
		this.testModeId = testModeId;
		this.frequency = frequency;
		this.fieldStrength = fieldStrength;
		this.snr = snr;
		this.transforLat = transforLat;
		this.transforLng = transforLng;
		this.distance = distance;
		this.angle = angle;
		this.eid = eid;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
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
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getTestModeId() {
		return testModeId;
	}
	public void setTestModeId(int testModeId) {
		this.testModeId = testModeId;
	}
	public float getFrequency() {
		return frequency;
	}
	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}
	public float getFieldStrength() {
		return fieldStrength;
	}
	public void setFieldStrength(float fieldStrength) {
		this.fieldStrength = fieldStrength;
	}
	public float getSnr() {
		return snr;
	}
	public void setSnr(float snr) {
		this.snr = snr;
	}
	public float getTransforLat() {
		return transforLat;
	}
	public void setTransforLat(float transforLat) {
		this.transforLat = transforLat;
	}
	public float getTransforLng() {
		return transforLng;
	}
	public void setTransforLng(float transforLng) {
		this.transforLng = transforLng;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	
}
