package lty.buu.irrigation.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.REQNAME;

public class GetRealSensorRequest extends BaseRequest {
    double light;
    double tem;

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public double getTem() {
        return tem;
    }

    public void setTem(double tem) {
        this.tem = tem;
    }

    public double getHum() {
        return hum;
    }

    public void setHum(double hum) {
        this.hum = hum;
    }

    public double getSoil() {
        return soil;
    }

    public void setSoil(double soil) {
        this.soil = soil;
    }

    double hum;



    double soil;
    public GetRealSensorRequest(IrrigationApplication tapp) {
        super(tapp);
    }

    @Override
    public void parseJSON(String res) {
        super.parseJSON(res);
        try {
            JSONObject object = new JSONObject(res);
            if(object.has("result")&&object.getString("result").equals("ok")){
                setSoil(object.getDouble("Irrsoilhum"));
                setTem(object.getDouble("Irrtemperature"));
                setHum(object.getDouble("Irrhumidity"));
                setLight(object.getDouble("Irrlight"));
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
        return REQNAME.GetRealTimeSensorValues;
    }
}
