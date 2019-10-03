package lty.buu.irrigation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.fragment.frag_realtimesensor;

public class RealTimeDataActivity extends AppCompatActivity {
    IrrigationApplication app;
    FragmentManager manager;
    FragmentTransaction trans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_real_time_data);
        manager = getSupportFragmentManager();
        app = (IrrigationApplication) getApplication();
        trans =  manager.beginTransaction();
        trans.setCustomAnimations(R.anim.scale_in,R.anim.scale_out);
        trans.replace(R.id.content_real,new frag_realtimesensor());

        trans.commit();
    }

}
