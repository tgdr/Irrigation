package lty.buu.irrigation.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.request.GetRealSensorRequest;
import lty.buu.irrigation.http.request.SetPumpRequest;

import static android.content.Context.MODE_PRIVATE;

public class frag_moren extends Fragment {
    IrrigationApplication app;
    ImageView aukai;
    ImageView auGuan;
    SharedPreferences spf;
    Timer timer1;
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (IrrigationApplication) getActivity().getApplication();
        spf=getActivity().getSharedPreferences("mauto",MODE_PRIVATE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.moren, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aukai=getView().findViewById(R.id.autokai);
        auGuan=getView().findViewById(R.id.autoguan);

        aukai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=spf.edit();
                editor.putInt("switch",1);
                editor.commit();
                Toast.makeText(getContext(),"已执行操作开启",Toast.LENGTH_SHORT).show();
                Log.e("已执行操作", String.valueOf(spf.getInt("switch",-1)));
            }
        });
        auGuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=spf.edit();
                editor.putInt("switch",2);
                editor.commit();
                Toast.makeText(getContext(),"已执行操作关闭",Toast.LENGTH_SHORT).show();
                Log.e("已执行操作", String.valueOf(spf.getInt("switch",-1)));
            }
        });



         timer1=new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("tttt","fa qingqiule");
                if ((spf.getInt("switch",1)==1)) {
                    Log.e("tttt","fa qingqiule2222222222222");
                    final GetRealSensorRequest request0 = new GetRealSensorRequest(app);
                    request0.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                        @Override
                        public void onRequestLinstener() {
                            Log.e("",String.valueOf(request0.getSoil()));
                            if (request0.getSoil()>450){
                                SetPumpRequest request=new SetPumpRequest(app);
                                request.setKaiguan(true);
                                request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                                    @Override
                                    public void onRequestLinstener() {
                                     //   Toast.makeText(getContext(),"因干燥尝试开启水泵",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                app.requestOneThread(request);
                            }else if (request0.getSoil()<390){
                                SetPumpRequest request=new SetPumpRequest(app);
                                request.setKaiguan(false);
                                request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                                    @Override
                                    public void onRequestLinstener() {
                                     //   Toast.makeText(getContext(),"因过湿尝试关闭水泵",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                app.requestOneThread(request);
                            }
                        }
                    });
                    app.requestOneThread(request0);

                }
            }
        },500,2000);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer1.cancel();
    }
}
