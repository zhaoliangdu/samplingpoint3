package com.samplepoint.dao;

import java.util.List;
import java.util.Map;
import com.samplepoint.beans.AnalogData; 

public interface AnalogDataMapper {
	/**
	 * 获取频率
	 * 
	 * @return
	 */
	List<Float> listFrequencys();

	/**
	 * 条件查询模拟电视数据
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<AnalogData> listAnalogDataByParam(Map map);

	/**
	 * 获取统计
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
	 * 添加数字电视
	 * 
	 * @param testData
	 * @return
	 */
	int saveAnalogData(AnalogData testData);

	/**
	 * 根据地区获取场强
	 * 
	 * @param area
	 * @return
	 */
	List<Float> listFieldStrength(String area);

	/**
	 * 获取模拟电视List
	 * 
	 * @return
	 */
	List<AnalogData> listAnalogDatas();

	/**
	 * 统计场强
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int getFieldCount(Map map);

	/**
	 * 根据经度找纬度
	 * 
	 * @param lng
	 * @return
	 */
	Float getLatitueByLng(float lng);

	/**
	 * 根据纬度找经度
	 * 
	 * @param latF
	 * @return
	 */
	Float getLngitudeByLat(float latF);

	/**
	 * 查找最大场强
	 * 
	 * @return
	 */
	Float findMaxField();

	/**
	 * 查找最小场强
	 * 
	 * @return
	 */
	Float findMinField();

	/**
	 * 查找最大snr
	 * 
	 * @return
	 */
	Float findMaxSnr();

	/**
	 * 查找最小snr
	 * 
	 * @return
	 */
	Float findMinSnr();

	 

	/**
	 * 查找最大经度
	 * 
	 * @return
	 */
	Float findMaxLng();

	/**
	 * 查找最小经度
	 * 
	 * @return
	 */
	Float findMinLng();

	/**
	 * 查找最大纬度
	 * 
	 * @return
	 */
	Float findMaxLat();

	/**
	 * 查找最小纬度
	 * 
	 * @return
	 */
	Float findMinLat();

	/**
	 * 根据参数查找AnalogData数据
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<AnalogData> listAnalogDataByField(Map map);

	/**
	 * 根据参数修改eid
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int updateEId(Map map);

	/**
	 * 根据参数修改经纬度
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int updateLngLat(Map map);

	/**
	 * 根据id查找AnalogData数据
	 * 
	 * @param id
	 * @return
	 */
	AnalogData findById(int id);

	/**
	 * 根据时间查找数据
	 * 
	 * @param time
	 * @return
	 */
	int checkTime(String time);

	/**
	 * 根据地区找AnalogData数据
	 * 
	 * @param area
	 * @return
	 */
	List<AnalogData> findAnalogDataByArea(String area);

	/**
	 * 获取所有地区
	 * 
	 * @return
	 */
	List<String> listAreas();

	/**
	 * 删除AnalogData数据
	 * 
	 * @param id
	 * @return
	 */
	int deleteAnalogData(int id);
}
