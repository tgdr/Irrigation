package lty.buu.irrigation.http.request;

import org.json.JSONException;
import org.json.JSONObject;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.REQNAME;

/**
 * Created by Administrator on 2017/5/24.
 */

public class UserLoginRequest extends BaseRequest {
    String action= REQNAME.LOGIN;
    String username;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    String result;


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

    String password;
    int auth;

    public UserLoginRequest(IrrigationApplication tapp) {
        super(tapp);
    }

    @Override
    public void parseJSON(String responsestr) {
        JSONObject object = null;
        try {
            object = new JSONObject(responsestr);
            if(object.has("result")){
                if(object.getString("result").equals("ok")){
                auth = object.getInt("auth");
                getApp().setUserpermission(auth);
                }
                else{
                    auth =-1;
                }
            }
        } catch (JSONException e) {
            auth = -1;
            e.printStackTrace();
        }

    }

    @Override
    public String getBody() {
        JSONObject object = new JSONObject();
        try {
            object.put("Username",username);
            object.put("Password",password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
return object.toString();
    }

    @Override
    public String getAction() {
        return action;
    }
}
