package lty.buu.irrigation.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.request.GetVrCode;
import lty.buu.irrigation.http.request.RegisterRequest;



public class RegisterActivity extends AppCompatActivity {

    EditText tvun,tvup,tvreup,tvphone;
    RegisterRequest request;
    GetVrCode requestcode;
   IrrigationApplication app;
   Button regbtn,getqrcodebtn;
    Spinner sp;
    String vrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

vrcode = null;
        app = (IrrigationApplication) getApplication();
        setContentView(R.layout.content_register);
        getqrcodebtn = (Button) findViewById(R.id.getqrcode);
        regbtn = (Button) findViewById(R.id.btnregister);
        regbtn.setVisibility(View.INVISIBLE);
        sp = (Spinner) findViewById(R.id.spinner);
        tvphone = (EditText) findViewById(R.id.editregphonenum);
        tvun = (EditText) findViewById(R.id.editregusername);
        tvup = (EditText) findViewById(R.id.editregpassword);
        tvreup = (EditText) findViewById(R.id.editregrepassword);
        getqrcodebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!tvup.getText().toString().equals(tvreup.getText().toString())){
                Toast.makeText(RegisterActivity.this,"两次密码输入的不一样啊~", Toast.LENGTH_LONG).show();
            }
            else if(tvup.getText().toString().length()<6 || tvup.getText().toString().length()>15){
                Toast.makeText(RegisterActivity.this,"密码长度必须大于6位或小于15", Toast.LENGTH_LONG).show();

            } else  if(tvun.getText().toString().equals("")){
                Toast.makeText(RegisterActivity.this,"没输入用户名", Toast.LENGTH_LONG).show();
            }
            else{
                requestcode = new GetVrCode(app);
                requestcode.setPhonenum(tvphone.getText().toString());
                requestcode.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                    @Override
                    public void onRequestLinstener() {
                        Log.e("vrcode",requestcode.getVrcode())  ;
                    }
                });
                app.requestOneThread(requestcode);
                Toast.makeText(RegisterActivity.this,"获取验证码成功", Toast.LENGTH_SHORT).show();
                regbtn.setVisibility(View.VISIBLE);
            }

        }
    });


    findViewById(R.id.btnregister).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!tvup.getText().toString().equals(tvreup.getText().toString())){
                Toast.makeText(RegisterActivity.this,"两次密码输入的不一样啊~", Toast.LENGTH_LONG).show();
            }
            else if(tvup.getText().toString().length()<6 || tvup.getText().toString().length()>15){
                Toast.makeText(RegisterActivity.this,"密码长度必须大于6位或小于15", Toast.LENGTH_LONG).show();

            }
            else{
                request =  new RegisterRequest(app);
                request.setPhonenum(tvphone.getText().toString());
                request.setUsername(tvun.getText().toString());
                request.setPassword(tvup.getText().toString());
                request.setAuth(sp.getSelectedItemPosition()+1);
                request.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
                    @Override
                    public void onRequestLinstener() {
                        if(request.isRegresult()){


                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("注册成功");
                            builder.setMessage("请牢记您的用户名"+tvun.getText().toString());
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent it = new Intent();
                                    it.setClass(RegisterActivity.this,LoginActivity.class);
                                    it.putExtra("username1",tvun.getText().toString());
                                    it.putExtra("password1",tvup.getText().toString());
                                    startActivity(it);
                                    finish();

                                }
                            });
                            builder.show();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("注册失败");
                            builder.setMessage("注册失败系统中已经存在此用户");

                            builder.show();
                        }
                    }
                });
                app.requestOneThread(request);
            }
        }
    });
}

}
