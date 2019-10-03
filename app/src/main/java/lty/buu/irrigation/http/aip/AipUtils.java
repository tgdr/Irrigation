package lty.buu.irrigation.http.aip;

import com.baidu.aip.imageclassify.AipImageClassify;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AipUtils {
    //设置APPID/AK/SK
    public static final String APP_ID = "17402625";
    public static final String API_KEY = "Mab8FBa2GQ06DFdICom8SgKD";
    public static final String SECRET_KEY = "sdLTGbYFKnpGnWmjMguMzRie3YlGy5IE";

    public static  AipImageClassify initAipServer() {
        // 初始化一个AipImageClassify
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        return client;

    }
    public static JSONObject flowerinfo(byte[] picdata) {
        AipImageClassify client = initAipServer();
        JSONObject res = null;
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "3");
        options.put("baike_num", "5");


        res = client.plantDetect(picdata, options);
        try {
            System.out.println(res.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }
}
