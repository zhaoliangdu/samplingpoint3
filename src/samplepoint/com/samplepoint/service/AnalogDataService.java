package com.samplepoint.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.samplepoint.beans.AnalogData;
import com.samplepoint.beans.Emitter;
import com.samplepoint.dao.AnalogDataMapper;
import com.samplepoint.dao.EmitterMapper;
import com.samplepoint.dao.SystemSetMapper;
import com.samplepoint.utils.CalculationTools;
import com.samplepoint.utils.Wgs84ToBaidu;
import com.samplepoint.utils.CalculationTools.MyLatLng;

import me.jor.util.Help;

@Service
public class AnalogDataService {
	@Autowired
	AnalogDataMapper analogDataMapper;
	@Autowired
	EmitterMapper trserviceMapper;
	@Autowired
	SystemSetMapper systemSetMapper;
	private static final Log LOG = LogFactory.getLog(CDRDataService.class);

	/**
	 * 根据条件查询测试数据表信息 计算角度和距离 格式化时间
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")

	public List<AnalogData> listAnalogDataByParam(Map map) throws Exception {
		LOG.info("map:" + map);
		List<AnalogData> analogDatas = analogDataMapper.listAnalogDataByParam(map);
		List wgs84ToBaidu = Wgs84ToBaidu.wgs84ToBaidu(analogDatas, "analog");
		System.err.println("wgs84:" + wgs84ToBaidu);
		if (!Help.isEmpty(analogDatas)) {
			analogDatas.forEach(analogdata -> {
				Emitter transmit = trserviceMapper.findByEmitter();
				analogdata.setDistance(CalculationTools.getDistance(analogdata.getTransforLat(),
						analogdata.getTransforLng(), transmit.getLatitude(), transmit.getLongitude()));
				analogdata.setAngle(CalculationTools.getAngle(
						new MyLatLng(analogdata.getTransforLng(), analogdata.getTransforLat()),
						new MyLatLng(transmit.getLongitude(), transmit.getLatitude())));
				try {
					String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(Help.txtToDate(analogdata.getTime()));
					analogdata.setTime(format);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			});
		}
		return analogDatas;
	}

	/**
	 * 加载采样点 信息
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List listPoints(int typeId) throws Exception {

		List list = new ArrayList<>();
		List rangelist = new ArrayList<>();
		List colorlist = new ArrayList<>();
		String[] rangeArray = null;
		String[] colorArray = null;
		if (typeId == 1) {
			rangeArray = systemSetMapper.getSystemSet().getFieldRange().split(",");
			colorArray = systemSetMapper.getFieldColors().split(",");
		} else if (typeId == 2) {
			rangeArray = systemSetMapper.getSystemSet().getSnrRange().split(",");
			colorArray = systemSetMapper.getSnrColors().split(",");
		} else {
			rangeArray = systemSetMapper.getSystemSet().getLdpcRange().split(",");
			colorArray = systemSetMapper.getLdpcColors().split(",");
		}
		for (String rangedarr : rangeArray) {
			rangelist.add(rangedarr);
		}
		for (String colorarr : colorArray) {
			colorlist.add(colorarr);
		}
		List pointInfo = new ArrayList<>();
		listAnalogDatas().forEach(digitaldata -> {
			Map<Object, Object> mapinfo = new HashMap<>(16);
			mapinfo.put("area", digitaldata.getArea());
			mapinfo.put("lon", digitaldata.getTransforLng());
			mapinfo.put("lat", digitaldata.getTransforLat());
			mapinfo.put("field", digitaldata.getFieldStrength());
			mapinfo.put("time", digitaldata.getTime());
			mapinfo.put("distance", digitaldata.getDistance());
			mapinfo.put("angle", digitaldata.getAngle());
			mapinfo.put("height", digitaldata.getHeight());
			mapinfo.put("frequency", digitaldata.getFrequency());
			mapinfo.put("snr", digitaldata.getSnr());
			mapinfo.put("center", digitaldata.getTransforLng() + "," + digitaldata.getTransforLat());
			pointInfo.add(mapinfo);
		});
		list.add(rangelist);
		list.add(pointInfo);
		list.add(colorlist);
		return list;
	}

	/**
	 * 获取模拟电视数据
	 * 
	 * @return
	 */
	public List<AnalogData> listAnalogDatas() {
		List<AnalogData> analogdatas = analogDataMapper.listAnalogDatas();
		analogdatas.forEach(analogdata -> {
			Emitter transmit = trserviceMapper.findByEmitter();
			float distance = CalculationTools.getDistance(analogdata.getTransforLat(), analogdata.getTransforLng(),
					transmit.getLatitude(), transmit.getLongitude());
			analogdata.setDistance(distance);
			float angle = CalculationTools.getAngle(
					new MyLatLng(analogdata.getTransforLng(), analogdata.getTransforLat()),
					new MyLatLng(transmit.getLongitude(), transmit.getLatitude()));
			analogdata.setAngle(angle);
		});
		return analogdatas;
	}

