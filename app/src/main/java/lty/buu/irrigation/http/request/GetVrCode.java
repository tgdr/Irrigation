package lty.buu.irrigation.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.REQNAME;


public class GetVrCode extends BaseRequest {

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String phonenum;

    public String getVrcode() {
        return vrcode;
    }

    public void setVrcode(String vrcode) {
        this.vrcode = vrcode;
    }

    public String vrcode;
    public GetVrCode(IrrigationApplication tapp) {
        super(tapp);
    }

    @Override
    public String getAction() {

        return REQNAME.GETVRCODE;
    }

    @Override
    public String getBody() {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("Phonenum",getPhonenum());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public void parseJSON(String responsestr) {
        super.parseJSON(responsestr);
        try {
            JSONObject object = new JSONObject(responsestr);
            if(object.has("vrcode")){
                if(object.getString("result").equals("ok")){
                    setVrcode(object.getString("vrcode"));
                }
            }
            else{
                setVrcode(null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            setVrcode(null);
        }
    }
}
