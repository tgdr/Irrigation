package lty.buu.irrigation.bean;

import java.util.LinkedList;
/*
 * 曲线图表信息类，存储两个曲线图的信息
 */
public class ChartPagerBean 
{
	public ChartPagerBean(String majorName)
	{
		this.majorName = majorName;
	}
	
	//主曲线图的名称
	public String majorName = "";
	//主曲线图的点集合
	public LinkedList<Integer> majorValueList = new LinkedList<Integer>();
	//主曲线图y轴最小值
	public int majorMin = 0;
	//主曲线图y轴最大值
	public int majorMax = 0;
	//告警最小值
	public int majorWarningMin = 0;
	//告警最大值
	public int majorWarningMax = 0;
	
	//是否存在次曲线图
	public boolean isHasSlave = false;
	//次曲线图的名称
	public String slaveName = "";
	//次曲线图的点集合
	public LinkedList<Integer> slaveValueList = new LinkedList<Integer>();
	//次曲线图y轴最小值
	public int slaveMin = 0;
	//次曲线图y轴最大值
	public int slaveMax = 0;
	//告警最小值
	public int slaveWarningMin = 0;
	//告警最大值
	public int slaveWarningMax = 0;
}
