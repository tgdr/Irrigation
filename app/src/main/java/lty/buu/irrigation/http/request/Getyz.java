package lty.buu.irrigation.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.REQNAME;

public class Getyz extends BaseRequest {
    boolean enable = false;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public double getYz_tem() {
        return yz_tem;
    }

    public void setYz_tem(double yz_tem) {
        this.yz_tem = yz_tem;
    }

    public double getYz_hum() {
        return yz_hum;
    }

    public void setYz_hum(double yz_hum) {
        this.yz_hum = yz_hum;
    }

    public double getYz_light() {
        return yz_light;
    }

    public void setYz_light(double yz_light) {
        this.yz_light = yz_light;
    }

    public double getYz_soil() {
        return yz_soil;
    }

    public void setYz_soil(double yz_soil) {
        this.yz_soil = yz_soil;
    }

    double yz_tem,yz_hum,yz_light,yz_soil;

    public double getYz_tem1() {
        return yz_tem1;
    }

    public void setYz_tem1(double yz_tem1) {
        this.yz_tem1 = yz_tem1;
    }

    public double getYz_hum1() {
        return yz_hum1;
    }

    public void setYz_hum1(double yz_hum1) {
        this.yz_hum1 = yz_hum1;
    }

    public double getYz_light1() {
        return yz_light1;
    }

    public void setYz_light1(double yz_light1) {
        this.yz_light1 = yz_light1;
    }

    public double getYz_soil1() {
        return yz_soil1;
    }

    public void setYz_soil1(double yz_soil1) {
        this.yz_soil1 = yz_soil1;
    }

    double yz_tem1,yz_hum1,yz_light1,yz_soil1;
    public Getyz(IrrigationApplication tapp) {
        super(tapp);
    }


    @Override
    public void parseJSON(String res) {
        try {
            JSONObject o = new JSONObject(res);
            JSONObject object = new JSONObject(o.getString("data"));

            if(object.has("enable")){
                if(object.getInt("enable")==1){
                    enable = true;
                }
                setYz_tem(object.getDouble("yz_temperature_0"));
                setYz_hum(object.getDouble("yz_humidity_0"));
                setYz_light(object.getDouble("yz_light_0"));
                setYz_soil(object.getDouble("yz_tr_0"));

                setYz_tem1(object.getDouble("yz_temperature_1"));
                setYz_hum1(object.getDouble("yz_humidity_1"));
                setYz_light1(object.getDouble("yz_light_1"));
                setYz_soil1(object.getDouble("yz_tr_1"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBody() {
        return "{}";
    }

    @Override
    public String getAction() {
        return REQNAME.GETYZ;
    }
}
