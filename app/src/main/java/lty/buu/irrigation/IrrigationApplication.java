package lty.buu.irrigation;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lty.buu.irrigation.bean.SensorConfig;
import lty.buu.irrigation.bean.SensorValue;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.HttpThread;
import lty.buu.irrigation.http.Multi_HttpThread;

public class IrrigationApplication extends Application {
BaseRequest baseRequest;
    public static Context appContext=null;
    //所有传感器的预警值范围，在进入主界面时，主动从服务器端获取
    private SensorConfig mSensorConfig;
    //各个传感器的当前数据值
    private SensorValue mCurSensorValue;
    public int getUserpermission() {
        return userpermission;
    }

    public void setUserpermission(int userpermission) {
        this.userpermission = userpermission;
    }

    int userpermission;

    public SharedPreferences getSpf() {
        return spf;
    }



    SharedPreferences spf;
    String ipstr;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpstr() {
        return spf.getString("ipstr",null);
    }

    public void setIpstr(String ipstr) {
        spf.edit().putString("ipstr",ipstr).commit();
    }

    String username;

    @Override
    public void onCreate() {
        super.onCreate();
        spf= PreferenceManager.getDefaultSharedPreferences(this);
        appContext=getApplicationContext();
        mSensorConfig = new SensorConfig();
        mCurSensorValue = new SensorValue();
        readSensorConfig();

    }

    //读取所有传感器预警值的范围
    private void readSensorConfig()
    {

    }

    //保存所有传感器预警值的范围
    public void saveSensorConfig()
    {
        SharedPreferences.Editor editor = getSpf().edit();
		/*
		请填写代码
		 */

        editor.commit();
    }

    public SensorConfig getSensorConfig() {
        return mSensorConfig;
    }
    public SensorValue getCurSensorValue() {
        return mCurSensorValue;
    }



    public Handler handler  = new Handler(){
        @Override
        public synchronized void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 200){
                HttpThread thread = (HttpThread) msg.obj;
                if(thread!=null){
                    baseRequest = thread.getBaseRequest();
                    if(baseRequest!=null){
                        String str = baseRequest.getResponsestr();
                        if(str !=null){
                            baseRequest.parseJSON(str);
                        }
                    }
                    thread.handlerLinstener(baseRequest);
                }
            }
        }
    };
    public void requestOneThread(BaseRequest baseRequest){
        HttpThread thread = new HttpThread(this,this);
        thread.setBaseRequest(baseRequest);
        thread.setPause(false);
        thread.setCancel(false);
        thread.setLoop(false);
        thread.startThread();
    }
    public HttpThread requestLoopThread(BaseRequest baseRequest, int looptime){
        HttpThread thread = new HttpThread(this,this);
        thread.setBaseRequest(baseRequest);
        thread.setPause(false);
        thread.setCancel(false);
        thread.setLoop(true);
        thread.setLooptime(looptime);
        thread.startThread();
        return  thread;

    }
    public Multi_HttpThread requestMultiLoopThread(long looptime)
    {
        Multi_HttpThread thread = new Multi_HttpThread(this,this);
        thread.setCancel(false);
        thread.setPause(false);
        thread.setLoop(true);
        thread.setLooptime(looptime);
        thread.startThread();
        return thread;
    }

    public static final int DB_READ_MAXSIZE=200;
    //防止程序假死过久设置的单次读取最大量
    Map<String,ArrayList<Object>> datadb=new HashMap<>();
    //核心数据组
    Map<String,ArrayList<Long>> datadbtimestamps=new HashMap<>();
    //时间戳数据组

    public String allocNewdb(String tag)//分配新数据库，返回对应tag
    {
        ArrayList res=datadb.get(tag);
        if(res==null)
        {
            res=new ArrayList<Object>();
            datadb.put(tag,res);
        }
        ArrayList tim=datadbtimestamps.get(tag);
        if(tim==null)
        {
            tim=new ArrayList<Long>();
            datadbtimestamps.put(tag,tim);
        }
        return tag;
    }

    //两种储存数据方法，分别接受单个数据和数据组
    public void saveTodb(String tag,ArrayList<Object> datalist)
    {
        ArrayList bj=datadb.get(tag);
        ArrayList tm=datadbtimestamps.get(tag);
        if(bj==null||tm==null){
            Log.d("T.T","no db like that!!");return;}
        for(Object v:datalist)
        {
            bj.add(v);
            tm.add(gettime());
        }

    }
    public void saveTodb(String tag,Object data)
    {
        ArrayList bj=datadb.get(tag);
        ArrayList tm=datadbtimestamps.get(tag);
        if(bj==null||tm==null){Log.d("T.T","no db like that!!");return;}
        bj.add(data);
        tm.add(gettime());
        Log.d("T.T","Current DB with data size="+bj.size());
        Log.d("T.T","Current DB with outtime size="+tm.size());
    }
    //读取数据的方法，传入tag与时间戳，将返回最多两百个在传入时间戳之后的数据组成的AL
    public ArrayList<Object> readDbdata(String tag,long afterthistime)
    {
        ArrayList<Object> res=new ArrayList<>();
        ArrayList bj=datadb.get(tag);
        ArrayList<Long> tm=datadbtimestamps.get(tag);
        if(bj==null||tm==null){Log.d("T.T","no db like that!!");return new ArrayList<Object>();}


        int index=bj.size()-1;
        while(  res.size()<DB_READ_MAXSIZE&&
                tm.get(index)>afterthistime)
        {
            Log.d("T.T","Starting READ DB index="+index);
            res.add(bj.get(index));
            index--;
            if(index==-1)break;
        }


        return res;
    }
    //辅助方法
    public long gettime()
    {
        Date dt=new Date();
        return dt.getTime();
    }
    public String getYMDHMStime()
    {
        Date dt=new Date();
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return fmt.format(dt);
    }

    public Handler Multi_handler = new Handler(){
        @Override
        public synchronized void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 200){
                BaseRequest req = (BaseRequest) msg.obj;
                if(req!=null){
                    String res = req.getResponsestr();
                    if(res!=null)req.parseJSON(res);
                    if(req.getOnBackRequestLinstener()!=null)req.getOnBackRequestLinstener().onRequestLinstener();
                }

            }
        }

    };





}
