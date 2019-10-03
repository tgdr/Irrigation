package lty.buu.irrigation.fragment;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.request.SetPumpRequest;


/**
 * 环境指标子窗体
 * @author 胡宇腾
 *
 */
public class frag_pumpContr extends Fragment {
    ImageView kai;
    ImageView guan;
    IrrigationApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (IrrigationApplication) getActivity().getApplication();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pumpset, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        kai=getView().findViewById(R.id.pumpkai);
        guan=getView().findViewById(R.id.pumpguan);

        kai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPumpRequest request=new SetPumpRequest(app);
                request.setKaiguan(true);
                request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                    @Override
                    public void onRequestLinstener() {
                        Toast.makeText(getContext(),"开启网络访问成功",Toast.LENGTH_SHORT).show();
                    }
                });
                app.requestOneThread(request);
            }

        });
        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SetPumpRequest request=new SetPumpRequest(app);
            request.setKaiguan(false);
            request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                @Override
                public void onRequestLinstener() {
                    Toast.makeText(getContext(),"关闭网络访问成功",Toast.LENGTH_SHORT).show();
                }
            });
                app.requestOneThread(request);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
