/**
 * 调幅调频
 */
package com.samplepoint.beans;

import java.io.Serializable;

/**
 * @author 作者: ZhaoLiangdu
 * @time 创建时间：2017年11月10日下午1:15:28
 */
public class RadioData implements Serializable {
	 
	private static final long serialVersionUID = 1L;
	private int id;
	private String time;
	private String area;
	private float longitude;
	private float latitude;
	private float height;
	private float speed;
	private int testModeId;
	private float frequency;
	private Float wideBand;
	private float fieldStrength;
	private float frequencyOffset;
	private float signalNoiseRatio;
	private float regulationSystem;
	private float transforLat;
	private float transforLng;
	private float distance;
	private float angle;
	private int eid;

	@Override
	public String toString() {
		return "RadioData [id=" + id + ", time=" + time + ", area=" + area + ", longitude=" + longitude + ", latitude="
				+ latitude + ", height=" + height + ", speed=" + speed + ", testModeId=" + testModeId + ", frequency="
				+ frequency + ", wideBand=" + wideBand + ", fieldStrength=" + fieldStrength + ", frequencyOffset="
				+ frequencyOffset + ", signalNoiseRatio=" + signalNoiseRatio + ", regulationSystem=" + regulationSystem
				+ ", transforLat=" + transforLat + ", transforLng=" + transforLng + ", distance=" + distance
				+ ", angle=" + angle + ", eid=" + eid + "]";
	}

	public RadioData(String time, String area, float longitude, float latitude, float height, float speed,
			int testModeId, float frequency, Float wideBand, float fieldStrength, float frequencyOffset,
			float signalNoiseRatio, float regulationSystem, float transforLat, float angle, int eid) {
		super();
		this.time = time;
		this.area = area;
		this.longitude = longitude;
		this.latitude = latitude;
		this.height = height;
		this.speed = speed;
		this.testModeId = testModeId;
		this.frequency = frequency;
		this.wideBand = wideBand;
		this.fieldStrength = fieldStrength;
		this.frequencyOffset = frequencyOffset;
		this.signalNoiseRatio = signalNoiseRatio;
		this.regulationSystem = regulationSystem;
		this.transforLat = transforLat;
		this.angle = angle;
		this.eid = eid;
	}

	public RadioData(int id, String time, String area, float longitude, float latitude, float height, float speed,
			int testModeId, float frequency, Float wideBand, float fieldStrength, float frequencyOffset,
			float signalNoiseRatio, float regulationSystem, float transforLat, float transforLng, float distance,
			float angle, int eid) {
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
		this.wideBand = wideBand;
		this.fieldStrength = fieldStrength;
		this.frequencyOffset = frequencyOffset;
		this.signalNoiseRatio = signalNoiseRatio;
		this.regulationSystem = regulationSystem;
		this.transforLat = transforLat;
		this.transforLng = transforLng;
		this.distance = distance;
		this.angle = angle;
		this.eid = eid;
	}

	public RadioData() {
		super();
		// TODO 自动生成的构造函数存根
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

	public Float getWideBand() {
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

	public float getFrequencyOffset() {
		return frequencyOffset;
	}

	public void setFrequencyOffset(float frequencyOffset) {
		this.frequencyOffset = frequencyOffset;
	}

	public float getSignalNoiseRatio() {
		return signalNoiseRatio;
	}

	public void setSignalNoiseRatio(float signalNoiseRatio) {
		this.signalNoiseRatio = signalNoiseRatio;
	}

	public float getRegulationSystem() {
		return regulationSystem;
	}

	public void setRegulationSystem(float regulationSystem) {
		this.regulationSystem = regulationSystem;
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
