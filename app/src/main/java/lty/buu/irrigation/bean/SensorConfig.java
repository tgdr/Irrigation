package lty.buu.irrigation.bean;

/**
 * @author 李天宇
 * 所有传感器的阀值类
 */
public class SensorConfig 
{
	//所有传感器的预警值范围,在进入主界面时，主动从服务器端获取
	public int minAirTemperature = 0;//空气温度最小值
	public int maxAirTemperature = 0;//空气温度最大值
	public int minAirHumidity = 0;//空气湿度最小值
	public int maxAirHumidity = 0;//空气湿度最大值
	public int minSoilTemperature = 0;//土壤温度最小值
	public int maxSoilTemperature = 0;//土壤温度最大值
	public int minSoilHumidity = 0;//土壤湿度最小值
	public int maxSoilHumidity = 0;//土壤湿度最大值

	public int minLight = 0;//灯光强度最小值
	public int maxLight = 0;//灯光强度最大值
	public int minCo2 = 0;//CO2浓度最小值
	public int maxCo2 = 0;//CO2浓度最大值
	public int maxPM25 = 0;//PM2.5浓度最大值
	public int controlAuto = 0;//0表示手动控制，1表示自动控制
}
