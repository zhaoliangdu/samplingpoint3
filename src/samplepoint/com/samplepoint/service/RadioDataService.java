/**
 * 
 */
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
import com.samplepoint.beans.RadioData;
import com.samplepoint.beans.SystemSet;
import com.samplepoint.beans.Emitter;
import com.samplepoint.dao.RadioDataMapper;
import com.samplepoint.dao.SystemSetMapper;
import com.samplepoint.dao.EmitterMapper;
import com.samplepoint.utils.CalculationTools;
import com.samplepoint.utils.Wgs84ToBaidu;
import com.samplepoint.utils.CalculationTools.MyLatLng;

import me.jor.util.Help;

/**
 * @author 作者: ZhaoLiangdu
 * @time 创建时间：2017年11月10日下午1:19:47
 */
@Service
public class RadioDataService {

	@Autowired
	RadioDataMapper radioDataMapper;
	@Autowired
	EmitterMapper transmitMapper;
	@Autowired
	SystemSetMapper systemSetMapper;
	private static final Log LOG = LogFactory.getLog(RadioDataService.class);

	/**
	 * 根据条件查询测试数据表信息 计算角度和距离 格式化时间
	 * 
	 * @throws Exception
	 */

	@SuppressWarnings("rawtypes")
	public List<RadioData> listRadioDataByParam(Map map) throws Exception {
		LOG.info("map:" + map);
		List<RadioData> radioDatas = radioDataMapper.listRadioDataByParam(map);
		// List wgs84ToBaidu = Wgs84ToBaidu.wgs84ToBaidu(radioDatas, "radio");
		// System.err.println("wgs84ToBaidu:" + wgs84ToBaidu);
		if (!Help.isEmpty(radioDatas)) {
			radioDatas.forEach(radioData -> {
				Emitter transmit = transmitMapper.findByEmitter();
				radioData.setDistance(CalculationTools.getDistance(radioData.getTransforLat(),
						radioData.getTransforLng(), transmit.getLatitude(), transmit.getLongitude()));
				radioData.setAngle(CalculationTools.getAngle(radioData.getTransforLat(), radioData.getTransforLng(),
						transmit.getLatitude(), transmit.getLongitude()));
				try {
					String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(Help.txtToDate(radioData.getTime()));
					radioData.setTime(format);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			});
		}
		System.err.println("radiodatas:" + radioDatas);
		return radioDatas;
	}

	/**
	 * 获取采样点详细信息
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
		SystemSet systemSet = systemSetMapper.getSystemSet();
		if (typeId == 1) {
			rangeArray = systemSet.getFieldRange().split(",");
			colorArray = systemSet.getFieldColor().split(",");
		} else if (typeId == 2) {
			rangeArray = systemSet.getSnrRange().split(",");
			colorArray = systemSet.getSnrColor().split(",");
		} else {
			rangeArray = systemSet.getLdpcRange().split(",");
			colorArray = systemSet.getLdpcColor().split(",");
		}
		for (String rangearr : rangeArray) {
			rangelist.add(rangearr);
		}
		for (String colorarr : colorArray) {
			colorlist.add(colorarr);
		}
		List pointInfo = new ArrayList<>();
		listRadioDatas().forEach(radioData -> {
			Map<String, Object> mapinfo = new HashMap<>(16);
			mapinfo.put("area", radioData.getArea());
			mapinfo.put("lat", radioData.getTransforLat());
			mapinfo.put("lon", radioData.getTransforLng());
			mapinfo.put("field", radioData.getFieldStrength());
			mapinfo.put("time", radioData.getTime());
			mapinfo.put("distance", radioData.getDistance());
			mapinfo.put("angle", radioData.getAngle());
			mapinfo.put("height", radioData.getHeight());
			mapinfo.put("frequency", radioData.getFrequency());
			mapinfo.put("snr", radioData.getSignalNoiseRatio());
			mapinfo.put("center", radioData.getLongitude() + "," + radioData.getLatitude());
			pointInfo.add(mapinfo);
		});
		list.add(rangelist);
		list.add(pointInfo);
		list.add(colorlist);
		return list;
	}

	/**
	 * 得到调幅调频数据
	 * 
	 * @return
	 */
	public List<RadioData> listRadioDatas() {
		List<RadioData> radioDatas = radioDataMapper.listRadioDatas();
		radioDatas.forEach(radioData -> {
			Emitter transmit = transmitMapper.findByEmitter();
			float distance = CalculationTools.getDistance(radioData.getTransforLat(), radioData.getTransforLng(),
					transmit.getLatitude(), transmit.getLongitude());
			radioData.setDistance(distance);
			float angle = CalculationTools.getAngle(
					new MyLatLng(radioData.getTransforLng(), radioData.getTransforLat()),
					new MyLatLng(transmit.getLongitude(), transmit.getLatitude()));
			radioData.setAngle(angle);
		});
		return radioDatas;
	}

	/**
	 * 根据地区查调幅调频数据
	 * 
	 * @param area
	 * @return
	 */
	public List<RadioData> findRadioDataByArea(String area) {
		List<RadioData> radioDatas = radioDataMapper.listRadioDataByArea(area);
		radioDatas.forEach(radioData -> {
			Emitter transmit = transmitMapper.findByEmitter();
			float distance = CalculationTools.getDistance(radioData.getTransforLat(), radioData.getTransforLng(),
					transmit.getLatitude(), transmit.getLongitude());
			radioData.setDistance(distance);
			float angle = CalculationTools.getAngle(
					new MyLatLng(radioData.getTransforLng(), radioData.getTransforLat()),
					new MyLatLng(transmit.getLongitude(), transmit.getLatitude()));
			radioData.setAngle(angle);
		});
		return radioDatas;
	}

	/**
	 * 读取文件进数据库
	 * 
	 * @param map
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public boolean readerFileToRadioData(File file) {
		int addRadioData = 0;
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
						RadioData radioData = new RadioData();
						if (radioDataMapper.checkTime(pointarr[0]) == 0) {
							radioData.setTime(pointarr[0]);
							radioData.setLongitude(Float.parseFloat(pointarr[2]));
							radioData.setLatitude(Float.parseFloat(pointarr[1]));
							Map wgs84ToBaidu = Wgs84ToBaidu.wgs84ToBaidu(Float.parseFloat(pointarr[2]),
									Float.parseFloat(pointarr[1]));
							radioData.setTransforLng((Float) wgs84ToBaidu.get("lng"));
							radioData.setTransforLat((Float) wgs84ToBaidu.get("lat"));
							radioData.setHeight(Float.parseFloat(pointarr[3]));
							radioData.setSpeed(Float.parseFloat(pointarr[4]));
							if ("调频".equals(pointarr[5])) {
								radioData.setTestModeId(2);
							} else if ("调幅".equals(pointarr[5])) {
								radioData.setTestModeId(3);
							}
							radioData.setFrequency(Float.parseFloat(pointarr[6]));
							radioData.setWideBand(Float.parseFloat(pointarr[7]));
							radioData.setFieldStrength(Float.parseFloat(pointarr[8]));
							radioData.setFrequencyOffset(Float.parseFloat(pointarr[9]));
							radioData.setSignalNoiseRatio(Float.parseFloat(pointarr[10]));
							radioData.setRegulationSystem(Float.parseFloat(pointarr[11]));
							if (radioData != null) {
								System.err.println(radioData);
								addRadioData = radioDataMapper.saveRadioData(radioData);
							}
						} else {
							System.err.println("此条数据已经存在！");
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
			e.printStackTrace();
		}
		if (addRadioData == 1) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public int getCount(Map map) {

		return radioDataMapper.getCount(map);
	}

	public List<Float> listFrequencys() {

		return radioDataMapper.listFrequencys();
	}

	public int saveRadioData(RadioData radioData) {
		return radioDataMapper.saveRadioData(radioData);
	}

	public int getSampleCount() {

		return radioDataMapper.getSampleCount();
	}

	public Float getLatitueByLng(float lng) {

		return radioDataMapper.getLatitueByLng(lng);
	}

	public Float getLngitudeByLat(float lat) {

		return radioDataMapper.getLngitudeByLat(lat);
	}

	public Float findMaxField() {
		return radioDataMapper.findMaxField();
	}

	public Float findMinField() {
		return radioDataMapper.findMinField();
	}

	public int getFieldCount(@SuppressWarnings("rawtypes") Map map) {

		return radioDataMapper.getFieldCount(map);
	}

	public int updateLngLat(@SuppressWarnings("rawtypes") Map map) {
		return radioDataMapper.updateLngLat(map);
	}

	public List<Float> listFieldStrength(String area) {

		return radioDataMapper.listFieldStrength(area);
	}

	@SuppressWarnings("rawtypes")
	public int updateEId(Map map) {

		return radioDataMapper.updateEId(map);
	}

	public RadioData findById(int id) {

		return radioDataMapper.findById(id);
	}

	public List<String> listAreas() {
		return radioDataMapper.listAreas();
	}

	public int deleteRadioData(int id) {
		return radioDataMapper.deleteRadioData(id);
	}
}
