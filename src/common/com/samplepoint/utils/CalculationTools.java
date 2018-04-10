package com.samplepoint.utils;

public class CalculationTools {
	/**
	 * 根据两坐标计算两点之间的距离 Distance
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	private static double EARTH_RADIUS = 6378.137;// 单位千米

	/**
	 * 角度弧度计算公式 rad:(). <br/>
	 * 
	 * 360度=2π π=Math.PI
	 * 
	 * x度 = x*π/360 弧度
	 * 
	 * @author chiwei
	 * @param d
	 * @return
	 * @since JDK 1.6
	 */
	private static float getRadian(float degree) {
		return (float) (degree * Math.PI / 180.0);
	}

	/**
	 * 根据经纬度计算两点之间的距离 GetDistance:(). <br/>
	 * 
	 * 
	 * @author chiwei
	 * @param lat1
	 *            1点的纬度
	 * @param lng1
	 *            1点的经度
	 * @param lat2
	 *            2点的纬度
	 * @param lng2
	 *            2点的经度
	 * @return 距离 单位 米
	 * @since JDK 1.6
	 */
	public static float getDistance(float lat1, float lng1, float lat2, float lng2) {
		float radLat1 = getRadian(lat1);
		float radLat2 = getRadian(lat2);
		float a = radLat1 - radLat2;// 两点纬度差
		float b = getRadian(lng1) - getRadian(lng2);// 两点的经度差
		float s = (float) (2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))));
		s = (float) (s * EARTH_RADIUS);
		return s * 1000;
	}

	/**
	 * 根据两个坐标计算两点之间的角
	 * 
	 * @param lng_a
	 * @param lat_a
	 * @param lng_b
	 * @param lat_b
	 * @return
	 */
	public static float getAngle(float lat_a, float lng_a, float lat_b, float lng_b) {

		float y = (float) (Math.sin(lng_b - lng_a) * Math.cos(lat_b));
		float x = (float) (Math.cos(lat_a) * Math.sin(lat_b)
				- Math.sin(lat_a) * Math.cos(lat_b) * Math.cos(lng_b - lng_a));
		float brng = (float) Math.atan2(y, x);
		brng = (float) Math.toDegrees(brng);
		if (brng < 0) {
			brng = brng + 360;
		}
		return brng;
	}

	/**
	 * 获取AB连线与正北方向的角度
	 * 
	 * @param A
	 *            A点的经纬度
	 * @param B
	 *            B点的经纬度
	 * @return AB连线与正北方向的角度（0~360）
	 */
	public static float getAngle(MyLatLng A, MyLatLng B) {
		float dx = (B.m_RadLo - A.m_RadLo) * A.Ed;
		float dy = (B.m_RadLa - A.m_RadLa) * A.Ec;
		float angle = 0.0f;
		angle = (float) (Math.atan(Math.abs(dx / dy)) * 180. / Math.PI);
		float dLo = B.m_Longitude - A.m_Longitude;
		float dLa = B.m_Latitude - A.m_Latitude;
		if (dLo > 0 && dLa <= 0) {
			angle = (float) ((90. - angle) + 90);
		} else if (dLo <= 0 && dLa < 0) {
			angle = (float) (angle + 180.);
		} else if (dLo < 0 && dLa >= 0) {
			angle = (float) ((90. - angle) + 270);
		}
		return angle;
	}

	public static class MyLatLng {
		final static float Rc = 6378137;
		final static float Rj = 6356725;
		float m_LoDeg, m_LoMin, m_LoSec;
		float m_LaDeg, m_LaMin, m_LaSec;
		float m_Longitude, m_Latitude;
		float m_RadLo, m_RadLa;
		float Ec;
		float Ed;

		public MyLatLng(float longitude, float latitude) {
			m_LoDeg = (int) longitude;
			m_LoMin = (int) ((longitude - m_LoDeg) * 60);
			m_LoSec = (float) ((longitude - m_LoDeg - m_LoMin / 60.) * 3600);

			m_LaDeg = (int) latitude;
			m_LaMin = (int) ((latitude - m_LaDeg) * 60);
			m_LaSec = (float) ((latitude - m_LaDeg - m_LaMin / 60.) * 3600);

			m_Longitude = longitude;
			m_Latitude = latitude;
			m_RadLo = (float) (longitude * Math.PI / 180.);
			m_RadLa = (float) (latitude * Math.PI / 180.);
			Ec = (float) (Rj + (Rc - Rj) * (90. - m_Latitude) / 90.);
			Ed = (float) (Ec * Math.cos(m_RadLa));
		}
	}

}
