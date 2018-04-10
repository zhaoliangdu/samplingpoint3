package com.samplepoint.beans;

public class CDRData {
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
	private float mer;
	private float ldpc;
	private float ber;
	private float transforLat;
	private float transforLng;
	private float distance;
	private float angle;
	private int eid;

	public CDRData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CDRData [id=" + id + ", time=" + time + ", area=" + area + ", longitude=" + longitude + ", latitude="
				+ latitude + ", height=" + height + ", speed=" + speed + ", testModeId=" + testModeId + ", frequency="
				+ frequency + ", fieldStrength=" + fieldStrength + ", snr=" + snr + ", mer=" + mer + ", ldpc=" + ldpc
				+ ", ber=" + ber + ", transforLat=" + transforLat + ", transforLng=" + transforLng + ", distance="
				+ distance + ", angle=" + angle + ", eid=" + eid + "]";
	}

	public CDRData(int id, String time, String area, float longitude, float latitude, float height, float speed,
			int testModeId, float frequency, float fieldStrength, float snr, float mer, float ldpc, float ber,
			float transforLat, float transforLng, float distance, float angle, int eid) {
		super();
		this.id = id;
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
		this.mer = mer;
		this.ldpc = ldpc;
		this.ber = ber;
		this.transforLat = transforLat;
		this.transforLng = transforLng;
		this.distance = distance;
		this.angle = angle;
		this.eid = eid;
	}

	public CDRData(String time, String area, float longitude, float latitude, float height, float speed, int testModeId,
			float frequency, float fieldStrength, float snr, float mer, float ldpc, float ber, float distance,
			float angle, int eid) {
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
		this.mer = mer;
		this.ldpc = ldpc;
		this.ber = ber;
		this.distance = distance;
		this.angle = angle;
		this.eid = eid;
	}

	public CDRData(String time, String area, float longitude, float latitude, float height, float speed, int testModeId,
			float frequency, float fieldStrength, float snr, float mer, float ldpc, float ber, float transforLat,
			float transforLng, float distance, float angle, int eid) {
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
		this.mer = mer;
		this.ldpc = ldpc;
		this.ber = ber;
		this.transforLat = transforLat;
		this.transforLng = transforLng;
		this.distance = distance;
		this.angle = angle;
		this.eid = eid;
	}

	public float getSnr() {
		return snr;
	}

	public void setSnr(float snr) {
		this.snr = snr;
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

	public float getMer() {
		return mer;
	}

	public void setMer(float mer) {
		this.mer = mer;
	}

	public float getLdpc() {
		return ldpc;
	}

	public void setLdpc(float ldpc) {
		this.ldpc = ldpc;
	}

	public float getBer() {
		return ber;
	}

	public void setBer(float ber) {
		this.ber = ber;
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
