package com.samplepoint.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.samplepoint.beans.AnalogData;
import com.samplepoint.beans.CDRData;
import com.samplepoint.beans.DigitalData;
import com.samplepoint.beans.RadioData;
import me.jor.http.HttpClient;

/**
 * 
 * @author zhao
 *
 */
public class Wgs84ToBaidu {

	private static final String STATUS = "status", RESULT = "result", X = "x", Y = "y";

	/**
	 * 坐标转换
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List wgs84ToBaidu(List testDatas, String type) throws Exception {
		String getJson = "";
		Iterator iterator = testDatas.iterator();
		List list = new ArrayList<>();
		float longitude = 0, latitude = 0;
		int id = 0;
		while (iterator.hasNext()) {
			if ("digital".equals(type)) {
				DigitalData digitaldata = (DigitalData) iterator.next();
				longitude = digitaldata.getLongitude();
				latitude = digitaldata.getLatitude();
				id = digitaldata.getId();
			}
			if ("radio".equals(type)) {
				RadioData radioData = (RadioData) iterator.next();
				longitude = radioData.getLongitude();
				latitude = radioData.getLatitude();
				id = radioData.getId();
			}
			if("cdr".equals(type)) {
				CDRData cdrData = (CDRData) iterator.next();
				longitude = cdrData.getLongitude();
				latitude = cdrData.getLatitude();
				id = cdrData.getId();
			}
			if("analog".equals(type)) {
				AnalogData analogData = (AnalogData) iterator.next();
				longitude = analogData.getLongitude();
				latitude = analogData.getLatitude();
				id = analogData.getId();
			}
			String url = "http://api.map.baidu.com/geoconv/v1/?coords=" + longitude + "," + latitude
					+ "&from=1&to=5&ak=S9eC9hSo7haHPpzXiXtv7rApqfeVnIip";
			getJson = new HttpClient(url, "utf-8").get();
			Map mapjson = new Gson().fromJson(getJson, Map.class);
			List<Map> listmap = (List<Map>) mapjson.get(RESULT);
			if ((Double) mapjson.get(STATUS) == 0) {
				Iterator<Map> listiterator = listmap.iterator();
				DecimalFormat df = new DecimalFormat("#.0000");
				Map mapxy = listiterator.next();
				Double lngDou = (Double) mapxy.get(X);
				String lngStr = df.format(lngDou);
				float tranLng = Float.parseFloat(lngStr);
				Double latDou = (Double) mapxy.get(Y);
				String latStr = df.format(latDou);
				float tranLat = Float.parseFloat(latStr);
				HashMap<String, Object> map = new HashMap(16);
				map.put("id", id);
				map.put("transforLng", tranLng);
				map.put("transforLat", tranLat);
				list.add(map);
			} else {
				System.err.println("百度坐标转换错误，状态：" + mapjson.get(STATUS));
			}
		} 
		return list;
	}

	/**
	 * 百度坐标转换原始经度+0.0116 ，纬度+0.0072
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map wgs84ToBaidu(Float longitude, Float latitude) throws Exception {
		String getJson = "";
		HashMap<String, Float> map = new HashMap(16);
		String url = "http://api.map.baidu.com/geoconv/v1/?coords=" + longitude + "," + latitude
				+ "&from=1&to=5&ak=S9eC9hSo7haHPpzXiXtv7rApqfeVnIip";
		getJson = new HttpClient(url, "utf-8").get();
		Map mapjson = new Gson().fromJson(getJson, Map.class);
		List<Map> listmap = (List<Map>) mapjson.get(RESULT);
		if ((Double) mapjson.get(STATUS) == 0) {
			Iterator<Map> listiterator = listmap.iterator();
			DecimalFormat df = new DecimalFormat("#.0000");
			Map mapxy = listiterator.next();
			Double lngDou = (Double) mapxy.get(X);
			String lngStr = df.format(lngDou);
			float tranLng = Float.parseFloat(lngStr);
			Double latDou = (Double) mapxy.get(Y);
			String latStr = df.format(latDou);
			float tranLat = Float.parseFloat(latStr);
			map.put("lng", tranLng);
			map.put("lat", tranLat);
		} else {
			System.err.println("百度坐标转换错误，状态：" + mapjson.get(STATUS));
		}
		System.err.println("wgslist:" + map);
		return map;
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Float lng = 122.8400f;
		Float lat = 39.7453f;
		try {
			Map wgs84ToBaidu = wgs84ToBaidu(lng, lat);
			System.err.println(wgs84ToBaidu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