	/**
	 * 读取文件插入数据库
	 * 
	 * @param fileName
	 * @param area
	 * @param eid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean readerFileToRadioData(File file) {
		int addInMySQL = 0;
		try {
			String encoding = "GBK";
			if (file.isFile() && file.exists()) {
				InputStreamReader isread = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(isread);
				int t = 0;
				String lineTxt = bufferedReader.readLine();
				readfile: while (lineTxt != null) {
					if (t >= 1) {
						String[] pointarr = lineTxt.split("\t");
						AnalogData analogData = new AnalogData();
						if (analogDataMapper.checkTime(pointarr[0]) == 0) {
							analogData.setTime(pointarr[0]);
							analogData.setLatitude(Float.parseFloat(pointarr[1]));
							analogData.setLongitude(Float.parseFloat(pointarr[2]));
							Map wgs84ToBaidu = Wgs84ToBaidu.wgs84ToBaidu(Float.parseFloat(pointarr[2]),
									Float.parseFloat(pointarr[1]));
							analogData.setTransforLng((Float) (wgs84ToBaidu.get("lng")));
							analogData.setTransforLat((Float) (wgs84ToBaidu.get("lat")));
							analogData.setHeight(Float.parseFloat(pointarr[3]));
							analogData.setSpeed(Float.parseFloat(pointarr[4]));
							if ("模拟电视".equals(pointarr[5])) {
								analogData.setTestModeId(5);
							}
							analogData.setFrequency(Integer.parseInt(pointarr[6]));
							analogData.setFieldStrength(Float.parseFloat(pointarr[7]));
							analogData.setSnr(Float.parseFloat(pointarr[8]));
							if (analogData != null) {
								addInMySQL = analogDataMapper.saveAnalogData(analogData);
							}
						} else {
							System.err.println("此条数据已经存在");
							break readfile;
						}
					}
					t++;
					lineTxt = bufferedReader.readLine();
				}
				isread.close();
				bufferedReader.close();
			} else {
				System.err.println("找不到指定的文件..");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		if (addInMySQL == 1) {
			return true;
		} else {
			return false;
		}
	}

	public List<AnalogData> listTestDataByArea(String area) {
		List<AnalogData> analogdatas = analogDataMapper.findAnalogDataByArea(area);
		analogdatas.forEach(analogdata -> {
			Emitter transmit = trserviceMapper.findByEmitter();
			float distance = CalculationTools.getDistance(analogdata.getTransforLat(), analogdata.getTransforLng(),
					transmit.getLatitude(), transmit.getLongitude());
			analogdata.setDistance(distance);
			float angle = CalculationTools.getAngle(
					new MyLatLng(analogdata.getTransforLng(), analogdata.getTransforLat()),
					new MyLatLng(transmit.getLongitude(), transmit.getLatitude()));
			analogdata.setAngle(angle);
		});
		return analogdatas;
	}

	@SuppressWarnings("rawtypes")
	public int getCount(Map map) {

		return analogDataMapper.getCount(map);
	}

	public List<Float> getFrequencys() {

		return analogDataMapper.listFrequencys();
	}

	public int getSampleCount() {
		return analogDataMapper.getSampleCount();
	}

	public Float getLatitueByLng(float lng) {

		return analogDataMapper.getLatitueByLng(lng);
	}

	public Float getLngitudeByLat(float lat) {

		return analogDataMapper.getLngitudeByLat(lat);
	}

	public Float findMaxField() {
		return analogDataMapper.findMaxField();
	}

	public Float findMinField() {
		return analogDataMapper.findMinField();
	}

	@SuppressWarnings("rawtypes")
	public int getFieldCount(Map map) {

		return analogDataMapper.getFieldCount(map);
	}

	@SuppressWarnings("rawtypes")
	public int updateLngLat(Map map) {
		return analogDataMapper.updateLngLat(map);
	}

	public int addTestData(AnalogData analogData) {

		return analogDataMapper.saveAnalogData(analogData);
	}

	public List<String> listAreas() {
		return analogDataMapper.listAreas();
	}

	public int deleteAnalogData(int id) {
		return analogDataMapper.deleteAnalogData(id);
	}
}
