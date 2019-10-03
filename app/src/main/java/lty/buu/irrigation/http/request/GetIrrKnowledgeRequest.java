package lty.buu.irrigation.http.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.REQNAME;

public class GetIrrKnowledgeRequest extends BaseRequest {
    public int getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(int msgcount) {
        this.msgcount = msgcount;
    }

    public ArrayList getId() {
        return id;
    }

    public void setId(ArrayList id) {
        this.id = id;
    }

    public ArrayList getMsg() {
        return msg;
    }

    public void setMsg(ArrayList msg) {
        this.msg = msg;
    }

    int msgcount=0;
    ArrayList id,msg;
    @Override
    public void parseJSON(String res) {
       // super.parseJSON(res);


        try {
            JSONObject o = new JSONObject(res);

            JSONArray array = o.getJSONArray("msgresp");

            if(array.length()>0){
                for(int i=0;i<array.length();i++){
                    JSONObject object = (JSONObject) array.get(i);
                    id.add(object.getInt("msgid"));
                    msg.add(object.getString("msgcontent"));

                    msgcount++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBody() {
        return "{}";
    }

    public GetIrrKnowledgeRequest(IrrigationApplication tapp) {
        super(tapp);

        id = new ArrayList();
        msg = new ArrayList();
    }

    @Override
    public String getAction() {
        return REQNAME.GETKNOWLEDGE;
    }
}
