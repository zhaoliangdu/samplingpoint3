package com.samplepoint.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.samplepoint.dao.DigitalDataMapper;
import com.samplepoint.dao.EmitterMapper;
import com.samplepoint.dao.SystemSetMapper;
import com.samplepoint.beans.DigitalData;
import com.samplepoint.beans.Emitter;
import com.samplepoint.utils.CalculationTools;
import com.samplepoint.utils.Wgs84ToBaidu;
import com.samplepoint.utils.CalculationTools.MyLatLng; 

/**
 * 
 * @author 作者: ZhaoLiangdu
 * @time 创建时间：2017年11月9日上午9:50:20
 */
@Service
public class DigitalDataService {
	@Autowired
	DigitalDataMapper digitalDataMapper;
	@Autowired
	EmitterMapper trserviceMapper;
	@Autowired
	SystemSetMapper systemSetMapper;
 
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
		listDigitalDatas().forEach(digitaldata -> {
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
			mapinfo.put("ldpc", digitaldata.getLdpc());
			mapinfo.put("center", digitaldata.getTransforLng() + "," + digitaldata.getTransforLat());
			pointInfo.add(mapinfo);
		});
		list.add(rangelist);
		list.add(pointInfo);
		list.add(colorlist);
		return list;
	}

	/**
	 * 获取数字电视数据
	 * 
	 * @return
	 */
	public List<DigitalData> listDigitalDatas() {
		List<DigitalData> digitaldatas = digitalDataMapper.listDigitalDatas();
		digitaldatas.forEach(digitaldata -> {
			Emitter transmit = trserviceMapper.findByEmitter();
			float distance = CalculationTools.getDistance(digitaldata.getTransforLat(), digitaldata.getTransforLng(),
					transmit.getLatitude(), transmit.getLongitude());
			digitaldata.setDistance(distance);
			float angle = CalculationTools.getAngle(
					new MyLatLng(digitaldata.getTransforLng(), digitaldata.getTransforLat()),
					new MyLatLng(transmit.getLongitude(), transmit.getLatitude()));
			digitaldata.setAngle(angle);
		});
		return digitaldatas;
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
	public boolean readerFileToDigitalData(File file) {
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
						DigitalData digitalData = new DigitalData();
						if (digitalDataMapper.checkTime(pointarr[0]) == 0) {
							digitalData.setTime(pointarr[0]);
							digitalData.setLongitude(Float.parseFloat(pointarr[1]));
							digitalData.setLatitude(Float.parseFloat(pointarr[2]));
							Map wgs84ToBaidu = Wgs84ToBaidu.wgs84ToBaidu(Float.parseFloat(pointarr[1]),
									Float.parseFloat(pointarr[2]));
							digitalData.setTransforLng((Float) (wgs84ToBaidu.get("lng")));
							digitalData.setTransforLat((Float) (wgs84ToBaidu.get("lat")));
							digitalData.setHeight(Float.parseFloat(pointarr[3]));
							digitalData.setSpeed(Float.parseFloat(pointarr[4]));
							if ("数字电视".equals(pointarr[5])) {
								digitalData.setTestModeId(1);
							}
							digitalData.setFrequency(Integer.parseInt(pointarr[6]));
							digitalData.setWideBand(Float.parseFloat(pointarr[7]));
							digitalData.setFieldStrength(Float.parseFloat(pointarr[8]));
							digitalData.setSnr(Float.parseFloat(pointarr[9]));
							digitalData.setMer(Float.parseFloat(pointarr[10]));
							digitalData.setLdpc(Float.parseFloat(pointarr[11]));
							if (digitalData != null) {
								addInMySQL = digitalDataMapper.saveDigitalData(digitalData);
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

	@SuppressWarnings("rawtypes")
	public int getCount(Map map) {

		return digitalDataMapper.getCount(map);
	}

	public List<Float> getFrequencys() {

		return digitalDataMapper.listFrequencys();
	}

	public int getSampleCount() {
		return digitalDataMapper.getSampleCount();
	}

	public Float getLatitueByLng(float lng) {

		return digitalDataMapper.getLatitueByLng(lng);
	}

	public Float getLngitudeByLat(float lat) {

		return digitalDataMapper.getLngitudeByLat(lat);
	}

	public Float findMaxField() {
		return digitalDataMapper.findMaxField();
	}

	public Float findMinField() {
		return digitalDataMapper.findMinField();
	}

	@SuppressWarnings("rawtypes")
	public int getFieldCount(Map map) {

		return digitalDataMapper.getFieldCount(map);
	}

	@SuppressWarnings("rawtypes")
	public int updateLngLat(Map map) {
		return digitalDataMapper.updateLngLat(map);
	}

	public int addTestData(DigitalData digitalData) {

		return digitalDataMapper.saveDigitalData(digitalData);
	}

	public int deleteDigitalData(int id) {
		return digitalDataMapper.deleteDigitalData(id);
	}
}
