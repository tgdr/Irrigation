package lty.buu.irrigation.http;

import android.content.Context;
import android.os.Message;

import java.util.ArrayList;

import lty.buu.irrigation.IrrigationApplication;

/**
 * Created by Administrator on 2017/5/24.
 */

public class Multi_HttpThread extends Thread {
    IrrigationApplication app;
    public Multi_HttpThread(Context ct, IrrigationApplication tapp){
        app = tapp;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public long getLooptime() {
        return looptime;
    }

    public void setLooptime(long looptime) {
        this.looptime = looptime;
    }


    public void addRequests(BaseRequest baseRequests) {
        this.baseRequests.add(baseRequests);
    }
    public ArrayList<BaseRequest> getAllReq()
    {
        return baseRequests;
    }
    public BaseRequest getLastReq()
    {
        return baseRequests.get(baseRequests.size()-1);
    }
    public boolean pause,cancel,loop;
    public long looptime;
    ArrayList<BaseRequest> baseRequests=new ArrayList<>();

    @Override
    public void run() {
        super.run();
        if(!cancel){
            do {
                if(!pause){
                    for(BaseRequest req:baseRequests) {
//                    S                                    tring ipstr= baseRequests.getApp().getIpstr();
                        String ipstr = "192.168.1.107";
                        int port = 8080;
                        String action = req.getAction();
                        String body = req.getBody();
                        String url = "http://" + ipstr + ":" + port + "/" + action;
                        SendRequest sendRequest = new SendRequest(url, body);
                        int result = sendRequest.send();
                        if (result == 200) {
                            String res = sendRequest.getResponsestr();
                            req.setResponsestr(res);
                            Message msg = new Message();
                            msg.what = 200;
                            msg.obj = req;
                            app.Multi_handler.sendMessage(msg);
                        }
                        if (loop) {
                            try {
                                sleep(looptime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }while (loop);
        }
    }
    public void startThread(){
        this.start();
    }
    public void restartThread(){
        pause = false;
        cancel = false;
    }
    public void pauseThread(){
        pause = true;
    }
    public void stopThread(){
        cancel  = true;
        pause = true;
    }
    public void destroyThread(){
        loop = false;
    }
    public void handlerLinstener(BaseRequest baseRequest){
        baseRequest.getOnBackRequestLinstener().onRequestLinstener();
    }
}
