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
import com.samplepoint.beans.CDRData;
import com.samplepoint.beans.Emitter;
import com.samplepoint.dao.CDRDataMapper;
import com.samplepoint.dao.EmitterMapper;
import com.samplepoint.dao.SystemSetMapper;
import com.samplepoint.utils.CalculationTools;
import com.samplepoint.utils.Wgs84ToBaidu;
import com.samplepoint.utils.CalculationTools.MyLatLng;

import me.jor.util.Help;

@Service
public class CDRDataService {
	@Autowired
	CDRDataMapper cdrDataMapper;
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

	public List<CDRData> listCDRDataByParam(Map map) throws Exception {
		LOG.info("map:" + map);
		List<CDRData> cdrDatas = cdrDataMapper.listCDRDataByParam(map);
		// List wgs84ToBaidu = Wgs84ToBaidu.wgs84ToBaidu(cdrDatas, "cdr");
		// System.err.println("wgs84:" + wgs84ToBaidu);
		if (!Help.isEmpty(cdrDatas)) {
			cdrDatas.forEach(cdrdata -> {
				Emitter transmit = trserviceMapper.findByEmitter();
				System.err.println(transmit);
				System.err.println(cdrdata);
				cdrdata.setDistance(CalculationTools.getDistance(cdrdata.getTransforLat(), cdrdata.getTransforLng(),
						transmit.getLatitude(), transmit.getLongitude()));
				cdrdata.setAngle(
						CalculationTools.getAngle(new MyLatLng(cdrdata.getTransforLng(), cdrdata.getTransforLat()),
								new MyLatLng(transmit.getLongitude(), transmit.getLatitude())));
				try {
					String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(Help.txtToDate(cdrdata.getTime()));
					cdrdata.setTime(format);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			});
		}
		System.err.println("cdrDatas:" + cdrDatas);
		return cdrDatas;
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
		listCDRDatas().forEach(digitaldata -> {
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
	public List<CDRData> listCDRDatas() {
		List<CDRData> cdrdatas = cdrDataMapper.listCDRDatas();
		cdrdatas.forEach(cdrdata -> {
			Emitter transmit = trserviceMapper.findByEmitter();
			float distance = CalculationTools.getDistance(cdrdata.getTransforLat(), cdrdata.getTransforLng(),
					transmit.getLatitude(), transmit.getLongitude());
			cdrdata.setDistance(distance);
			float angle = CalculationTools.getAngle(new MyLatLng(cdrdata.getTransforLng(), cdrdata.getTransforLat()),
					new MyLatLng(transmit.getLongitude(), transmit.getLatitude()));
			cdrdata.setAngle(angle);
		});
		return cdrdatas;
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
	public boolean readerFileToCDRData(File file ) {
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
						CDRData cdrData = new CDRData();
						if (cdrDataMapper.checkTime(pointarr[0]) == 0) {
							cdrData.setTime(pointarr[0]);
							cdrData.setLatitude(Float.parseFloat(pointarr[1]));
							cdrData.setLongitude(Float.parseFloat(pointarr[2]));
							Map wgs84ToBaidu = Wgs84ToBaidu.wgs84ToBaidu(Float.parseFloat(pointarr[2]),
									Float.parseFloat(pointarr[1]));
							cdrData.setTransforLng((Float) (wgs84ToBaidu.get("lng")));
							cdrData.setTransforLat((Float) (wgs84ToBaidu.get("lat")));
							cdrData.setHeight(Float.parseFloat(pointarr[3]));
							cdrData.setSpeed(Float.parseFloat(pointarr[4]));
							if ("CDR".equals(pointarr[5])) {
								cdrData.setTestModeId(4);
							}
							cdrData.setFrequency(Integer.parseInt(pointarr[6]));
							cdrData.setFieldStrength(Float.parseFloat(pointarr[7]));
							cdrData.setSnr(Float.parseFloat(pointarr[8]));
							cdrData.setMer(Float.parseFloat(pointarr[9]));
							cdrData.setBer(Float.parseFloat(pointarr[10]));
							cdrData.setLdpc(Float.parseFloat(pointarr[11])); 
							if (cdrData != null) {
								addInMySQL = cdrDataMapper.saveCDRData(cdrData);
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

	public List<CDRData> listTestDataByArea(String area) {
		List<CDRData> cdrdatas = cdrDataMapper.findCDRDataByArea(area);
		cdrdatas.forEach(cdrdata -> {
			Emitter transmit = trserviceMapper.findByEmitter();
			float distance = CalculationTools.getDistance(cdrdata.getTransforLat(), cdrdata.getTransforLng(),
					transmit.getLatitude(), transmit.getLongitude());
			cdrdata.setDistance(distance);
			float angle = CalculationTools.getAngle(new MyLatLng(cdrdata.getTransforLng(), cdrdata.getTransforLat()),
					new MyLatLng(transmit.getLongitude(), transmit.getLatitude()));
			cdrdata.setAngle(angle);
		});
		return cdrdatas;
	}

	@SuppressWarnings("rawtypes")
	public int getCount(Map map) {

		return cdrDataMapper.getCount(map);
	}

	public List<Float> getFrequencys() {

		return cdrDataMapper.listFrequencys();
	}

	public int getSampleCount() {
		return cdrDataMapper.getSampleCount();
	}

	public Float getLatitueByLng(float lng) {

		return cdrDataMapper.getLatitueByLng(lng);
	}

	public Float getLngitudeByLat(float lat) {

		return cdrDataMapper.getLngitudeByLat(lat);
	}

	public Float findMaxField() {
		return cdrDataMapper.findMaxField();
	}

	public Float findMinField() {
		return cdrDataMapper.findMinField();
	}

	@SuppressWarnings("rawtypes")
	public int getFieldCount(Map map) {

		return cdrDataMapper.getFieldCount(map);
	}

	@SuppressWarnings("rawtypes")
	public int updateLngLat(Map map) {
		return cdrDataMapper.updateLngLat(map);
	}

	public int addTestData(CDRData cdrData) {

		return cdrDataMapper.saveCDRData(cdrData);
	}

	public List<String> listAreas() {
		return cdrDataMapper.listAreas();
	}

	public int deleteCDRData(int id) {
		return cdrDataMapper.deleteCDRData(id);
	}
}
