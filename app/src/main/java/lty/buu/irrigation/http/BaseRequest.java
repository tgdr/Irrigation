package lty.buu.irrigation.http;

import lty.buu.irrigation.IrrigationApplication;

/**
 * Created by Administrator on 2017/5/27.
 */


    public abstract class BaseRequest {
        public String getUsername(){
            return  app.getUsername();
        }
        public String getResponsestr() {
            return responsestr;
        }

        public void setResponsestr(String responsestr) {
            this.responsestr = responsestr;
        }

        public BaseRequest.onBackRequestLinstener getOnBackRequestLinstener() {
            return onBackRequestLinstener;
        }

        public void setOnBackRequestLinstener(BaseRequest.onBackRequestLinstener onBackRequestLinstener) {
            this.onBackRequestLinstener = onBackRequestLinstener;
        }

        public IrrigationApplication getApp() {
            return app;
        }

        public void setApp(IrrigationApplication app) {
            this.app = app;
        }

        String responsestr;
        IrrigationApplication app;
        public BaseRequest(IrrigationApplication tapp){
            app = tapp;
        }

        public interface onBackRequestLinstener{
            public void onRequestLinstener();
        }
        public  onBackRequestLinstener onBackRequestLinstener;
        public abstract String getAction();
        public void parseJSON(String res){

        }
        public String getBody(){
            return  null;
        }



}
