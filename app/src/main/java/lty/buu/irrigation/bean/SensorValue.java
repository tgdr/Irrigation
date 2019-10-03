package lty.buu.irrigation.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 李天宇
 * 所有传感器值的类，记录了当前所有传感器的值
 */
public class SensorValue implements Comparable<SensorValue>,Cloneable
{
	private Date updataTime = null; //取得传感器数据值的时间戳
	private int pm25 = -1; 			//PM2.5浓度
	private int co2 = -1;			//CO2浓度
	private int light = -1;			//灯光强度
	
	private int airTemper = -1;		//空气温度
	private int airHumid = -1;		//空气湿度
	private int soilTemper = -1;	//土壤温度
	private int	soilHumid = -1;		//土壤湿度
	
	public SensorValue()
	{
	}
	
	//转成字符串，便于日志打印
	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		if(updataTime!=null){
			buffer.append(dateFormat.format(updataTime));
			buffer.append(",");
		}
		buffer.append("PM2.5="+pm25);
		buffer.append(",C02="+co2);
		buffer.append(",light="+light);
		buffer.append(",airTemper="+airTemper);
		buffer.append(",airHumid="+airHumid);
		buffer.append(",soilTemper="+soilTemper);
		buffer.append(",soilHumid="+soilHumid);
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public int compareTo(SensorValue another) {
		return updataTime.compareTo(another.updataTime);
	}

	public Date getUpdataTime() {
		return updataTime;
	}
	public void setUpdataTime(Date updataTime) {
		this.updataTime = updataTime;
	}
	public int getPm25() {
		return pm25;
	}
	public void setPm25(int pm25) {
		this.pm25 = pm25;
	}
	public int getCo2() {
		return co2;
	}
	public void setCo2(int co2) {
		this.co2 = co2;
	}
	public int getLight() {
		return light;
	}
	public void setLight(int light) {
		this.light = light;
	}
	public int getAirTemper() {
		return airTemper;
	}
	public void setAirTemper(int airTemper) {
		this.airTemper = airTemper;
	}
	public int getAirHumid() {
		return airHumid;
	}
	public void setAirHumid(int airHumid) {
		this.airHumid = airHumid;
	}
	public int getSoilTemper() {
		return soilTemper;
	}
	public void setSoilTemper(int soilTemper) {
		this.soilTemper = soilTemper;
	}
	public int getSoilHumid() {
		return soilHumid;
	}
	public void setSoilHumid(int soilHumid) {
		this.soilHumid = soilHumid;
	}
	
	//克隆对象
	public SensorValue clone()
	{
		SensorValue cloneObj = null;
		try {
			cloneObj = (SensorValue)super.clone();
			if(updataTime != null){
				cloneObj.updataTime = (Date)updataTime.clone();
			} else {
				cloneObj.updataTime = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloneObj;
	}
}
