package com.samplepoint.dao;

import com.samplepoint.beans.SystemSet;

/**
 * 
 * @author 作者: ZhaoLiangdu
 * @time 创建时间：2017年11月9日上午9:51:08
 */
public interface SystemSetMapper {

	/**
	 * 获取系统设置
	 * 
	 * @return
	 */
	SystemSet getSystemSet();

	/**
	 * 获取场强颜色字符串
	 * 
	 * @param uid
	 * @return
	 */
	String getFieldColors();

	/**
	 * 获取snr颜色
	 * 
	 * @param uid
	 * @return
	 */
	String getSnrColors();

	/**
	 * 获取ldpc颜色
	 * 
	 * @param uid
	 * @return
	 */
	String getLdpcColors();

	/**
	 * 恢复默认设置
	 * 
	 * @param uid
	 * @return
	 */
	int resetSysSet();

	/**
	 * 修改场强
	 * 
	 * @param systemSet
	 * @return
	 */
	int updateFieldSet(SystemSet systemSet);

	/**
	 * 修改snr
	 * 
	 * @param systemSet
	 * @return
	 */
	int updateSnrSet(SystemSet systemSet);

	/**
	 * 修改ldpc
	 * 
	 * @param systemSet
	 * @return
	 */
	int updateLdpcSet(SystemSet systemSet);

}
