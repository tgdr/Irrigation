package lty.buu.irrigation.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.REQNAME;


/**
 * Created by Administrator on 2017/5/24.
 */

public class RegisterRequest extends BaseRequest {
    public String action= REQNAME.REGISTERUSER;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public boolean isRegresult() {
        return regresult;
    }

    public void setRegresult(boolean regresult) {
        this.regresult = regresult;
    }

    boolean regresult;
    String username;
    String password;

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    String phonenum;
    int auth;
    public RegisterRequest(IrrigationApplication tapp) {
        super(tapp);
    }

    @Override
    public String getBody() {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("Phonenum",phonenum);
            object.put("Username",username);
            object.put("Password",password);
            object.put("auth",auth);
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
            if(object.has("result")){
                if(object.getString("result").equals("ok")){
                   regresult= true;
                }
            }
            else{
               regresult = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            regresult = false;
        }

    }

    @Override
    public String getAction() {
        return action;
    }
}
