package lty.buu.irrigation.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;


public class SetPumpRequest extends BaseRequest {
    Boolean kaiguan=true;

    public void setKaiguan(Boolean kaiguan) {
        this.kaiguan = kaiguan;
    }

    public SetPumpRequest(IrrigationApplication tapp) {
        super(tapp);

    }


    @Override
    public String getBody() {
        if (kaiguan==true){
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("what","open");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }else{
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("what","close");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }
    }

    @Override
    public String getAction() {

        return "setpump.do";
    }
}
