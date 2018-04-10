package com.samplepoint.dao;

import java.util.List;
import java.util.Map;

import com.samplepoint.beans.RadioData;

/**
 * @author 作者: ZhaoLiangdu
 * @time 创建时间：2017年11月10日下午1:18:57
 */
public interface RadioDataMapper {

	/**
	 * 获取所有频率
	 * 
	 * @return
	 */
	List<Float> listFrequencys();

	/**
	 * 根据条件返回List<radio>
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<RadioData> listRadioDataByParam(Map map);

	/**
	 * 根据条件返回记录数
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int getCount(Map map);

	/**
	 * 返回总记录数
	 * 
	 * @return
	 */
	int getSampleCount();

	/**
	 * 添加radio数据
	 * 
	 * @param radioData
	 * @return
	 */
	int saveRadioData(RadioData radioData);

	/**
	 * 根据地区返回场强list
	 * 
	 * @param area
	 * @return
	 */
	List<Float> listFieldStrength(String area);

	/**
	 * 获取List<radio>
	 * 
	 * @return
	 */
	List<RadioData> listRadioDatas();

	/**
	 * 返回转换坐标的List<RadioData>
	 * 
	 * @return
	 */
	List<RadioData> listRadioDatatsf();

	/**
	 * 根据条件返回场强统计
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int getFieldCount(Map map);

	/**
	 * 根据经度返回纬度
	 * 
	 * @param lng
	 * @return
	 */
	Float getLatitueByLng(float lng);

	/**
	 * 根据纬度返回经度
	 * 
	 * @param lat
	 * @return
	 */
	Float getLngitudeByLat(float lat);

	/**
	 * 根据地区返回List<RadioData>
	 * 
	 * @param area
	 * @return
	 */
	List<RadioData> listRadioDataByArea(String area);

	/**
	 * 返回最大场强
	 * 
	 * @return
	 */
	Float findMaxField();

	/**
	 * 返回最小场强
	 * 
	 * @return
	 */
	Float findMinField();

	/**
	 * 返回最大snr
	 * 
	 * @return
	 */
	Float findMaxSnr();

	/**
	 * 返回最小snr
	 * 
	 * @return
	 */
	Float findMinSnr();

	/**
	 * 返回最大经度
	 * 
	 * @return
	 */
	Float findMaxLng();

	/**
	 * 返回最小经度
	 * 
	 * @return
	 */
	Float findMinLng();

	/**
	 * 返回最大纬度
	 * 
	 * @return
	 */
	Float findMaxLat();

	/**
	 * 返回最小纬度
	 * 
	 * @return
	 */
	Float findMinLat();

	/**
	 * 根据条件返回List<RadioData>
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<RadioData> listRadioDataByField(Map map);

	/**
	 * 根据条件修改eid
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int updateEId(Map map);

	/**
	 * 根据条件修改经纬度
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int updateLngLat(Map map);

	/**
	 * 根据id返回radio
	 * 
	 * @param id
	 * @return
	 */
	RadioData findById(int id);

	/**
	 * 根据时间查找数据
	 * 
	 * @param time
	 * @return
	 */
	int checkTime(String time);

	/**
	 * 返回所有地区
	 * 
	 * @return
	 */
	List<String> listAreas();

	/**
	 * 根据id删除radio
	 * 
	 * @param id
	 * @return
	 */
	int deleteRadioData(int id);
}
