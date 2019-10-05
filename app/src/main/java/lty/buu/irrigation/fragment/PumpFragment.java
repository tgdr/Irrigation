package lty.buu.irrigation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.request.SetPumpRequest;

public class PumpFragment extends Fragment {
    private final static String ON = "1";
    private final static String OFF = "0";

    private ImageView ivWater;
    private boolean isWater;



    private TextView tvMoisture, tvWaterbox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pump,null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isWater=false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivWater = (ImageView) getView().findViewById(R.id.iv_water);
        ivWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWater) {
                    AnimationSet set = new AnimationSet(false);
                    Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
                    Animation alpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                    LinearInterpolator lin = new LinearInterpolator();
                    set.setInterpolator(lin);
                    set.addAnimation(rotate);
                    set.addAnimation(alpha);
                    set.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            ivWater.setClickable(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ivWater.setImageResource(R.drawable.ic_water_on);
                            ivWater.clearAnimation();
                            ivWater.setClickable(true);

                            SetPumpRequest request=new SetPumpRequest((IrrigationApplication) getActivity().getApplication());
                            request.setKaiguan(true);
                            request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                                @Override
                                public void onRequestLinstener() {
                                    Toast.makeText(getContext(),"开启网络访问成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                             ((IrrigationApplication) getActivity().getApplication()).requestOneThread(request);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ivWater.startAnimation(set);
                    isWater = false;
                } else {
                    AnimationSet set = new AnimationSet(false);
                    Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.tip_n);
                    Animation alpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
                    LinearInterpolator lin = new LinearInterpolator();
                    set.setInterpolator(lin);
                    set.addAnimation(rotate);
                    set.addAnimation(alpha);
                    set.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            ivWater.setClickable(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ivWater.setImageResource(R.drawable.ic_water_off);
                            ivWater.clearAnimation();
                            ivWater.setClickable(true);


                            SetPumpRequest request=new SetPumpRequest((IrrigationApplication) getActivity().getApplication());
                            request.setKaiguan(false);
                            request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                                @Override
                                public void onRequestLinstener() {
                                    Toast.makeText(getContext(),"开启网络访问成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                            ((IrrigationApplication) getActivity().getApplication()).requestOneThread(request);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ivWater.startAnimation(set);
                    isWater = true;
                }
            }
        });
        if (isWater) {
            ivWater.setImageResource(R.drawable.ic_water_on);
        } else {
            ivWater.setImageResource(R.drawable.ic_water_off);
        }
    }

    private void initView() {
        tvMoisture = (TextView) getView().findViewById(R.id.tv_moisture_num);
        tvWaterbox = (TextView) getView().findViewById(R.id.tv_waterBox_num);
    }


    private void getSwitchData() {

    }





}
