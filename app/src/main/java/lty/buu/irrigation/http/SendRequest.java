package lty.buu.irrigation.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2019/2/10.
 */

public class SendRequest {
    String inurl;

    public String getResponsestr() {
        return responsestr;
    }

    public void setResponsestr(String responsestr) {
        this.responsestr = responsestr;
    }

    String responsestr;
    OutputStream outputStream;
    HttpURLConnection connection;

    public SendRequest(String url, String responsestr){
        inurl = url;
        this.responsestr = responsestr;

    }

    public int send(){int status= 0;
        try {
            URL url = new URL(inurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            outputStream = connection.getOutputStream();
            outputStream.write(responsestr.getBytes());
            outputStream.close();
            status =connection.getResponseCode();
            if(status ==200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String temp="";
                responsestr = "";
                while ((temp = reader.readLine())!=null){
                    responsestr += temp;
                }
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return status;
    }

}
