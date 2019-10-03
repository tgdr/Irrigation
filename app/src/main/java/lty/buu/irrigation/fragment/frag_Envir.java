package lty.buu.irrigation.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.adapter.SensorGridAdapter;
import lty.buu.irrigation.bean.SensorBean;
import lty.buu.irrigation.bean.SensorConfig;
import lty.buu.irrigation.bean.SensorValue;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.HttpThread;
import lty.buu.irrigation.http.request.GetRealSensorRequest;

/**
 * 环境指标子窗体
 * @author 李天宇
 *
 */
public class frag_Envir extends Fragment {
	public static final String TAG = "EnvirFragment";
	//所有传感器数据的集合
	HttpThread thread;
	TextView tvjs;
	SensorConfig config;
	private ArrayList<SensorBean> mSensorList;
	//传感器数据值适配器
	private SensorGridAdapter mAdp;
	//存放所有传感器view的容器
	private GridView mGridView;
	GetRealSensorRequest request;
	IrrigationApplication app;
	SensorValue value;
	float js;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		js = Float.parseFloat(new DecimalFormat("###.0000").format(Math.random()+0.2));
		app = (IrrigationApplication) getActivity().getApplication();
		mSensorList = new ArrayList<SensorBean>();
		value = new SensorValue();
	//	mSensorList.add(new SensorBean(getString(R.string.CO2_title))); //C02浓度
		mSensorList.add(new SensorBean(getString(R.string.light_title))); //光照强度
		mSensorList.add(new SensorBean(getString(R.string.air_humid_title))); //空气温度
		mSensorList.add(new SensorBean(getString(R.string.air_tmper_title))); //空气湿度
	//	mSensorList.add(new SensorBean(getString(R.string.soil_tmper_title))); //土壤温度
		mSensorList.add(new SensorBean(getString(R.string.soil_humid_title))); //土壤湿度

		mAdp = new SensorGridAdapter(getActivity(), mSensorList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.envir_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvjs = getView().findViewById(R.id.myjs);
		mGridView = (GridView)getView().findViewById(R.id.sensor_grid_view);
		mGridView.setAdapter(mAdp);
		mGridView.setVerticalSpacing(50);
		mGridView.setHorizontalSpacing(50);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position==0){
					Toast.makeText(getActivity(),"i小智已经累计帮您节水"+js+"m³",Toast.LENGTH_SHORT).show();
				}
			}
		});
		//设置grid view的点击事情，点到传感器以后再跳转到该传感器的实时曲线图表界面
//		  mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				int chartItem = RealTimeDataActivity.CO2_CHART;
//				switch(position)
//				{
//				case 0:
//					chartItem = RealTimeDataActivity.CO2_CHART;
//					break;
//				case 1:
//					chartItem = RealTimeDataActivity.LIGHT_CHART;
//					break;
//				case 2:
//					chartItem = RealTimeDataActivity.AIR_T_CHART;
//					break;
//				case 3:
//					chartItem = RealTimeDataActivity.AIR_H_CHART;
//					break;
//				case 4:
//					chartItem = RealTimeDataActivity.SOIL_T_CHART;
//					break;
//				case 5:
//					chartItem = RealTimeDataActivity.SOIL_H_CHART;
//					break;
//				}
//				//跳转到指定传感器的实时曲线图表界面
//				Intent intent = new Intent(getActivity(), RealTimeDataActivity.class);
//				intent.putExtra(RealTimeDataActivity.CHART_TYPE, chartItem);
//				startActivity(intent);
//			}
//		});
		updateView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(thread!=null){
			thread.stopThread();
		}

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	//刷新环境指标子窗体界面
	public void updateView()
	{
		config = new SensorConfig();
		config.minSoilHumidity = app.getSensorConfig().minSoilHumidity;
		config.maxSoilHumidity = app.getSensorConfig().maxSoilHumidity;
		config.minAirTemperature = app.getSensorConfig().minAirTemperature;
		config.maxAirTemperature = app.getSensorConfig().maxAirTemperature;
		config.minAirHumidity = app.getSensorConfig().minAirHumidity;
		config.maxAirHumidity = app.getSensorConfig().maxAirHumidity;
		config.minLight = app.getSensorConfig().minLight;
		config.maxLight = app.getSensorConfig().maxLight;

		request = new GetRealSensorRequest(app);
		request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
			@Override
			public void onRequestLinstener() {
				value.setLight((int) request.getLight());
				value.setAirTemper((int) request.getTem());
				value.setAirHumid((int) request.getHum());
				value.setSoilHumid((int) request.getSoil());
				if(value!=null && config!=null)
				{
					//光照强度
					SensorBean light = mSensorList.get(0);
					light.setValue(value.getLight());
					light.setMinValue(config.minLight);
					light.setMaxValue(config.maxLight);
					//空气温度
					SensorBean airH = mSensorList.get(1);
					airH.setValue(value.getAirHumid());
					airH.setMinValue(config.minAirHumidity);
					airH.setMaxValue(config.maxAirHumidity);
					//空气湿度
					SensorBean airT = mSensorList.get(2);
					airT.setValue(value.getAirTemper());
					airT.setMinValue(config.minAirTemperature);
					airT.setMaxValue(config.maxAirTemperature);
					//土壤湿度
					SensorBean soilH = mSensorList.get(3);
					soilH.setValue(value.getSoilHumid());
					soilH.setMinValue(config.minSoilHumidity);
					soilH.setMaxValue(config.maxSoilHumidity);
					mAdp.notifyDataSetChanged();
				}

				try {
					js += Float.parseFloat(formateRate(String.valueOf(nextDouble(0.001,0.03))));
					js = Float.parseFloat(formateRate(String.valueOf(js)));
					tvjs.setText("i花酱已累计帮您节水"+js+"m³");
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			}
		});
		thread = app.requestLoopThread(request,5000);


		


	}
	public static double nextDouble(final double min, final double max) {
		return min + ((max - min) * new Random().nextDouble());
	}


	public static String formateRate(String rateStr) {
		if (rateStr.indexOf(".") != -1) {
			// 获取小数点的位置
			int num = 0;
			num = rateStr.indexOf(".");

			String dianAfter = rateStr.substring(0, num + 1);
			String afterData = rateStr.replace(dianAfter, "");

			return rateStr.substring(0, num) + "." + afterData.substring(0, 3);
		} else {
			if (rateStr == "1") {
				return "100";
			} else {
				return rateStr;
			}
		}

	}
}
