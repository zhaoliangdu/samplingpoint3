/**
 * 数字电视
 */
package com.samplepoint.beans;

import java.io.Serializable;

/**
 * 
 * @author ZhaoLiangdu
 *
 */
public class DigitalData implements Serializable{
	 
	private static final long serialVersionUID = -8326326049380446699L;
	private int id; 
	private String time; 
	private String area; 
	private float latitude; 
	private float longitude; 
	private float height; 
	private float speed; 
	private int testModeId; 
	private int frequency; 
	private float wideBand; 
	private float fieldStrength; 
	private float snr; 
	private float mer; 
	private float ldpc; 
	private float distance; 
	private float angle; 
	private float transforLat;
	private float transforLng;
	private int eid;

	@Override
	public String toString() {
		return "TestData [id=" + id + ", time=" + time + ", area=" + area + ", latitude=" + latitude + ", longitude="
				+ longitude + ", height=" + height + ", speed=" + speed + ", testModeId=" + testModeId + ", frequency="
				+ frequency + ", wideBand=" + wideBand + ", fieldStrength=" + fieldStrength + ", snr=" + snr + ", mer="
				+ mer + ", ldpc=" + ldpc + ", distance=" + distance + ", angle=" + angle + ", transforLat="
				+ transforLat + ", transforLng=" + transforLng + ", eid=" + eid + "]";
	}

	public DigitalData(String time, String area, float latitude, float longitude, float height, float speed,
			int testModeId, int frequency, float wideBand, float fieldStrength, float snr, float mer, float ldpc,
			float distance, float angle, float transforLat, float transforLng, int eid) {
		super();
		this.time = time;
		this.area = area;
		this.latitude = latitude;
		this.longitude = longitude;
		this.height = height;
		this.speed = speed;
		this.testModeId = testModeId;
		this.frequency = frequency;
		this.wideBand = wideBand;
		this.fieldStrength = fieldStrength;
		this.snr = snr;
		this.mer = mer;
		this.ldpc = ldpc;
		this.distance = distance;
		this.angle = angle;
		this.transforLat = transforLat;
		this.transforLng = transforLng;
		this.eid = eid;
	}

	public DigitalData() {
		super();
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

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
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

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public float getWideBand() {
		return wideBand;
	}

	public void setWideBand(Float wideBand) {
		this.wideBand = wideBand;
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

}
