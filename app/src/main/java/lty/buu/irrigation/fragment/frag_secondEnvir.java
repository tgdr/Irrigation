package lty.buu.irrigation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.bean.SensorBean;
import lty.buu.irrigation.bean.SensorConfig;
import lty.buu.irrigation.bean.SensorValue;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.HttpThread;
import lty.buu.irrigation.http.request.GetRealSensorRequest;

public class frag_secondEnvir extends Fragment {
    IrrigationApplication app;
    HttpThread thread;
    SensorConfig config;
    TextView tv_soilhum,tv_tem,tv_light,tv_hum;
    TextView tv_temyz,tv_humyz,tv_lightyz,tv_soilhumyz;
    GetRealSensorRequest request;
    SensorValue value;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (IrrigationApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_realtime,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_soilhum = view.findViewById(R.id.tv_moisture_num);
        tv_tem = view.findViewById(R.id.tv_temp);
        tv_hum=view.findViewById(R.id.tv_minTemp);
        tv_light =view.findViewById(R.id.tv_maxTemp);
        tv_humyz = view.findViewById(R.id.tv_humyz);
        tv_lightyz = view.findViewById(R.id.tv_lightyz);
        tv_soilhumyz = view.findViewById(R.id.tv_soilhumyz);
        tv_temyz = view.findViewById(R.id.tv_temyz);
        config = new SensorConfig();
        config.minSoilHumidity = app.getSensorConfig().minSoilHumidity;
        config.maxSoilHumidity = app.getSensorConfig().maxSoilHumidity;
        config.minAirTemperature = app.getSensorConfig().minAirTemperature;
        config.maxAirTemperature = app.getSensorConfig().maxAirTemperature;
        config.minAirHumidity = app.getSensorConfig().minAirHumidity;
        config.maxAirHumidity = app.getSensorConfig().maxAirHumidity;
        config.minLight = app.getSensorConfig().minLight;
        config.maxLight = app.getSensorConfig().maxLight;
        tv_temyz.setText("温度："+config.minAirTemperature+"~"+config.maxAirTemperature+"℃");
        tv_humyz.setText("湿度："+config.minAirHumidity+"~"+config.maxAirHumidity+"%");
        tv_lightyz.setText("光照："+config.minLight+"~"+config.maxLight+"lx");
        tv_soilhumyz.setText("土壤："+config.minSoilHumidity+"~"+config.maxSoilHumidity);
        value = new SensorValue();
        initdata();
        getView().findViewById(R.id.iv_plant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mycontent,new PumpFragment()).commit();
            }
        });
    }

    private void initdata() {
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
                   tv_soilhum.setText(value.getSoilHumid()+"");
                   tv_light.setText(value.getLight()+"lx");
                   tv_tem.setText(value.getAirTemper()+"");
                   tv_hum.setText(value.getAirHumid()+"%");
                }



            }
        });
        thread = app.requestLoopThread(request,5000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(thread!=null){
            thread.stopThread();
        }

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            if(thread!=null){
                thread.stopThread();
            }
        }
    }

}
