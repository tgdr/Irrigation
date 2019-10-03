package lty.buu.irrigation.activity;



import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import lty.buu.irrigation.IrrigationApplication;
import lty.buu.irrigation.R;
import lty.buu.irrigation.defui.BottomNavigationViewHelper;
import lty.buu.irrigation.fragment.UserCenter_Fragment;
import lty.buu.irrigation.fragment.frag_Envir;
import lty.buu.irrigation.fragment.frag_irr_knowledge;
import lty.buu.irrigation.fragment.frag_moren;

import lty.buu.irrigation.fragment.frag_pumpContr;
import lty.buu.irrigation.fragment.frag_secondEnvir;
import lty.buu.irrigation.http.BaseRequest;
import lty.buu.irrigation.http.request.Getyz;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    IrrigationApplication app;
    FragmentManager manager;
    FragmentTransaction trans;
    Toolbar toolbar;
    private BottomNavigationView mBottomNavigationView;
    Getyz yzrequest;
    BottomNavigationItemView bottom_menu_realtime, bottom_menu_history, bottom_menu_usercenter;
    SharedPreferences spf;
    Boolean isinitok=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spf=getSharedPreferences("mauto",MODE_PRIVATE);
        manager = getSupportFragmentManager();
        app = (IrrigationApplication) getApplication();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottom_menu_realtime = findViewById(R.id.menu_realtimedata);
        bottom_menu_history = findViewById(R.id.menu_history);
        bottom_menu_usercenter = findViewById(R.id.menu_usercenter);
        initBottomNavigation();
        yzrequest = new Getyz(app);
        yzrequest.setOnBackRequestLinstener(new BaseRequest.onBackRequestLinstener() {
            @Override
            public void onRequestLinstener() {
                app.getSensorConfig().maxLight= (int) yzrequest.getYz_light1();
                app.getSensorConfig().minLight= (int) yzrequest.getYz_light();
                app.getSensorConfig().maxAirTemperature= (int) yzrequest.getYz_tem1();
                app.getSensorConfig().minAirTemperature= (int) yzrequest.getYz_tem();

                app.getSensorConfig().maxAirHumidity= (int) yzrequest.getYz_hum1();
                app.getSensorConfig().minAirHumidity= (int) yzrequest.getYz_hum();
                app.getSensorConfig().maxSoilHumidity= (int) yzrequest.getYz_soil1();
                app.getSensorConfig().minSoilHumidity= (int) yzrequest.getYz_soil();
                isinitok=true;
                trans =  manager.beginTransaction();
                trans.setCustomAnimations(R.anim.scale_in,R.anim.scale_out);
                trans.replace(R.id.mycontent,new frag_moren());
                trans.commit();
            }
        });
        app.requestOneThread(yzrequest);











      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    private void initBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.bv_bottomNavigation);
        // 解决当item大于三个时，非平均布局问题
        mBottomNavigationView.setItemIconTintList(null);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_realtimedata:
                        trans = manager.beginTransaction();
                        trans.setCustomAnimations(R.anim.scale_in,R.anim.scale_out);
                        trans.replace(R.id.mycontent,new frag_secondEnvir());
                        trans.commit();

                        setTitle("实时数据采集");
                        break;
                    case R.id.menu_history:
                        startActivity(new Intent(MainActivity.this,RealTimeDataActivity.class));
                       // setFragmentPosition(4);
                        //setTitle("历史数据监控");
                        break;
                    case R.id.menu_usercenter:
                        trans=manager.beginTransaction();
                        trans.setCustomAnimations(R.anim.scale_in,R.anim.scale_out);

                        trans.replace(R.id.mycontent,new UserCenter_Fragment());
                        trans.commit();
                        setTitle("个人中心");
                        break;

                    default:
                        break;
                }
                // 这里注意返回true,否则点击失效
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_showdata) {
            // Handle the camera action
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            trans=manager.beginTransaction();
//            trans.setCustomAnimations(R.anim.scale_in,R.anim.scale_out);
//
//            trans.replace(R.id.mycontent,new frag_Envir());
//            trans.commit();
            trans = manager.beginTransaction();
            trans.setCustomAnimations(R.anim.scale_in,R.anim.scale_out);
            trans.replace(R.id.mycontent,new frag_secondEnvir());
            trans.commit();
        } else if (id == R.id.nav_showirr) {
            startActivity(new Intent(MainActivity.this,RealTimeDataActivity.class));

           // overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        } else if (id == R.id.nav_autoctrl) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            trans=manager.beginTransaction();
            trans.setCustomAnimations(R.anim.my_card_flip_left_in,
                    R.anim.my_card_flip_left_out, R.anim.my_card_flip_right_in,
                    R.anim.my_card_flip_right_out);
            trans.replace(R.id.mycontent,new frag_pumpContr());
            trans.commit();
        } else if (id == R.id.nav_irrknowledge) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            trans=manager.beginTransaction();
            trans.setCustomAnimations(R.anim.my_card_flip_left_in,
                    R.anim.my_card_flip_left_out, R.anim.my_card_flip_right_in,
                    R.anim.my_card_flip_right_out);
            trans.replace(R.id.mycontent,new frag_irr_knowledge());

            trans.commit();
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_explain) {

        }
        else if (id == R.id.nav_exit) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}