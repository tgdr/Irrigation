package lty.buu.irrigation.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import lty.buu.irrigation.R;

public class SplashActivity extends Activity {
    private Handler handler = new Handler();
    String []permissions = {Manifest.permission.CAMERA};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(ContextCompat.checkSelfPermission(SplashActivity.this,permissions[0]) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SplashActivity.this, permissions, 0x0016);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoLogin();
                }
            }, 6000);
        }



            else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoLogin();
                }
            }, 2000);

        }




    }

    private void gotoLogin() {
        Intent it = new Intent(SplashActivity.this,GuideActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            //If token is null, all callbacks and messages will be removed.
            handler.removeCallbacksAndMessages(null);
        }

        super.onDestroy();

    }
}
