package lty.buu.irrigation.bean;

/**
 * @author 李天宇
 * 传感器信息类，包含了传感器的名称，值，以及其阀值范围
 */
public class SensorBean 
{
	//传感器名称
	private String name = "";
	//传感器的值
	private int value = 0;
	//传感器阀值的最小值
	private int minValue = Integer.MIN_VALUE;
	//传感器阀值的最大值
	private int maxValue = Integer.MIN_VALUE;
	
	public SensorBean(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getMinValue() {
		return minValue;
	}
	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	
}
