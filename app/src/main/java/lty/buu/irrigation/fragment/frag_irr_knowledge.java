package lty.buu.irrigation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.request.GetIrrKnowledgeRequest;

public class frag_irr_knowledge extends Fragment implements GestureDetector.OnGestureListener {

    IrrigationApplication app;
    List<View> views;
    GetIrrKnowledgeRequest request;
    ViewFlipper flipper;
    int msgcount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_irrknowledge,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flipper = view.findViewById(R.id.knowledgeflipper);
        app = (IrrigationApplication) getActivity().getApplication();
        views = new ArrayList();
        final LayoutInflater inflater1 = getLayoutInflater();
        request  = new GetIrrKnowledgeRequest(app);
        request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
            @Override
            public void onRequestLinstener() {
                msgcount = request.getMsgcount();
                //Log.e("cococococococococ","cocococococo"+msgcount);
                for(int i=0;i<request.getMsgcount();i++){
                    View v = inflater1.inflate(R.layout.frag_irrknowledge_item,null);
                    TextView tvid = v.findViewById(R.id.tvid);
                    TextView tvmsg = v.findViewById(R.id.tvmsg);
                    tvid.setText(request.getId().get(i).toString());
                    tvmsg.setText(request.getMsg().get(i).toString());
                  //  Log.e("id",request.getId().get(i).toString());
                  //  Log.e("msg",request.getMsg().get(i).toString());
                    views.add(v);
                }


                for(int i = 0; i < msgcount; i++){
                    //在往viewFlipper中加入imageView

                    flipper.addView(views.get(i));

                }
                flipper.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }

                });

//动态设置切换时间为3000ms
                flipper.setFlipInterval(10000);
                //开始自动滚动
                flipper.startFlipping();
            }
        });

        app.requestOneThread(request);




    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
