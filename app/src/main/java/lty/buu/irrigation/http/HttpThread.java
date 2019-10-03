
package lty.buu.irrigation.http;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import lty.buu.irrigation.AppConfig;
import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.SendRequest;


/**
 * Created by Administrator on 2017/5/27.
 */

public class HttpThread extends Thread {
    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public int getLooptime() {
        return looptime;
    }

    public void setLooptime(int looptime) {
        this.looptime = looptime;
    }

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public IrrigationApplication getApp() {
        return app;
    }

    public void setApp(IrrigationApplication app) {
        this.app = app;
    }

    boolean pause,loop,cancel;
    int looptime;
    public BaseRequest baseRequest;
    public IrrigationApplication app;

    public HttpThread(Context ct, IrrigationApplication tapp){
        app= tapp;


    }


    @Override
    public void run() {
        super.run();
        if(!cancel){
            do {
                if(!pause){

                    String ipstr= AppConfig.ip;
                    String action = baseRequest.getAction();
                    String body = baseRequest.getBody();
//                    int port = AppConfig.port;
                    String url = "http://"+ipstr+"/Irrigation/"+action;
                    Log.e("ipip",url);
                    SendRequest sendRequest = new SendRequest(url,body);
                  int result =   sendRequest.send();
                    if(result ==200){
                        String str= sendRequest.getResponsestr();
                        baseRequest.setResponsestr(str);
                        Message msg = new Message();
                        msg.what = 200;
                        msg.obj  = HttpThread.this;
                        app.handler.sendMessage(msg);
                    }
                }
                if(loop){
                    try {
                        sleep(looptime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (loop);
        }
    }
    public void handlerLinstener(BaseRequest baseRequest){
        baseRequest.getOnBackRequestLinstener().onRequestLinstener();
    }
    public void startThread(){this.start();}
    public void pauseThread(){
        pause = true;
    }
    public void stopThread(){
        cancel = true;
        pause = true;
    }
    public void destroyThread(){
        loop = false;

    }
    public void restartThread(){
        cancel = false;
        pause =  false;
        
    }

}
